package com.tapquality.processors;

import java.util.HashMap;
import java.util.Map;

import com.pony.PonyException;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.sun.mail.iap.Response;
import com.tapquality.processors.WhitepagesComponent.AddressParser;
import com.tapquality.processors.WhitepagesComponent.GeolocationParser;
import com.tapquality.processors.whitepages.AddressCheckResponse;
import com.tapquality.processors.whitepages.EmailCheckResponse;
import com.tapquality.processors.whitepages.Geolocation;
import com.tapquality.processors.whitepages.IpCheckResponse;
import com.tapquality.processors.whitepages.PhoneCheckResponse;
import com.tapquality.processors.whitepages.WhitepagesAddressResponse;
import com.tapquality.processors.whitepages.WhitepagesDAO;
import com.tapquality.processors.whitepages.WhitepagesRequest;
import com.tapquality.processors.whitepages.WhitepagesResponse;

public class WhitepagesComponent implements ProcessingTask {

	private static final String STATE_ATTR = "state";
	private static final String ZIP_ATTR = "zip";
	private static final String CITY_ATTR = "city";
	private static final String STREET_ATTR = "street";
	private static final String IP_ATTR = "ip";
	private static final String PHONE_HOME_ATTR = "phone_home";
	private static final String LAST_NAME_ATTR = "last_name";
	private static final String FIRST_NAME_ATTR = "first_name";
	protected WhitepagesDAO dao;
	protected GeolocationParser geolocationParser;
	protected AddressParser addressParser;
	protected PhoneChecksParser phoneChecksParser;
	protected AddressChecksParser addressChecksParser;
	protected EmailChecksParser emailChecksParser;
	protected IPChecksParser ipChecksParser;
	
	public WhitepagesComponent(WhitepagesDAO dao) {
		this(
				new GeolocationParser(),
				new AddressParser(),
				new PhoneChecksParser(),
				new AddressChecksParser(),
				new EmailChecksParser(),
				new IPChecksParser());
		this.dao = dao;
	}
	
	protected WhitepagesComponent(
			GeolocationParser geolocationParser,
			AddressParser addressParser,
			PhoneChecksParser phoneChecksParser,
			AddressChecksParser addressChecksParser,
			EmailChecksParser emailChecksParser,
			IPChecksParser ipChecksParser) {
		this.geolocationParser = geolocationParser;
		this.addressParser = addressParser;
		this.phoneChecksParser = phoneChecksParser;
		this.addressChecksParser = addressChecksParser;
		this.emailChecksParser = emailChecksParser;
		this.ipChecksParser = ipChecksParser;
	}
	
	@Override
	public IPublisherContext process(IPublisherContext context) throws PublisherException {
		Lead lead = context.getLead();
		if (lead != null) {
			try {
				String firstName = lead.getAttributeValue(FIRST_NAME_ATTR);
				String lastName = lead.getAttributeValue(LAST_NAME_ATTR);
				String phone = lead.getAttributeValue(PHONE_HOME_ATTR);
				String emailAddress = context.getUserProfile() != null ? context.getUserProfile().getEmail() : null;
				String ipAddress = lead.getAttributeValue(IP_ATTR);
				String addressStreet1 = lead.getAttributeValue(STREET_ATTR);
				String addressCity = lead.getAttributeValue(CITY_ATTR);
				String addressPostalCode = lead.getAttributeValue(ZIP_ATTR);
				String addressStateCode = lead.getAttributeValue(STATE_ATTR);
				String addressCountryCode = "US";

				WhitepagesRequest request = new WhitepagesRequest(
						null,
						firstName,
						lastName,
						phone,
						emailAddress,
						ipAddress,
						addressStreet1,
						null,
						addressCity,
						addressPostalCode,
						addressStateCode,
						addressCountryCode);
				
				WhitepagesResponse response = dao.query(request);
				Map<String, String> newAttributes = new HashMap<>();
				if(response.getPhoneResponse() != null) {
					newAttributes = this.phoneChecksParser.parse(newAttributes, this.addressParser, response.getPhoneResponse());
				}
				if(response.getAddressResponse() != null) {
					newAttributes = this.addressChecksParser.parse(newAttributes, response.getAddressResponse());
				}
				if(response.getEmailResponse() != null) {
					newAttributes = this.emailChecksParser.parse(newAttributes, response.getEmailResponse());
				}
				if(response.getIpResponse() != null) {
					newAttributes = this.ipChecksParser.parse(newAttributes, this.geolocationParser, response.getIpResponse());
				}
				lead.mergeAttributes(newAttributes);
			} catch (PonyException e) {
				
			} finally {}
		}
		return context;
	}
	
