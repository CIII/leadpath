package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStep;
import com.pony.form.FormStepAttribute;
import com.pony.form.FormStepGroup;
import com.pony.form.SelectValue;
import com.pony.lead.LeadType;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;
import com.pony.publisher.PublisherListMember;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Populate the form meta data for the Discovery Advisor site (small business registrations)
 * delete from form_step_attribute_filters;
 * delete from form_step_filters;
 * delete from form_step_attributes;
 * delete from form_select_filters;
 * delete from form_step_group_filters;
 * delete from select_values;
 * delete from form_step_groups;
 * delete from form_steps;
 * <p/>
 * <p/>
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 12:08 PM
 */
public class DiscoveryAdvisorFormData {
	private static final Log LOG = LogFactory.getLog(DiscoveryAdvisorFormData.class);

    public static final String PUBLISHER_NAME = "ArbVentures.com";
    public static final String FORM_NAME = "DiscoveryAdvisor Business Registration Form";
    public static final String PUBLISHER_USER_NAME = "greg";
    public static final String PUBLISHER_USER_PWD = "scooTer100";
    public static final String PUBLISHER_LIST_EXT_ID = "DA-1";


    public static void main(String[] args) {

        // ArbVentures Site: Registrations for businesses interested in working with us
        createData();
    }

    public static void createData() {
        try {
            Publisher publisher = PublisherModel.findByNameAndPwd(PUBLISHER_USER_NAME, PUBLISHER_USER_PWD);
            if (publisher == null) {
                publisher = PublisherModel.create(PUBLISHER_NAME, PUBLISHER_USER_NAME, PUBLISHER_USER_PWD, true);
            }

            LeadType leadType = LeadType.find(FORM_NAME);
            if (leadType == null) {
                leadType = LeadType.create(FORM_NAME);
            }

            // create a publisher list so we can address this 'channel'
            PublisherList publisherList = PublisherListModel.findByExternalId(PUBLISHER_LIST_EXT_ID);
            if (publisherList == null) {
                publisherList = PublisherListModel.create(leadType, FORM_NAME, PUBLISHER_LIST_EXT_ID);
            }

            // make sure we're allowed to send data to that list
            PublisherListMember listMember = PublisherListMemberModel.findByPublisherAndList(publisher.getId(), publisherList.getId());
            if (listMember == null) {
                PublisherListMemberModel.create(publisher, publisherList);
            }

            // -- ================================================
            // :Quick Pass:

            Form form = FormModel.findByPublisherListAndName(publisherList, FORM_NAME);
            if (form == null) {
                form = FormModel.create(FORM_NAME, "Tell us about your business", "Get Reliable Help Now", publisherList, publisher);
            }

            // now for the questions

            // ====== step 1, group 1 ====================================
            FormStep step1 = FormStepModel.findByFormAndName(form, "Tell us about your business");
            if (step1 == null) {
                step1 = FormStepModel.create(form, "Tell us about your business", 1, true, true);
            }

            FormStepGroup step1Group1 = FormStepGroupModel.findByFormStepAndName(step1, "general business");

            if (step1Group1 == null) {
                step1Group1 = FormStepGroupModel.create(step1, "general business", 1, true);
            }

            // How many years have you been in business?
            FormStepAttribute fsaYearsInBusiness = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group1, "years_in_business");
            if (fsaYearsInBusiness == null) {
                fsaYearsInBusiness = FormStepAttributeModel.createAll(form, step1Group1, "years_in_business", "integer", 3, true, "How many years have you been in business?", 1, true, false, null, null, "Please provide a valid number");
            }

            // What industry are you in?
            Map<String, SelectValue> industrySelectValues;
            FormStepAttribute fsaIndustry = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group1, "industry");
            if (fsaIndustry == null) {
                fsaIndustry = FormStepAttributeModel.createAll(form, step1Group1, "industry", "select", 99, true, "What industry are you in?", 2, true, true, null, null, "Please select an industry from the list");

                // select values for industry
                // create the select values for this new attribute
                Map<String, String> keyValues = new HashMap<String, String>();
                keyValues.put("1", "Manufacturing");
                keyValues.put("2", "Professional Office");
                keyValues.put("3", "Restaurants");
                keyValues.put("4", "Retail");
                keyValues.put("5", "Service Providers");
                keyValues.put("6", "Specialty Trade Contractors");
                keyValues.put("7", "Transportation & Trucking");
                keyValues.put("8", "Wholesale & Warehousing");
                keyValues.put("9", "Other");

                industrySelectValues = SelectValueModel.createAll(fsaIndustry, keyValues);
            }
            else {
                // look up the select values for this attribute
                industrySelectValues = SelectValueModel.findAll(fsaIndustry);
            }

