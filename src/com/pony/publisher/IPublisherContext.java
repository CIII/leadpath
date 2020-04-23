package com.pony.publisher;

import java.util.List;
import java.util.Map;

import com.pony.core.TestContext;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.validation.ValidationResponse;

public interface IPublisherContext {

	boolean isEmailDup();

	boolean isPublisherDup();

	PublisherChannel getChannel();

	LeadType getLeadType();

	Lead getLead();

	UserProfile getUserProfile();

	Arrival getArrival();

	List<Long> getBuyerIds();

	Long getMessageId();

	void setMessageId(Long messageId);

	Map<String, String> getParams();

	boolean isPing();

	boolean isPost();

	boolean isRePost();

	boolean isValidation();

	boolean isPoll();

	boolean isEmail();

	boolean isSubscription();

	boolean isResend();

	boolean isForm();

	boolean waitForResponse();

	Publisher getPublisher();

	PublisherList getPublisherList();

	boolean isTest();

	TestContext getTestContext();

	boolean refreshCache();

	Long getLeadId();

	void addErrorCode(String code, String message);

	PublisherException getException();

	void setValidationResponse(ValidationResponse validationResponse);

	ValidationResponse getValidationResponse();

	void setPublisherResponse(PublisherResponse response);

	PublisherResponse getPublisherResponse();

	void setLead(Lead lead);

}
