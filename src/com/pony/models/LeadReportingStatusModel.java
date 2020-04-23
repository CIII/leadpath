package com.pony.models;

import java.sql.Timestamp;

import com.google.inject.ImplementedBy;
import com.pony.PonyException;

@ImplementedBy(LeadReportingStatusModelImpl.class)
public interface LeadReportingStatusModel {
	Timestamp[] getStartingPoint() throws PonyException;
	void migrateData(Timestamp startTime) throws PonyException;
	void setEndTime(Timestamp endTime);
}