            // what business type?
            Map<String, SelectValue> businessTypeSelectValues = null;
            FormStepAttribute fsaBusinessType = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group1, "business_type");
            if (fsaBusinessType == null) {
                fsaBusinessType = FormStepAttributeModel.createAll(form, step1Group1, "business_type", "text", 99, true, "What type of business?", 3, true, false, null, null, "Please select a type from the list");

                // select values for business type

                // ==============
                // ------ sync for Manufacturing
                Map<String, String> keyValues = new HashMap<String, String>();
                keyValues.put("ANY", "ANY");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
                SelectValueModel.preSelect(businessTypeSelectValues.get("ANY"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("1"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // ------ sync for <option value="2">Professional Office</option>
                keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "select business type");
                keyValues.put("8810", "Accountants - in office");
                keyValues.put("8803", "Accountants - travel");
                keyValues.put("8810", "Advertising Agency");
                keyValues.put("8601", "Architect");
                keyValues.put("8810", "Bank");
                keyValues.put("8859", "Computer Programming");
                keyValues.put("8839", "Dentists");
                keyValues.put("8601", "Engineer");
                keyValues.put("8810", "Graphic Design");
                keyValues.put("8822", "Insurance Agent/Broker");
                keyValues.put("8820", "Law Office");
                keyValues.put("8838", "Museums");
                keyValues.put("8013", "Optometrists");
                keyValues.put("8810", "Other Professional Office");
                keyValues.put("8834", "Physical/Occupational Therapist");
                keyValues.put("8834", "Physicians");
                keyValues.put("9015", "Property Management - non-professional");
                keyValues.put("8740", "Property Management - professional");
                keyValues.put("8741", "Real Estate Agents");
                keyValues.put("8742", "Sales Office");
                keyValues.put("8831", "Veterinarians");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
//                SelectValueModel.preSelect(businessTypeSelectValues.get("undefined"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("2"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 3
//                keyValues.put("3", "Restaurants");
                keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "select business type");
                keyValues.put("2003", "Bagel Shop - bakery");
                keyValues.put("8017", "Bagel Shop - no food service");
                keyValues.put("2003", "Bakery");
                keyValues.put("8017", "Coffee Shop / Cyber Cafe");
                keyValues.put("8006", "Delicatessen");
                keyValues.put("2003", "Donut Shop (baking on premises)");
                keyValues.put("9079", "Donut Shop (no baking on premises)");
                keyValues.put("9079", "Fast Food Restaurant (Counter Service)");
                keyValues.put("8078", "Ice Cream Shop");
                keyValues.put("9999", "Other Restaurants");
                keyValues.put("9079", "Restaurant (With Table Service)");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
//                SelectValueModel.preSelect(businessTypeSelectValues.get("undefined"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("3"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 4
//                keyValues.put("4", "Retail");
                keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "select business type");
                keyValues.put("8046", "Automobile Parts &amp; Accessories");
                keyValues.put("2003", "Bakery");
                keyValues.put("8810", "Bank");
                keyValues.put("8071", "Book or Music Store");
                keyValues.put("8008", "Clothing Stores");
                keyValues.put("8061", "Convenience Store");
                keyValues.put("8039", "Department Store");
                keyValues.put("8017", "Drug Store");
                keyValues.put("2589", "Dry Cleaning");
                keyValues.put("8017", "Electronics Store");
                keyValues.put("8031", "Fish, Meat or Poultry Retail Store");
                keyValues.put("8042", "Floor Covering Store");
                keyValues.put("8001", "Florists");
                keyValues.put("8015", "Furniture Store");
                keyValues.put("8017", "Gift Shops");
                keyValues.put("8017", "Hardware Store");
                keyValues.put("8078", "Ice Cream Store");
                keyValues.put("8013", "Jewelry Store");
                keyValues.put("8017", "Laundromat - Self Service");
                keyValues.put("8004", "Lawn &amp; Garden Center");
                keyValues.put("8060", "Liquor Stores");
                keyValues.put("8064", "Office Supply Store");
                keyValues.put("8013", "Optical Store");
                keyValues.put("8017", "Other Retail Store");
                keyValues.put("8065", "Paint/Wallpaper Store");
                keyValues.put("8017", "Pet &amp; Pet Supply Store");
                keyValues.put("8017", "Photo Supply Store");
                keyValues.put("8111", "Plumbing Supply Store");
                keyValues.put("8008", "Shoe Stores");
                keyValues.put("8017", "Sporting Goods Store");
                keyValues.put("8006", "Supermarket");
                keyValues.put("8017", "Tailors");
                keyValues.put("8017", "Variety/Dollar Store");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
//                SelectValueModel.preSelect(businessTypeSelectValues.get("undefined"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("4"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 5
//                keyValues.put("5", "Service Providers");
                keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "select business type");
                keyValues.put("7605", "Alarm Installation &amp; Service");
                keyValues.put("9519", "Appliance Repair");
                keyValues.put("8393", "Auto Body Repair Shop");
                keyValues.put("8391", "Automobile Rental");
                keyValues.put("8389", "Automobile Service &amp; Repair");
                keyValues.put("9586", "Barber Shop");
                keyValues.put("9586", "Beauty Salon");
                keyValues.put("9092", "Bowling Lanes");
                keyValues.put("8387", "Car Wash");
                keyValues.put("2584", "Carpet &amp; Upholstery Cleaning");
                keyValues.put("4299", "Commercial Printer");
                keyValues.put("5191", "Computer Repair");
                keyValues.put("9060", "Country Club / Golf Courses");
                keyValues.put("2589", "Dry Cleaning");
                keyValues.put("9155", "Entertainers &amp; Musicians");
                keyValues.put("9053", "Fitness Center");
                keyValues.put("9620", "Funeral Homes");
                keyValues.put("9050", "Hotel/Motel");
                keyValues.put("9008", "Janitorial Service");
                keyValues.put("8831", "Kennels/Doggy Day Care");
                keyValues.put("42", "Landscape Gardener");
                keyValues.put("9015", "Lawn Care/Maintenance");
                keyValues.put("4304", "Newspaper Publishing");
                keyValues.put("9999", "Other Service Provider");
                keyValues.put("8831", "Pet Groomers");
                keyValues.put("8017", "Photo Processing");
                keyValues.put("4361", "Photographer");
                keyValues.put("8019", "Quick Printer/Copy Shop");
                keyValues.put("9101", "Schools - non-professional");
                keyValues.put("8868", "Schools - professional");
                keyValues.put("7605", "Security System Installation");
                keyValues.put("8017", "Self-Serve Gas Station");
                keyValues.put("9402", "Snowplowing");
                keyValues.put("9999", "Social Services");
                keyValues.put("8810", "Tax Preparation Service");
                keyValues.put("8810", "Travel Agency");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
