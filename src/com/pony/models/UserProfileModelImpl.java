package com.pony.models;

import com.pony.advertiser.Advertiser;
import com.pony.email.MailHost;
import com.pony.email.MessageData;
import com.pony.email.validation.AddressValidator;
import com.pony.lead.Arrival;
import com.pony.lead.UserProfile;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/1/12
 * Time: 12:08 PM
 */
public class UserProfileModelImpl extends Model implements UserProfileModel {
	private final static Log LOG = LogFactory.getLog(UserProfileModelImpl.class);
    private final static String COLUMNS = "up.id, up.email, up.is_trap, up.mail_host_id, mh.mx_validated";

    @Inject UserProfileModelImpl() {
    	super(0L);
    }
    
    protected UserProfileModelImpl(Long id) {
        super(id);
    }

    /**
     * look up user profile by email. If it doesn't exist, create it
     *
     * @param up the user profile containing the email address
     * @return the user profile with its id
     * @throws NamingException
     * @throws SQLException
     */
    @Override
	public UserProfile findOrCreate(UserProfile up) throws NamingException, SQLException {
    	return UserProfileModelImpl.findOrCreateStatic(up);
    }
    
    public static UserProfile findOrCreateStatic(UserProfile up) throws NamingException, SQLException {
        if (up == null || up.getEmail() == null) {
            throw new IllegalArgumentException("no valid user profile provided");
        }

        // findByEmail?
        Connection con = null;
        try {
            con = connectX();
            UserProfile userProfile = findByEmailStatic(con, up);
            if (userProfile != null) {
                return userProfile;
            }

            String isp = UserProfile.parseMailHost(up);
            MailHost mailHost = findOrCreateMailHostStatic(con, isp);

            return createStatic(con, up.getEmail(), mailHost);
        }
        finally {
            close(con);
        }
    }

    @Override
	public UserProfile findByArrival(Arrival arrival) throws NamingException, SQLException {
    	return findByArrivalStatic(arrival);
    }
    
