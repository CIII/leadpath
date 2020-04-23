package com.pony.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.pony.lead.Arrival;
import com.pony.lead.UserProfile;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;
import com.pony.validation.ValidationResponse;

@ImplementedBy(ArrivalModelImpl.class)
public interface ArrivalModel {

	Arrival find(Long id) throws NamingException, SQLException;

	Arrival find(Long publisherId, Long publisherListId, String externalId) throws NamingException, SQLException;

	Arrival findByLeadId(Long leadId) throws NamingException, SQLException;

	Arrival findByLeadMatchId(Long leadMatchId) throws NamingException, SQLException;

	Arrival findByExternalId(Publisher publisher, String external_id) throws SQLException, NamingException;

	Arrival create(Arrival a) throws SQLException, NamingException;

	Arrival create(Connection con, Arrival a) throws SQLException;

	Arrival findByPublisherAndUserProfile(Publisher publisher, UserProfile userProfile)
			throws NamingException, SQLException;

	Arrival findByPublisherAndUserProfile(Connection con, Publisher publisher, UserProfile userProfile)
			throws SQLException;

	void touch(Arrival arrival) throws NamingException, SQLException;

	void touch(Arrival arrival, ValidationResponse vResponse) throws NamingException, SQLException;

	/**
	 * read arrivals from a starting id up to #count rows in order of arrival id
	 *
	 * @param publisherList
	 * @param minId         the starting arrival id
	 * @param count         max number of arrivals to read
	 * @return an ordered list (by arrival id asc) of arrivals
	 */
	List<Arrival> findRange(PublisherList publisherList, Long minId, int count) throws NamingException, SQLException;

	/**
	 * read the arrivals for all messages to the provided publisher list that were opened (within days), and have not received a message since sendFrequencyDays
	 *
	 * @param publisherList     the list that we look at the messages for
	 * @param openWithinDays    number of days to look back (from today) for an open event for the email address of an arrival
	 * @param sendFrequencyDays number of days to block. If there was a message sent within this number of days, we cannot send another one yet
	 * @param maxRowCount       cut off at this number of arrivals found
	 * @return
	 */
	List<Arrival> findRange(PublisherList publisherList, int openWithinDays, int sendFrequencyDays, int maxRowCount)
			throws NamingException, SQLException;

}