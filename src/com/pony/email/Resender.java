package com.pony.email;

import com.pony.PonyException;
import com.pony.leadtypes.Email;
import com.pony.models.HostModel;
import com.pony.models.ResendMessagePhaseModel;
import com.pony.models.ResendSequenceModel;
import com.pony.publisher.Status;
import com.pony.validation.ValidationException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 4:27 PM
 */
public class Resender {
	private static final Log LOG = LogFactory.getLog(Resender.class);
	
    protected String name;
    protected final Map<Long, List<ResendCandidate>> resendCandidates = new HashMap<Long, List<ResendCandidate>>();
    protected final ResendSequence sequence;
    protected final int intervalHours;

    protected static final int SENDING_HOURS = 12;

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    protected Resender(String name, int intervalHours) throws NamingException, SQLException {
        this.name = name;
        this.intervalHours = intervalHours;
        // read or create the sequence
        sequence = ResendSequenceModel.findOrCreateSequence(name);
    }

    public static Resender create(String name, int hoursBack) throws NamingException, SQLException {
        return new Resender(name, hoursBack);
    }

    /**
     * calculate the minutes between two sends
     *
     * @param candidateListSize
     * @return
     */
    protected static long calculateDelayMinutes(int candidateListSize) {
        if (candidateListSize <= 1) {
            return 1L;
        }

        BigDecimal delay = BigDecimal.ONE.divide(new BigDecimal(candidateListSize).divide(new BigDecimal(SENDING_HOURS * 60), MC), MC);
        if (delay.doubleValue() < 1.0) {
            return 1L;
        }
        else if (delay.doubleValue() > 5.0) {
            return 5;
        }
        return delay.longValue();
    }

    /**
     * get new records for this queue, from current timestamp up to hoursBack
     */
    public void fetchNew() throws PonyException {

        try {
            // read new resend candidates
            resendCandidates.putAll(ResendMessagePhaseModel.findNewCandidates(sequence.getId(), name, intervalHours));

            for (Map.Entry<Long, List<ResendCandidate>> entry : resendCandidates.entrySet()) {
                List<ResendCandidate> list = entry.getValue();
                List<ResendCandidate> toBeRemoved = new ArrayList<ResendCandidate>();
                LOG.info("fetchNew: host_id=" + entry.getKey() + ": list.size=" + list.size());
                for (ResendCandidate candidate : list) {
                    try {
                        // check against existing sequence (the original message can only be in the same sequence once)
                        if (candidate.wasOpened() &&
                                Email.isValid(candidate.getUserProfileId(), candidate.getEmail(), candidate.getPublisherId())) {
                            continue;
                        }
                    }
                    catch (ValidationException e) {
                        LOG.error(e);
                    }
                    // if we make it here, either validation failed, or threw an error, so we don't send to this one
                    toBeRemoved.add(candidate);
                }
                list.removeAll(toBeRemoved);
            }
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
    }

    /**
     * for each phase, create the messages, and send them, then create the next phase for the message (if there is one)
     *
     * @param emailService
     */
    public long processPhases(EmailService emailService) throws PonyException {

        // get the list of active hosts
        long messageCount = 0L;

        try {
            List<Host> activeHosts = HostModel.findByStatus(Status.OPEN);

            // and segment the volume per host
            for (Host host : activeHosts) {
                messageCount += processHostCandidates(host, emailService);
            }
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }

        return messageCount;
    }

    private long processHostCandidates(Host host, EmailService emailService) throws PonyException {

        LOG.info(">>>>>>>>>>> Processing resend candidates for host_id=" + host.getId());

        Long hostId = host.getId();

        List<ResendCandidate> finalCandidates = new ArrayList<ResendCandidate>();

        // first figure out the volume we're expecting (so we can try to spread it out ...?)
        List<ResendCandidate> newCandidates = resendCandidates.get(hostId);
        if (newCandidates != null) {
            LOG.info("==> found newCandidates:" + newCandidates.size());
            finalCandidates.addAll(newCandidates); // all new ones are already checked and filtered (only opened ones)
        }

        Connection con = null;

        try {
            con = ResendMessagePhaseModel.connectX();

            ResendMessagePhase.Phase phase = ResendMessagePhase.START;
            while (phase != null) {
                // get all original message ids that are in this phase right now
                for (ResendCandidate phaseCandidate : ResendMessagePhaseModel.find(con, sequence, hostId, phase)) {
                    // check valid (unsub, suppression, ...)
                    if (Email.isValid(phaseCandidate.getUserProfileId(), phaseCandidate.getEmail(), phaseCandidate.getPublisherId())) {
                        // and add an entry for the queue for the next phase
                        finalCandidates.add(phaseCandidate.moveToNextPhase());
                    }
                }

                phase = phase.getNextPhase();
            }
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        catch (ValidationException e) {
            throw new PonyException(e);
        }
        finally {
            ResendMessagePhaseModel.close(con);
        }

        // calculate the delay between messages (to spread the sends out)
        long delayMinutes = calculateDelayMinutes(finalCandidates.size());

        emailService.resend(hostId, sequence, finalCandidates, delayMinutes);

        return finalCandidates.size();
    }

    public static void main(String[] args) {
        LOG.debug("0:" + calculateDelayMinutes(0));
        LOG.debug("1:" + calculateDelayMinutes(1));
        LOG.debug("10:" + calculateDelayMinutes(10));
        LOG.debug("100:" + calculateDelayMinutes(100));
        LOG.debug("1000:" + calculateDelayMinutes(1000));
        LOG.debug("10000:" + calculateDelayMinutes(10000));
        LOG.debug("130:" + calculateDelayMinutes(130));
    }
}