//                SelectValueModel.preSelect(businessTypeSelectValues.get("undefined"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("5"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 6
//                keyValues.put("6", "Specialty Trade Contractors");
                keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "select business type");
                keyValues.put("5403", "Carpentry - Commercial");
                keyValues.put("5645", "Carpentry - Residential");
                keyValues.put("9521", "Carpet, Vinyl or Linoleum Tile Installation");
                keyValues.put("5205", "Concrete &amp; Asphalt Work");
                keyValues.put("5201", "Driveway Paving or Repaving");
                keyValues.put("5190", "Electrical Contractors");
                keyValues.put("5403", "Finish Carpentry Contractors");
                keyValues.put("5146", "Furniture/Fixture Installation");
                keyValues.put("5538", "HVAC");
                keyValues.put("5403", "Hardwood Floor Installation or Refinishing");
                keyValues.put("9008", "Janitorial Service");
                keyValues.put("42", "Landscape Gardener");
                keyValues.put("5028", "Masonry Contractors");
                keyValues.put("9999", "Other Specialty Trade Contractors");
                keyValues.put("5474", "Painting &amp; Wall Covering Contractors");
                keyValues.put("5485", "Plastering");
                keyValues.put("5183", "Plumbing Contractors");
                keyValues.put("5645", "Siding Contractors (Commercial)");
                keyValues.put("5645", "Siding Contractors (Residential)");
                keyValues.put("9402", "Snowplowing");
                keyValues.put("5348", "Tile, Marble or Stone Contractors");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
