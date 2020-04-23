package com.tapquality.processors.whitepages;

public class WhitepagesRequest {
	protected String name;
	protected String firstName;
	protected String lastName;
	protected String phone;
	protected String emailAddress;
	protected String ipAddress;
	protected String addressStreet1;
	protected String addressStreet2;
	protected String addressCity;
	protected String addressPostalCode;
	protected String addressStateCode;
	protected String addressCountryCode;
	public WhitepagesRequest(String name, String firstName, String lastName, String phone, String emailAddress,
			String ipAddress, String addressStreet1, String addressStreet2, String addressCity,
			String addressPostalCode, String addressStateCode, String addressCountryCode) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.emailAddress = emailAddress;
		this.ipAddress = ipAddress;
		this.addressStreet1 = addressStreet1;
		this.addressStreet2 = addressStreet2;
		this.addressCity = addressCity;
		this.addressPostalCode = addressPostalCode;
		this.addressStateCode = addressStateCode;
		this.addressCountryCode = addressCountryCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAddressStreet1() {
		return addressStreet1;
	}
	public void setAddressStreet1(String addressStreet1) {
		this.addressStreet1 = addressStreet1;
	}
	public String getAddressStreet2() {
		return addressStreet2;
	}
	public void setAddressStreet2(String addressStreet2) {
		this.addressStreet2 = addressStreet2;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressPostalCode() {
		return addressPostalCode;
	}
	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}
	public String getAddressStateCode() {
		return addressStateCode;
	}
	public void setAddressStateCode(String addressStateCode) {
		this.addressStateCode = addressStateCode;
	}
	public String getAddressCountryCode() {
		return addressCountryCode;
	}
	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}
}
