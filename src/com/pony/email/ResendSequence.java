package com.pony.email;

import com.pony.publisher.Status;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 1:52 PM
 */
public class ResendSequence {
    private final Long id;
    private final String name;
    private final Status status;

    public static final int DAY_2 = 2;
    public static final int DAY_3 = 3;
    public static final int DAY_4 = 4;

    private static final List<String> FOR_3 = new ArrayList<String>();
    private static final List<String> FOR_4 = new ArrayList<String>();

    static {
        // 40, 75, 150: 3 // reverse offers from first send
        FOR_3.add("40");
        FOR_3.add("75");
        FOR_3.add("150");

        // 43, 44, 72: 4
        FOR_4.add("43");
        FOR_4.add("44");
        FOR_4.add("72");
    }

    /*
-- and separate for other publishers, plus later for LeadKarma
select x.up_id, x.email, x.first_message_id, x.opened, m.creative_id, m.host_id, o.lead_type_id
 from (select up.id up_id, up.email, (select min(id) from messages where user_profile_id = up.id) first_message_id, if(op.id is null, 0, 1) opened, o.lead_type_id from user_profiles up join arrivals a on a.user_profile_id = up.id join publishers p on p.id = a.publisher_id join messages m on m.user_profile_id = up.id join creatives c on c.id = m.creative_id join offers o on o.id = c.offer_id left join opens op on op.message_id = m.id where p.id <> 2 and timestampdiff(HOUR, up.created_at, now()) < 24 limit 20)x
   join messages m on m.id = x.first_message_id join creatives c on m.creative_id = c.id join offers o on o.id = c.offer_id
   order by x.up_id, o.lead_type_id;
     */

    private ResendSequence(Long id, String externalListId, Status status) {
        this.id = id;
        this.name = externalListId;
        this.status = status;
    }

    public static ResendSequence create(Long id, String name, Status status) {
        return new ResendSequence(id, name, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResendSequence that = (ResendSequence) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ResendSequence{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public Long determineOfferId(ResendCandidate candidate) throws NamingException, SQLException {
        Long offerId = null;

        if (ResendMessagePhase.DAY_2.equals(candidate.getPhase())) {
            // first resend
            if ("12346".equals(getName()) || "12348".equals(getName())) {
                offerId = 2L;
            }
            else if (FOR_3.contains(getName())) {
                offerId = 3L;
            }
            else if (FOR_4.contains(getName())) {
                offerId = 4L;
            }
            else if ("healthinsurance".equals(getName())) {
                offerId = 6L;
            }
        }
        else if (ResendMessagePhase.DAY_3.equals(candidate.getPhase())) {
            // default (all get 9) offerId = 9L;
        }

        return offerId;
    }
}