//                SelectValueModel.preSelect(businessTypeSelectValues.get("undefined"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("6"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 7
//                keyValues.put("7", "Transportation & Trucking");
                keyValues = new HashMap<String, String>();
                keyValues.put("ANY", "ANY");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
                SelectValueModel.preSelect(businessTypeSelectValues.get("ANY"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("7"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 8
//                keyValues.put("8", "Wholesale & Warehousing");
                keyValues = new HashMap<String, String>();
                keyValues.put("ANY", "ANY");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
                SelectValueModel.preSelect(businessTypeSelectValues.get("ANY"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("8"), fsaBusinessType, businessTypeSelectValues.values());

                // ==============
                // sync option 9
//                keyValues.put("9", "Other");
                keyValues = new HashMap<String, String>();
                keyValues.put("ANY", "ANY");

                businessTypeSelectValues = SelectValueModel.createAll(fsaBusinessType, keyValues);
                SelectValueModel.preSelect(businessTypeSelectValues.get("ANY"));

                // link the filters so that we can filter the options in business type, based on the selected industry
                FormSelectFilterModel.createFilters(form, fsaIndustry, industrySelectValues.get("9"), fsaBusinessType, businessTypeSelectValues.values());
            }
            else {
                // look up the select values for this attribute
                businessTypeSelectValues = SelectValueModel.findAll(fsaBusinessType);
            }

            // ====== step 1, group 2 ====================================
            // Do you have a physical location(retail store, bakery, warehouse, etc.)? Yes, No
            FormStepGroup step1Group2 = FormStepGroupModel.findByFormStepAndName(step1, "business_location");

            if (step1Group2 == null) {
                step1Group2 = FormStepGroupModel.create(step1, "business_location", 2, true);
            }

            FormStepAttribute fsaLocation = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group2, "physical_location");
            if (fsaLocation == null) {
                fsaLocation = FormStepAttributeModel.createAll(form, step1Group2, "physical_location", "select", 1, true, "Do you have a physical location(retail store, bakery, warehouse, etc.)?", 4, true, false, null, null, "Please choose");

                // options : yes, no
                HashMap<String, String> keyValues = new HashMap<String, String>();
//                keyValues.put("undefined", "Please select");
                keyValues.put("1", "Yes");
                keyValues.put("0", "No");
                Map<String, SelectValue> selectOptions = SelectValueModel.createAll(fsaLocation, keyValues);
//                SelectValueModel.preSelect(selectOptions.get("undefined"));
            }

            FormStepAttribute fsaBusinessZip = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group2, "zip_code_of_business");
            if (fsaBusinessZip == null) {
                fsaBusinessZip = FormStepAttributeModel.createAll(form, step1Group2, "zip_code_of_business", "text", 10, false, "What zip code is the business in?", 5, true, false, null, null, "Please provide a valid zip code");
            }

            // Note: skip group 3 for now

            // ====== step 1, group 4: current marketing ====================================
/*
    current marketing:
    -- What are your top 3 marketing problems we can help you with?
        Getting information to my existing customers
        Attracting new customers
        Providing information to mobile users
        I need a website or don’t have time to keep my website updated
        No one visits my website
        Customers wishing to pay via credit card
 */
            Map<String, SelectValue> currentWebSiteSelectOptions = null;
            FormStepGroup step1Group4 = FormStepGroupModel.findByFormStepAndName(step1, "current_marketing");
            if (step1Group4 == null) {
                // business_address by default disabled
                step1Group4 = FormStepGroupModel.create(step1, "current_marketing", 4, true);

                FormStepAttribute fsaCurrentWebsite = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group4, "has_current_website");
                if (fsaCurrentWebsite == null) {
                    fsaCurrentWebsite = FormStepAttributeModel.createAll(form, step1Group4, "has_current_website", "select", 1, false, "Do you currently have a website?", 6, true, true, null, null, "Please select");
                    // options : yes, no
                    HashMap<String, String> keyValues = new HashMap<String, String>();
//                    keyValues.put("undefined", "Please select");
                    keyValues.put("1", "Yes");
                    keyValues.put("0", "No");
                    currentWebSiteSelectOptions = SelectValueModel.createAll(fsaCurrentWebsite, keyValues);
//                    SelectValueModel.preSelect(currentWebSiteSelectOptions.get("undefined"));
                }

                // If Yes: where do you currently host your website?
                // ==> is on group 5

                FormStepAttribute fsaHelp = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group4, "what_help_needed");
                if (fsaHelp == null) {
                    fsaHelp = FormStepAttributeModel.createAll(form, step1Group4, "what_help_needed", "select", 1, false, "What marketing problem can we help you with?", 7, true, false, null, null, null);
                    //TODO: allow multi select
                    // select options
                    HashMap<String, String> keyValues = new HashMap<String, String>();
//                    keyValues.put("undefined", "Please select");
                    keyValues.put("1", "Getting information to my existing customers");
                    keyValues.put("2", "Attracting new customers");
                    keyValues.put("3", "Providing information to mobile users");
                    keyValues.put("4", "I need a website or don’t have time to keep my website updated");
                    keyValues.put("5", "No one visits my website");
                    keyValues.put("6", "Customers wishing to pay via credit card");

                    Map<String, SelectValue> selectOptions = SelectValueModel.createAll(fsaHelp, keyValues);
//                    SelectValueModel.preSelect(selectOptions.get("undefined"));
                }
            }

            // ====== step 1, group 5: current website details ====================================
            FormStepGroup step1Group5 = FormStepGroupModel.findByFormStepAndName(step1, "current_website");
            if (step1Group5 == null) {
                // business_address by default disabled
                step1Group5 = FormStepGroupModel.create(step1, "current_website", 5, false);

                FormStepAttribute fsaCurrentWebsite = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group5, "website_host");
                if (fsaCurrentWebsite == null) {
                    fsaCurrentWebsite = FormStepAttributeModel.createAll(form, step1Group5, "website_host", "text", 1, false, "Where do you currently host your website?", 8, true, false, null, null, null);
                }

                // link filter to fsaCurrentWebsite: if yes, enable this group, if no, disable it
                if (currentWebSiteSelectOptions != null) {
                    // tie this group to the 'yes/no' options of fsaCurrentWebsite
                    FormStepGroupFilterModel.createFilter(form, currentWebSiteSelectOptions.get("1"), step1Group5.getId(), true); // enable on yes
                    FormStepGroupFilterModel.createFilter(form, currentWebSiteSelectOptions.get("0"), step1Group5.getId(), false); // disable on no
                }
            }

            // ====== step 1, group 6: promotions ====================================
            FormStepGroup step1Group6 = FormStepGroupModel.findByFormStepAndName(step1, "promotions");
            if (step1Group6 == null) {
                step1Group6 = FormStepGroupModel.create(step1, "promotions", 6, true);

                FormStepAttribute fsaEmailList = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group6, "current_mailing_list");
                if (fsaEmailList == null) {
                    fsaEmailList = FormStepAttributeModel.createAll(form, step1Group6, "current_mailing_list", "select", 1, false, "Do you have a mailing list for your current customers?", 9, true, false, null, null, "Please select");

                    Map<String, String> keyValues = new HashMap<String, String>();
//                    keyValues.put("undefined", "Please select");
                    keyValues.put("1", "Yes");
                    keyValues.put("0", "No");
                    Map<String, SelectValue> selectOptions = SelectValueModel.createAll(fsaEmailList, keyValues);
//                    SelectValueModel.preSelect(selectOptions.get("undefined"));
                }
            }


            // ====== step 1, group 7: customer profiles ====================================
