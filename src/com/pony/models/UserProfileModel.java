package com.pony.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.pony.advertiser.Advertiser;
import com.pony.email.MailHost;
import com.pony.email.MessageData;
import com.pony.lead.Arrival;
import com.pony.lead.UserProfile;

@ImplementedBy(UserProfileModelImpl.class)
public interface UserProfileModel {

	/**
	 * look up user profile by email. If it doesn't exist, create it
	 *
	 * @param up the user profile containing the email address
	 * @return the user profile with its id
	 * @throws NamingException
	 * @throws SQLException
	 */
	UserProfile findOrCreate(UserProfile up) throws NamingException, SQLException;

	UserProfile findByArrival(Arrival arrival) throws NamingException, SQLException;

	UserProfile create(String email) throws NamingException, SQLException;

	UserProfile create(UserProfile up) throws NamingException, SQLException;

	MailHost findOrCreateMailHost(String domain) throws SQLException, NamingException;

	MailHost findOrCreateMailHost(Connection con, String domain) throws SQLException, NamingException;

	MailHost findMailHost(Connection con, String domain) throws SQLException, NamingException;

	MailHost findMailHost(Connection con, Long id) throws SQLException, NamingException;

	UserProfile findByEmail(UserProfile up) throws SQLException, NamingException;

	UserProfile findByEmail(String email) throws SQLException, NamingException;

	UserProfile find(Long id) throws SQLException, NamingException;

	UserProfile find(Connection con, Long id) throws SQLException, NamingException;

	Map<String, String> readProfileAttributes(Long userProfileId) throws NamingException, SQLException;

	Map<String, String> readProfileAttributes(Connection con, Long userProfileId) throws SQLException;

	UserProfile findByEmail(Connection con, UserProfile up) throws SQLException, NamingException;

	UserProfile findByEmail(Connection con, String email) throws SQLException, NamingException;

	UserProfile findByEmailMd5(String md5) throws SQLException, NamingException;

	UserProfile findByEmailMd5(Connection con, String md5) throws SQLException, NamingException;

	void touch(UserProfile userProfile) throws NamingException, SQLException;

	boolean hasComplained(Connection con, Long userProfileId) throws NamingException, SQLException;

	boolean isKnownTrap(String email) throws NamingException, SQLException;

	boolean isSuppressed(String email, Long publisherId) throws NamingException, SQLException;

	boolean hasComplained(Long userProfileId) throws NamingException, SQLException;

	boolean hasBounced(Long userProfileId) throws SQLException, NamingException;

	boolean hasBounced(Connection con, Long userProfileId) throws SQLException, NamingException;

	boolean hasUnsubscribed(Long userProfileId) throws SQLException, NamingException;

	boolean hasUnsubscribed(Connection con, Long userProfileId) throws SQLException, NamingException;

	void unsubscribe(UserProfile up, String userAgent, String ipAddress, String referrer, Long messageId)
			throws NamingException, SQLException;

	List<MessageData> readMessages(Long userProfileId) throws NamingException, SQLException;

	void createSuppression(String emailMd5, Advertiser advertiser) throws NamingException, SQLException;

	// check if the domain in the address is a common typo, and if so ; correct it
	String autoCorrect(String email) throws NamingException, SQLException;

}