	public static class PhoneChecksParser {
		protected AddressParser addressParser;
		
		public Map<String, String> parse(Map<String, String> newAttributes, AddressParser addressParser, PhoneCheckResponse response) {
			if(response.isValid() != null) {
				newAttributes.put("phone_checks.is_valid", response.isValid().toString());
			};
			if(response.getPhoneContactScore() != null) {
				newAttributes.put("phone_checks.phone_contact_score", response.getPhoneContactScore().toString());
			}
			if(response.isConnected() != null) {
				newAttributes.put("phone_checks.is_connected", response.isConnected().toString());
			}
			if(response.getPhoneToName() != null) {
				newAttributes.put("phone_checks.phone_to_name", response.getPhoneToName());
			}
			if(response.getSubscriberName() != null) {
				newAttributes.put("phone_checks.subscriber_name", response.getPhoneToName());
			}
			if(response.getSubscriberAgeRange() != null) {
				newAttributes.put("phone_checks.subscriber_age_range", response.getSubscriberAgeRange());
			}
			if(response.getSubscriberGender() != null) {
				newAttributes.put("phone_checks.subscriber_gender", response.getSubscriberGender());
			}
			if(response.getSubscriberAddress() != null) {
				newAttributes = addressParser.parse(newAttributes, "phone_checks.subscriber_address", response.getSubscriberAddress());
			}
			if(response.getCountryCode() != null) {
				newAttributes.put("phone_checks.country_code", response.getCountryCode());
			}
			if(response.isPrepaid() != null) {
				newAttributes.put("phone_checks.is_prepaid", response.isPrepaid().toString());
			}
			if(response.getLineType() != null) {
				newAttributes.put("phone_checks.line_type", response.getLineType());
			}
			if(response.getCarrier() != null) {
				newAttributes.put("phone_checks.carrier", response.getCarrier());
			}
			if(response.isCommercial() != null) {
				newAttributes.put("phone_checks.is_commercial", response.isCommercial().toString());
			}
			return newAttributes;
		}
		
	}
	
	public static class AddressParser {
		public Map<String, String> parse(Map<String, String> newAttributes, String prefix, WhitepagesAddressResponse address) {
			if(address.getStreetLine1() != null) {
				newAttributes.put(prefix + ".street_line_1", address.getStreetLine1());
			}
			if(address.getStreetLine2() != null) {
				newAttributes.put(prefix + ".street_line_2", address.getStreetLine2());
			}
			if(address.getCity() != null) {
				newAttributes.put(prefix + ".city", address.getCity());
			}
			if(address.getPostalCode() != null) {
				newAttributes.put(prefix + ".postal_code", address.getPostalCode());
			}
			if(address.getStateCode() != null) {
				newAttributes.put(prefix + ".state_code", address.getStateCode());
			}
			if(address.getCountryCode() != null) {
				newAttributes.put(prefix + ".country_code", address.getCountryCode());
			}
			return newAttributes;
		}
	}
	