/*
    Describe your current customers
        home owner[Y-N]
        car owner[ Y-N]
        male-female
        kids-adults
 */
            FormStepGroup step1Group7 = FormStepGroupModel.findByFormStepAndName(step1, "customer_profiles");
            if (step1Group7 == null) {
                // by default disabled
                step1Group7 = FormStepGroupModel.create(step1, "customer_profiles", 7, false);
                //TODO: these options make no sense to me so far ...
            }


            // ====== step 1, group 8: contact ====================================
            FormStepGroup step1Group8 = FormStepGroupModel.findByFormStepAndName(step1, "contact");
            if (step1Group8 == null) {
                // business_address by default disabled
                step1Group8 = FormStepGroupModel.create(step1, "contact", 8, true);

                FormStepAttribute fsa = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group8, "contact_first_name");
                if (fsa == null) {
                    fsa = FormStepAttributeModel.createAll(form, step1Group8, "contact_first_name", "text", 100, true, "Contact First Name", 9, true, false, null, null, null);
                }

                fsa = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group8, "contact_last_name");
                if (fsa == null) {
                    fsa = FormStepAttributeModel.createAll(form, step1Group8, "contact_last_name", "text", 100, true, "Contact Last Name", 10, true, false, null, null, null);
                }

                fsa = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group8, "email");
                if (fsa == null) {
                    fsa = FormStepAttributeModel.createAll(form, step1Group8, "email", "text", 100, true, "Email address we can reach you at", 11, true, false, null, null, null);
                }

                fsa = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group8, "contact_phone");
                if (fsa == null) {
                    fsa = FormStepAttributeModel.createAll(form, step1Group8, "contact_phone", "text", 100, true, "Phone number we can reach you at", 12, true, false, null, null, null);
                }

                fsa = FormStepAttributeModel.findByStepGroupAndAttributeName(form, step1Group8, "contact_phone_type");
                if (fsa == null) {
                    fsa = FormStepAttributeModel.createAll(form, step1Group8, "contact_phone_type", "select", 100, true, "Type of Phone", 13, true, false, null, null, null);

                    Map<String, String> keyValues = new HashMap<String, String>();
                    keyValues.put("0", "Home");
                    keyValues.put("1", "Work");
                    keyValues.put("2", "Mobile");
                    Map<String, SelectValue> selectOptions = SelectValueModel.createAll(fsa, keyValues);
                }
            }
        }
        catch (SQLException e) {
            LOG.error(e);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        finally {

        }

    }
}