    public static UserProfile findByArrivalStatic(Arrival arrival) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from user_profiles up join mail_hosts mh on mh.id = up.mail_host_id join arrivals a on a.user_profile_id = up.id where a.id = ? limit 1");
            stmt.setLong(1, arrival.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MailHost mailHost = findMailHostStatic(con, rs.getLong("mail_host_id"));
                return UserProfile.create(rs.getLong("id"), rs.getString("email"), rs.getBoolean("is_trap"), mailHost);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    @Override
	public UserProfile create(String email) throws NamingException, SQLException {
    	return UserProfileModelImpl.createStatic(email);
    }
    
    public static UserProfile createStatic(String email) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            String domain = UserProfile.parseMailHost(email);
            MailHost mailHost = findOrCreateMailHostStatic(con, domain);

            return createStatic(con, email, mailHost);
        }
        finally {
            close(con);
        }
    }


    @Override
	public UserProfile create(UserProfile up) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();

            MailHost mailHost = up.getMailHost();
            if (mailHost == null) {
                mailHost = findOrCreateMailHost(con, up);
            }

            return createStatic(con, up.getEmail(), mailHost);
        }
        finally {
            close(con);
        }
    }


    @Override
	public MailHost findOrCreateMailHost(String domain) throws SQLException, NamingException {
    	return findOrCreateMailHostStatic(domain);
    }
    
    public static MailHost findOrCreateMailHostStatic(String domain) throws SQLException, NamingException {
        Connection con = null;

        try {
            con = connectX();
            return findOrCreateMailHostStatic(con, domain);
        }
        finally {
            close(con);
        }
    }

    @Override
	public MailHost findOrCreateMailHost(Connection con, String domain) throws SQLException, NamingException {
    	return findOrCreateMailHostStatic(con, domain);
    }
    
    public static MailHost findOrCreateMailHostStatic(Connection con, String domain) throws SQLException, NamingException {

        MailHost mailHost = findMailHostStatic(con, domain);
        if (mailHost != null) {
            return mailHost;
        }

        PreparedStatement stmt = null;

        try {
            // it's a new one: do an mx lookup to validate it's a valid email domain
            boolean isMxValidated = AddressValidator.isValidMX(domain);

            stmt = con.prepareStatement("insert into mail_hosts (name, email_suffix, created_at, mx_validated) values(?, ?, now(), ?)");
            stmt.setString(1, domain);
            stmt.setString(2, domain.toLowerCase());
            stmt.setBoolean(3, isMxValidated);

            Long mailHostId = executeWithLastId(stmt);

            return MailHost.create(mailHostId, domain, isMxValidated);
        }
        finally {
            close(stmt);
        }
    }

    @Override
	public MailHost findMailHost(Connection con, String domain) throws SQLException, NamingException {
    	return findMailHostStatic(con, domain);
    }
    
    public static MailHost findMailHostStatic(Connection con, String domain) throws SQLException, NamingException {

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, mx_validated from mail_hosts where email_suffix=?");
            stmt.setString(1, domain.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return MailHost.create(rs.getLong("id"), domain, rs.getBoolean("mx_validated"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public MailHost findMailHost(Connection con, Long id) throws SQLException, NamingException {
    	return findMailHostStatic(con, id);
    }
    
    public static MailHost findMailHostStatic(Connection con, Long id) throws SQLException, NamingException {

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, email_suffix, mx_validated from mail_hosts where id=?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return MailHost.create(rs.getLong("id"), rs.getString("email_suffix"), rs.getBoolean("mx_validated"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    private MailHost findOrCreateMailHost(Connection con, UserProfile userProfile) throws SQLException, NamingException {

        if (userProfile.getMailHost() != null) {
            return userProfile.getMailHost();
        }

        String ispDomain = UserProfile.parseMailHost(userProfile.getEmail()).toLowerCase();

        return findOrCreateMailHost(con, ispDomain);
    }

    private static UserProfile createStatic(Connection con, String email, MailHost mailHost) throws SQLException {
        if (email == null) {
            throw new IllegalArgumentException("no valid user profile: null");
        }
        if (mailHost == null) {
            throw new IllegalArgumentException("no valid mailHost for user profile:" + mailHost);
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("insert into user_profiles (email, email_md5, mail_host_id, created_at) values(?,md5(?),?,now())");
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setLong(3, mailHost.getId());
            Long id = executeWithLastId(stmt);

            return UserProfile.create(id, email, false, mailHost);
        }
        finally {
            close(stmt);
        }
    }

    @Override
	public UserProfile findByEmail(UserProfile up) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return findByEmail(con, up);
        }
        finally {
            close(con);
        }
    }

    @Override
	public UserProfile findByEmail(String email) throws SQLException, NamingException {
    	return UserProfileModelImpl.findByEmailStatic(email);
    }
    public static UserProfile findByEmailStatic(String email) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return findByEmailStatic(con, email);
        }
        finally {
            close(con);
        }
    }

    @Override
	public UserProfile find(Long id) throws SQLException, NamingException {
    	return findStatic(id);
    }
    
    public static UserProfile findStatic(Long id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return findStatic(con, id);
        }
        finally {
            close(con);
        }
    }

    @Override
	public UserProfile find(Connection con, Long id) throws SQLException, NamingException {
    	return findStatic(con, id);
    }
    
    public static UserProfile findStatic(Connection con, Long id) throws SQLException, NamingException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, email, is_trap, mail_host_id from user_profiles where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MailHost isp = findMailHostStatic(con, rs.getLong("mail_host_id"));
                return UserProfile.create(rs.getLong("id"), rs.getString("email"), rs.getBoolean("is_trap"), isp);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public Map<String, String> readProfileAttributes(Long userProfileId) throws NamingException, SQLException {
    	return readProfileAttributesStatic(userProfileId);
    }
    
    public static Map<String, String> readProfileAttributesStatic(Long userProfileId) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return readProfileAttributesStatic(con, userProfileId);
        }
        finally {
            close(con);
        }

    }

    @Override
	public Map<String, String> readProfileAttributes(Connection con, Long userProfileId) throws SQLException {
    	return readProfileAttributes(con, userProfileId);
    }
    
    public static Map<String, String> readProfileAttributesStatic(Connection con, Long userProfileId) throws SQLException {

        Map<String, String> values = new HashMap<String, String>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select a.name , pa.value from attributes a join profile_attributes pa on pa.attribute_id = a.id where a.is_large=0 and pa.user_profile_id =?");
            stmt.setLong(1, userProfileId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                values.put(rs.getString("name"), rs.getString("value"));
            }
            close(stmt);

            // look for XL attributes
            stmt = con.prepareStatement("select a.name , pa.value from attributes a join profile_xl_attributes pa on pa.attribute_id = a.id where a.is_large=1 and pa.user_profile_id =?");
            stmt.setLong(1, userProfileId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                values.put(rs.getString("name"), rs.getString("value"));
            }
        }
        finally {
            close(stmt);
        }

        return values;
    }

    @Override
	public UserProfile findByEmail(Connection con, UserProfile up) throws SQLException, NamingException {
    	return findByEmailStatic(con, up);
    }
    public static UserProfile findByEmailStatic(Connection con, UserProfile up) throws SQLException, NamingException {
        String email = up.getEmail();
        return findByEmailStatic(email);
    }

    @Override
	public UserProfile findByEmail(Connection con, String email) throws SQLException, NamingException {
    	return UserProfileModelImpl.findByEmailStatic(con, email);
    }
    public static UserProfile findByEmailStatic(Connection con, String email) throws SQLException, NamingException {
        PreparedStatement stmt = null;

        try {
        	LOG.debug("Querying for user profile by email '" + email + "'");
            stmt = con.prepareStatement("select " + COLUMNS + " from user_profiles up join mail_hosts mh on mh.id = up.mail_host_id where up.email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	LOG.debug("Found data. Creating user profile object.");
                MailHost mailHost = findMailHostStatic(con, rs.getLong("mail_host_id"));
                return UserProfile.create(rs.getLong("id"), email, rs.getBoolean("is_trap"), mailHost);
            } else {
            	LOG.debug("Found no such user profile.");
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public UserProfile findByEmailMd5(String md5) throws SQLException, NamingException {
        Connection con = null;

        try {
            con = connectX();
            return findByEmailMd5(con, md5);
        }
        finally {
            close(con);
        }
    }

    @Override
	public UserProfile findByEmailMd5(Connection con, String md5) throws SQLException, NamingException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select up.id, up.email, up.is_trap, up.mail_host_id, mh.mx_validated from user_profiles up join mail_hosts mh on mh.id = up.mail_host_id where up.email_md5 = ?");
            stmt.setString(1, md5);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MailHost mailHost = findMailHost(con, rs.getLong("mail_host_id"));
                return UserProfile.create(rs.getLong("id"), rs.getString("email"), rs.getBoolean("is_trap"), mailHost);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public void touch(UserProfile userProfile) throws NamingException, SQLException {
    	touchStatic(userProfile);
    }
    
    public static void touchStatic(UserProfile userProfile) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("update user_profiles set last_seen_at = now() where id = ?");
            stmt.setLong(1, userProfile.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    @Override
	public boolean hasComplained(Connection con, Long userProfileId) throws NamingException, SQLException {
    	return hasComplained(con, userProfileId);
    }
    
    public static boolean hasComplainedStatic(Connection con, Long userProfileId) throws NamingException, SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select count(*) count from complaints where user_profile_id = ?");
            stmt.setLong(1, userProfileId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true;
            }
        }
        finally {
            close(stmt);
        }
        return false;
    }


    @Override
	public boolean isKnownTrap(String email) throws NamingException, SQLException {
    	return isKnownTrapStatic(email);
    }
    
    public static boolean isKnownTrapStatic(String email) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return isKnownTrapStatic(con, email);
        }
        finally {
            close(con);
        }
    }

    /**
     * check against known_traps table
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    private static boolean isKnownTrapStatic(Connection con, String email) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select count(*) count from known_traps where email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true;
            }
        }
        finally {
            close(stmt);
        }
        return false;
    }


    @Override
	public boolean isSuppressed(String email, Long publisherId) throws NamingException, SQLException {
    	return isSuppressedStatic(email, publisherId);
    }
    
    public static boolean isSuppressedStatic(String email, Long publisherId) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return isSuppressedStatic(con, email, publisherId);
        }
        finally {
            close(con);
        }
    }
    
    public static boolean isSuppressedStatic(Connection con, String email, Long publisherId) throws SQLException {
        PreparedStatement stmt = null;

        // todo: should we actually care and check if it's suppressed for this specific publisher?
        try {
            stmt = con.prepareStatement("select count(*) count from md5_suppressions where md5_email = md5(?)");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true;
            }
        }
        finally {
            close(stmt);
        }
        return false;
    }

    @Override
	public boolean hasComplained(Long userProfileId) throws NamingException, SQLException {
    	return hasComplainedStatic(userProfileId);
    }
    
    public static boolean hasComplainedStatic(Long userProfileId) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return hasComplainedStatic(con, userProfileId);
        }
        finally {
            close(con);
        }
    }

    @Override
	public boolean hasBounced(Long userProfileId) throws SQLException, NamingException {
    	return hasBouncedStatic(userProfileId);
    }
    
    public static boolean hasBouncedStatic(Long userProfileId) throws SQLException, NamingException {
        Connection con = null;

        try {
            con = connectX();
            return hasBouncedStatic(con, userProfileId);
        }
        finally {
            close(con);
        }
    }

    @Override
	public boolean hasBounced(Connection con, Long userProfileId) throws SQLException, NamingException {
    	return hasBouncedStatic(con, userProfileId);
    }
    
    public static boolean hasBouncedStatic(Connection con, Long userProfileId) throws SQLException, NamingException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select count(*) count from bounces where user_profile_id = ?");
            stmt.setLong(1, userProfileId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true;
            }
        }
        finally {
            close(stmt);
        }
        return false;
    }

    @Override
	public boolean hasUnsubscribed(Long userProfileId) throws SQLException, NamingException {
    	return hasUnsubscribedStatic(userProfileId);
    }
    
    public static boolean hasUnsubscribedStatic(Long userProfileId) throws SQLException, NamingException {
        Connection con = null;

        try {
            con = connectX();
            return hasUnsubscribedStatic(con, userProfileId);
        }
        finally {
            close(con);
        }
    }

    @Override
	public boolean hasUnsubscribed(Connection con, Long userProfileId) throws SQLException, NamingException {
    	return hasUnsubscribedStatic(con, userProfileId);
    }
    
    public static boolean hasUnsubscribedStatic(Connection con, Long userProfileId) throws SQLException, NamingException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select count(*) count from unsubscribes where user_profile_id = ?");
            stmt.setLong(1, userProfileId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true;
            }
        }
        finally {
            close(stmt);
        }
        return false;
    }

    @Override
	public void unsubscribe(UserProfile up, String userAgent, String ipAddress, String referrer, Long messageId) throws NamingException, SQLException {
    	unsubscribeStatic(up, userAgent, ipAddress, referrer, messageId);
    }
    
    public static void unsubscribeStatic(UserProfile up, String userAgent, String ipAddress, String referrer, Long messageId) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("insert into unsubscribes (user_profile_id, ip_address, user_agent, referrer, created_at");
            if (messageId != null) {
                sql.append(", message_id");
            }
            sql.append(") values(?,?,?,?,now()");

            if (messageId != null) {
                sql.append(",?");
            }
            sql.append(")  ON DUPLICATE KEY UPDATE message_id = message_id");

            con = connectX();

            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, up.getId());
            stmt.setString(2, ipAddress);
            stmt.setString(3, userAgent);
            stmt.setString(4, referrer);

            if (messageId != null) {
                stmt.setLong(5, messageId);
            }

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    @Override
	public List<MessageData> readMessages(Long userProfileId) throws NamingException, SQLException {
        List<MessageData> data = new ArrayList<MessageData>();

        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select up.id profile_id, a.id arrival_id, a.publisher_id, a.publisher_list_id, m.id message_id, m.host_id, of.lead_type_id, m.creative_id, cr.offer_id, if(o.id is null, 0, 1) opened, if(c.id is null, 0, 1) clicked from messages m join user_profiles up on up.id = m.user_profile_id join arrivals a on a.user_profile_id = up.id join creatives cr on cr.id = m.creative_id join offers of on of.id = cr.offer_id left join opens o on o.message_id = m.id left join clicks c on c.message_id = m.id where up.id = ? and a.validation_code = 0");
            stmt.setLong(1, userProfileId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(MessageData.create(rs));
            }
        }
        finally {
            close(stmt);
            close(con);
        }


        return data;
    }

    @Override
	public void createSuppression(String emailMd5, Advertiser advertiser) throws NamingException, SQLException {
    	createSuppressionStatic(emailMd5, advertiser);
    }
    
    public static void createSuppressionStatic(String emailMd5, Advertiser advertiser) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert into md5_suppressions (md5_email, advertiser_id, created_at) values (?,?,now())");
            stmt.setString(1, emailMd5);
            stmt.setLong(2, advertiser.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    // check if the domain in the address is a common typo, and if so ; correct it
    @Override
	public String autoCorrect(String email) throws NamingException, SQLException {
    	return autoCorrectStatic(email);
    }
    
    public static String autoCorrectStatic(String email) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            String[] emailLabels = UserProfile.parseMailLabels(email);

            con = connectX();
            MailHost fromIsp = findMailHostStatic(con, emailLabels[1]);
            if (fromIsp == null) {
                // nothing to correct; it doesn't even exist yet
                return email;
            }

            // look in the corrections table for an entry with our isp domain
            stmt = con.prepareStatement("select mh.email_suffix, mh.mx_validated, acd.to_mail_host_id from mail_hosts mh join auto_correct_domains acd on acd.to_mail_host_id = mh.id where  acd.from_mail_host_id = ?");
            stmt.setLong(1, fromIsp.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MailHost toMailHost = MailHost.create(rs.getLong("to_mail_host_id"), rs.getString("email_suffix"), rs.getBoolean("mx_validated"));

                // now create the corrected email address
                return emailLabels[0] + "@" + toMailHost.getDomain();
            }
        }
        catch (IllegalArgumentException e) {
            return email;
        }
        finally {
            close(stmt);
            close(con);
        }

        return email;
    }
}