	public static class AddressChecksParser {
		public Map<String, String> parse(Map<String, String> newAttributes, AddressCheckResponse response) {
			if(response.isValid() != null) {
				newAttributes.put("address_checks.is_valid", response.isValid().toString());
			}
			if(response.getAddressContactScore() != null) {
				newAttributes.put("address_checks.address_contact_score", response.getAddressContactScore().toString());
			}
			if(response.isActive() != null) {
				newAttributes.put("address_checks.is_active", response.isActive().toString());
			}
			if(response.getAddressToName() != null) {
				newAttributes.put("address_checks.address_to_name", response.getAddressToName());
			}
			if(response.getResidentName() != null) {
				newAttributes.put("address_checks.resident_name", response.getResidentName());
			}
			if(response.getResidentAgeRange() != null) {
				newAttributes.put("address_checks.resident_age_range", response.getResidentAgeRange());
			}
			if(response.getResidentGender() != null) {
				newAttributes.put("address_checks.resident_gender", response.getResidentGender());
			}
			if(response.getAddressType() != null) {
				newAttributes.put("address_checks.type", response.getAddressType());
			}
			if(response.isCommercial() != null) {
				newAttributes.put("address_checks.is_commercial", response.isCommercial().toString());
			}
			if(response.getResidentPhone() != null) {
				newAttributes.put("address_checks.resident_phone", response.getResidentPhone());
			}
			return newAttributes;
		}
	}
	
	public static class EmailChecksParser {
		public Map<String, String> parse(Map<String, String> newAttributes, EmailCheckResponse response) {
			if(response.isValid() != null) {
				newAttributes.put("email_address_checks.is_valid", response.isValid().toString());
			}
			if(response.getEmailContactScore() != null) {
				newAttributes.put("email_address_checks.email_contact_score", response.getEmailContactScore().toString());
			}
			if(response.isDisposable() != null) {
				newAttributes.put("email_address_checks.is_disposable", response.isDisposable().toString());
			}
			if(response.getEmailToName() != null) {
				newAttributes.put("email_address_checks.email_to_name", response.getEmailToName());
			}
			if(response.getRegisteredName() != null) {
				newAttributes.put("email_address_checks.registered_name", response.getRegisteredName());
			}
			return newAttributes;
		}
	}
	
	public static class IPChecksParser {
		public Map<String, String> parse(Map<String, String> newAttributes, GeolocationParser geolocationParser, IpCheckResponse response) {
			if(response.isValid() != null) {
				newAttributes.put("ip_address_checks.is_valid", response.isValid().toString());
			}
			if(response.isProxy() != null) {
				newAttributes.put("ip_address_checks.is_proxy", response.isProxy().toString());
			}
			if(response.getGeolocation() != null) {
				newAttributes = geolocationParser.parse(newAttributes, "ip_address_checks.geolocation", response.getGeolocation());
			}
			if(response.getDistanceFromAddress() != null) {
				newAttributes.put("ip_address_checks.distance_from_address", response.getDistanceFromAddress().toString());
			}
			if(response.getDistanceFromPhone() != null) {
				newAttributes.put("ip_address_checks.distance_from_phone", response.getDistanceFromPhone().toString());
			}
			if(response.getConnectionType() != null) {
				newAttributes.put("ip_address_checks.connection_type", response.getConnectionType());
			}
			return newAttributes;
		}
	}
	
	public static class GeolocationParser {
		public Map<String, String> parse(Map<String, String> newAttributes, String prefix, Geolocation geolocation) {
			if(geolocation.getPostalCode() != null) {
				newAttributes.put(prefix + ".postal_code", geolocation.getPostalCode());
			}
			if(geolocation.getCityName() != null) {
				newAttributes.put(prefix + ".city_name", geolocation.getCityName());
			}
			if(geolocation.getSubdivision() != null) {
				newAttributes.put(prefix + ".subdivision", geolocation.getSubdivision());
			}
			if(geolocation.getCountryName() != null) {
				newAttributes.put(prefix + ".country_name", geolocation.getCountryName());
			}
			if(geolocation.getCountryCode() != null) {
				newAttributes.put(prefix + ".country_code", geolocation.getCountryCode());
			}
			if(geolocation.getContinentCode() != null) {
				newAttributes.put(prefix + ".continent_code", geolocation.getContinentCode());
			}
			return newAttributes;
		}
	}



}
