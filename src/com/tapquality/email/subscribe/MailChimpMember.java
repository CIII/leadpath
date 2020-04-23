package com.tapquality.email.subscribe;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * POJO to encapsulate a "member" in MailChimp
 * 
 * @author dmcguire
 *
 */
@JsonInclude(Include.NON_NULL)
public class MailChimpMember {
	@JsonProperty("email_address")
	private String emailAddress;
	@JsonProperty("email_type")
	private String emailType;
	@JsonProperty("status_if_new")
	private MemberStatus statusIfNew;
	private MemberStatus status;			
	private String language;		// subscriber's language
	private Boolean vip;			// VIP status
	@JsonProperty("ip_signup")
	private String signupIp;		// IP address the subscriber signed up from
	@JsonProperty("timestamp_signup")
	private String signupTs;		// The date and time the subscrier signed up for the list.
	@JsonProperty("ip_opt")
	private String optIp;			// The IP address the subscriber used to confirm their opt in status
	@JsonProperty("timestamp_opt")
	private String optTs;			// The date and time th esubscribe confirmed their opt in status
	
	/**
	 * Represents the possible statuses for a member in MailChimp
	 * @author dmcguire
	 *
	 */
	public enum MemberStatus{
		Subscribed("subscribed"),
		Unsubscribed("unsubscribed"),
		Cleaned("cleaned"),
		Pending("pending"),
		Transactional("transactional");
		
		private final String name;
		private MemberStatus(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		@Override
		@JsonValue
		public String toString(){
			return getName();
		}
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
		this.statusIfNew = status;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public String getSignupIp() {
		return signupIp;
	}

	public void setSignupIp(String signupIp) {
		this.signupIp = signupIp;
	}

	public String getSignupTs() {
		return signupTs;
	}

	public void setSignupTs(String signupTs) {
		this.signupTs = signupTs;
	}

	public String getOptIp() {
		return optIp;
	}

	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}

	public String getOptTs() {
		return optTs;
	}

	public void setOptTs(String optTs) {
		this.optTs = optTs;
	}
}
