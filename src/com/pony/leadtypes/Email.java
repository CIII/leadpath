package com.pony.leadtypes;

import com.pony.email.validation.AddressValidator;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.AttributeModel;
import com.pony.models.LeadModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 10:15 PM
 */
public class Email extends LeadType {

    static {
        EXCLUDED_ATTRIBUTES.add("ref");
        EXCLUDED_ATTRIBUTES.add("domtok");
        EXCLUDED_ATTRIBUTES.add("username");
        EXCLUDED_ATTRIBUTES.add("password");
        EXCLUDED_ATTRIBUTES.add("email");
        EXCLUDED_ATTRIBUTES.add("listid");
    }

    public Email(Long id) {
        super(id);
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        // parse the request for lead type attributes and extract their values into a lead (EmailLead !!)
        // the params are in the query
        // examples:
        //source_url=welendcash.com&ipaddy=76.101.120.154&username=popmark&password=thomasb&listid=40&email=johngrieco1@hotmail.com&fname=&lname=grieco&signup_date=2012-07-01 00:00:00
        //username=leadkarma&password=doublenickels&fname=Danielle&state=NV&email=Jadabean08%40yahoo.com&source_url=insurancedesk.com&dob=01-04-1967&listid=12346&zip=89149&vertical=healthinsurance&lname=Joren&ipaddy=76.3.147.161&city=Las+Vegas&traffic_type=Affiliate+Display
        Map<String, String> attributes = parseLeadAttributes(request, params);
        return EmailLead.create(getId(), userProfile, arrival, attributes);
    }

    @Override
    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException, NamingException {
        if (!(lead instanceof EmailLead)) {
            throw new IllegalArgumentException("not an email lead");
        }
        if (userProfile.getId() == null) {
            throw new IllegalArgumentException("requires user_profile to be stored first!");
        }

        Long id = LeadModel.persist(lead);
        Lead emailLead = EmailLead.create(id, getId(), userProfile.getId(), arrival.getId(), lead.toMap());
        AttributeModel.persistProfileAttributes(userProfile, lead);

        return emailLead;
    }

    @Override
    public Lead findLead(Long leadId) {
        // TODO
        return null;
    }

    @Override
    public ValidationResponse validate(IPublisherContext context) throws ValidationException {
        if (context.isEmail()) {
            if (context.isEmailDup()) {
                // email dups are being checked when we parse the original request
                return ValidationResponse.DUP;
            }

            UserProfile userProfile = context.getUserProfile();
            Publisher publisher = context.getPublisher();

            if (!userProfile.getMailHost().isMxValidated()) {
                return ValidationResponse.INVALID_MX;
            }

            return validate(userProfile.getId(), userProfile.getEmail(), publisher.getId(), publisher.isExtendedValidation());
        }

        return ValidationResponse.NOOP;
    }

    /**
     * validation for resends (use simpler checks (bounce, unsub,...), since this is not the first time we send to this address)
     *
     * @param userProfileId
     * @param email
     * @param publisherId
     * @return
     * @throws ValidationException
     */
    public static boolean isValid(Long userProfileId, String email, Long publisherId) throws ValidationException {
        return validate(userProfileId, email, publisherId, false).isValid();
    }

    private static ValidationResponse validate(Long userProfileId, String email, Long publisherId, boolean withExtendedValidation) throws ValidationException {
        try {
            if (!AddressValidator.isValid(email, false)) {
                return ValidationResponse.INVALID_EMAIL;
            }

            // check email specifics
            if (bounced(userProfileId)) {
                return ValidationResponse.BOUNCED;
            }

            if (unsubscribed(userProfileId)) {
                return ValidationResponse.UNSUBSCRIBED;
            }

            if (complained(userProfileId)) {
                return ValidationResponse.COMPLAINT;
            }

            if (suppressed(email, publisherId)) {
                return ValidationResponse.SUPPRESSED;
            }

            if (knownExternalTrap(email)) {
                return ValidationResponse.KNOWN_TRAP;
            }

//            if (withExtendedValidation) {
//                if (!BriteVerifyEmailValidator.isValid(email)) {
//                    return ValidationResponse.INVALID_EMAIL;
//                }
//            }
        }
        catch (SQLException e) {
            throw new ValidationException(e);
        }
        catch (NamingException e) {
            throw new ValidationException(e);
        }

        return ValidationResponse.NOOP;
    }

    public String toString() {
        return "LeadType:Email(" + getId() + ")";
    }

    public static boolean knownExternalTrap(String email) throws NamingException, SQLException {
        return UserProfileModelImpl.isKnownTrapStatic(email);
    }

    private static boolean suppressed(String email, Long publisherId) throws NamingException, SQLException {
        return UserProfileModelImpl.isSuppressedStatic(email, publisherId);
    }

    private static boolean complained(Long userProfileId) throws NamingException, SQLException {
        return UserProfileModelImpl.hasComplainedStatic(userProfileId);
    }

    public static boolean bounced(Long userProfileId) throws NamingException, SQLException {
        return UserProfileModelImpl.hasBouncedStatic(userProfileId);
    }

    private static boolean unsubscribed(Long userProfileId) throws NamingException, SQLException {
        return UserProfileModelImpl.hasUnsubscribedStatic(userProfileId);
    }
}
