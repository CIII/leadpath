package com.pony.leadtypes;

import com.pony.lead.Lead;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 10:12 PM
 */
public class PaydayLead extends Lead {
	private static final Log LOG = LogFactory.getLog(PaydayLead.class);
	
    private String email, firstName, lastName, homePhone, address, address2, state, city, zipCode, timeAtAddress, socialSecurityNumber, driversLicenseNumber, driversLicenseState, bankName, accountType, routingNumber, accountNumber, incomeSource, companyName, jobTitle, timeAtEmployer, workPhone, payFrequency, monthlyIncome;
    private boolean directDeposit, activeMilitary;
    private Date dateOfBirth, nextPayDate, followingPayDate;

    private final int status;
    private Long arrivalId;

    private PaydayLead(ResultSet rs) throws SQLException {
        //TODO: this wouldn't work
        super(rs.getLong("id"), null, null, null, null);

        arrivalId = rs.getLong("arrival_id");
        email = rs.getString("email");
        firstName = rs.getString("first_name");
        lastName = rs.getString("last_name");
        homePhone = rs.getString("home_phone");
        address = rs.getString("address");
        address2 = rs.getString("address2");
        state = rs.getString("state");
        city = rs.getString("city");
        zipCode = rs.getString("zip_code");
        timeAtAddress = rs.getString("time_at_address");
        socialSecurityNumber = "";
        driversLicenseNumber = rs.getString("drivers_license_number");
        driversLicenseState = rs.getString("drivers_license_state");
        bankName = rs.getString("bank_name");
        accountType = rs.getString("account_type");
        routingNumber = rs.getString("routing_number");
        accountNumber = "";
        incomeSource = rs.getString("income_source");
        companyName = rs.getString("company_name");
        jobTitle = rs.getString("job_title");
        timeAtEmployer = rs.getString("time_at_employer");
        workPhone = rs.getString("work_phone");
        payFrequency = rs.getString("pay_frequency");
        monthlyIncome = rs.getString("monthly_income");

        directDeposit = rs.getBoolean("direct_deposit");
        activeMilitary = rs.getBoolean("active_military");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String birth = rs.getString("date_of_birth");
            dateOfBirth = birth == null ? null : df.parse(birth);

            String nextDate = rs.getString("next_pay_date");
            nextPayDate = nextDate == null ? null : df.parse(nextDate);

            String followingDay = rs.getString("following_pay_date");
            followingPayDate = followingDay == null ? null : df.parse(followingDay);
        }
        catch (ParseException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        status = rs.getInt("status");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getState() {
        return state == null ? null : state.toUpperCase();
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getTimeAtAddress() {
        return timeAtAddress;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public String getDriversLicenseState() {
        return driversLicenseState == null ? null : driversLicenseState.toUpperCase();
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getTimeAtEmployer() {
        return timeAtEmployer;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getPayFrequency() {
        return payFrequency;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public boolean isDirectDeposit() {
        return directDeposit;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getNextPayDate() {
        return nextPayDate;
    }

    public Date getFollowingPayDate() {
        return followingPayDate;
    }

    public static PaydayLead create(ResultSet rs) throws SQLException {
        return new PaydayLead(rs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaydayLead that = (PaydayLead) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "PaydayLead{id=" + getId() +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    public Map<String, String> toMap() {
        Map m = new HashMap<String, String>();

        m.put("email", email);
        m.put("first_name", firstName);
        m.put("last_name", lastName);
        return m;
    }

    public Long getArrivalId() {
        return arrivalId;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isActiveMilitary() {
        return activeMilitary;
    }
}

/*
			<div id="personal_information">
				<div class="form_row">
					<div class="form_row_half">
						<label id="first_name_label">First Name</label>
						<input type="text" name="first_name" class="form_input_half"/>
					</div>

					<div class="form_row_half">
						<label id="last_name_label">Last Name</label>
						<input type="text" name="last_name" class="form_input_half"/>
					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="email_label">Email</label>

						<input type="text" name="email" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="home_phone_label">Home Phone</label>
						<input type="text" name="home_phone" class="form_input_half"/>
					</div>
				</div>
				<div class="form_row">

					<div class="form_row_half">
						<label id="address_label">Address</label>
						<input type="text" name="address" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="address2_label">Address2</label>
						<input type="text" name="address2" class="form_input_half"/>
					</div>

				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="state_label">State</label>
						<select name="state" class="form_input_half">
							<option value="-1">Select One</option>
							<option value='ak' >Alaska</option>

							<option value='al' >Alabama</option>
							<option value='az' >Arizona</option>
							<option value='ar' >Arkansas</option>
							<option value='ca' >California</option>
							<option value='co' >Colorado</option>
							<option value='ct' >Connecticut</option>

							<option value='de' >Delaware</option>
							<option value='dc' >District of Columbia</option>
							<option value='fl' >Florida</option>
							<option value='ga' >Georgia</option>
							<option value='hi' >Hawaii</option>
							<option value='id' >Idaho</option>

							<option value='il' >Illinois</option>
							<option value='in' >Indiana</option>
							<option value='ia' >Iowa</option>
							<option value='ks' >Kansas</option>
							<option value='ky' >Kentucky</option>
							<option value='la' >Louisiana</option>

							<option value='me' >Maine</option>
							<option value='md' >Maryland</option>
							<option value='ma' >Massachusetts</option>
							<option value='mi' >Michigan</option>
							<option value='mn' >Minnesota</option>
							<option value='ms' >Mississippi</option>

							<option value='mo' >Missouri</option>
							<option value='mt' >Montana</option>
							<option value='ne' >Nebraska</option>
							<option value='nv' >Nevada</option>
							<option value='nh' >New Hampshire</option>
							<option value='nj' >New Jersey</option>

							<option value='nm' >New Mexico</option>
							<option value='ny' >New York</option>
							<option value='nc' >North Carolina</option>
							<option value='nd' >North Dakota</option>
							<option value='oh' >Ohio</option>
							<option value='ok' >Oklahoma</option>

							<option value='or' >Oregon</option>
							<option value='pa' >Pennsylvania</option>
							<option value='ri' >Rhode Island</option>
							<option value='sc' >South Carolina</option>
							<option value='sd' >South Dakota</option>
							<option value='tn' >Tennessee</option>

							<option value='tx' >Texas</option>
							<option value='ut' >Utah</option>
							<option value='vt' >Vermont</option>
							<option value='va' >Virginia</option>
							<option value='wa' >Washington</option>
							<option value='wv' >West Virginia</option>

							<option value='wi' >Wisconsin</option>
							<option value='wy' >Wyoming</option>
						</select>
					</div>
					<div class="form_row_half">
						<label id="city_label">City</label>
						<input type="text" name="city" class="form_input_half"/>

					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="zip_code_label">Zip Code</label>
						<input type="text" name="zip_code" class="form_input_half"/>
					</div>
					<div class="form_row_half">

						<label id="time_at_address_label">Time At Address</label>
						<select name="time_at_address" class="form_input_half">
							<option value="-1">Select One</option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>

							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>

							<option value="9">9</option>
							<option value="10">10+</option>
						</select>					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="date_of_birth_label">Date Of Birth</label>

						<input type="text" name="date_of_birth" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="social_security_number_label">Social Security Number</label>
						<input type="text" name="social_security_number" class="form_input_half"/>
					</div>
				</div>
				<div class="form_row">

					<div class="form_row_half">
						<label id="drivers_license_number_label">Driver's License Number/State ID Number</label>
						<input type="text" name="drivers_license_number" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="drivers_license_state_label">Driver's License State/State ID State</label>
						<select name="drivers_license_state" class="form_input_half">
							<option value="-1">Select One</option>

							<option value='ak' >Alaska</option>
							<option value='al' >Alabama</option>
							<option value='az' >Arizona</option>
							<option value='ar' >Arkansas</option>
							<option value='ca' >California</option>
							<option value='co' >Colorado</option>

							<option value='ct' >Connecticut</option>
							<option value='de' >Delaware</option>
							<option value='dc' >District of Columbia</option>
							<option value='fl' >Florida</option>
							<option value='ga' >Georgia</option>
							<option value='hi' >Hawaii</option>

							<option value='id' >Idaho</option>
							<option value='il' >Illinois</option>
							<option value='in' >Indiana</option>
							<option value='ia' >Iowa</option>
							<option value='ks' >Kansas</option>
							<option value='ky' >Kentucky</option>

							<option value='la' >Louisiana</option>
							<option value='me' >Maine</option>
							<option value='md' >Maryland</option>
							<option value='ma' >Massachusetts</option>
							<option value='mi' >Michigan</option>
							<option value='mn' >Minnesota</option>

							<option value='ms' >Mississippi</option>
							<option value='mo' >Missouri</option>
							<option value='mt' >Montana</option>
							<option value='ne' >Nebraska</option>
							<option value='nv' >Nevada</option>
							<option value='nh' >New Hampshire</option>

							<option value='nj' >New Jersey</option>
							<option value='nm' >New Mexico</option>
							<option value='ny' >New York</option>
							<option value='nc' >North Carolina</option>
							<option value='nd' >North Dakota</option>
							<option value='oh' >Ohio</option>

							<option value='ok' >Oklahoma</option>
							<option value='or' >Oregon</option>
							<option value='pa' >Pennsylvania</option>
							<option value='ri' >Rhode Island</option>
							<option value='sc' >South Carolina</option>
							<option value='sd' >South Dakota</option>

							<option value='tn' >Tennessee</option>
							<option value='tx' >Texas</option>
							<option value='ut' >Utah</option>
							<option value='vt' >Vermont</option>
							<option value='va' >Virginia</option>
							<option value='wa' >Washington</option>

							<option value='wv' >West Virginia</option>
							<option value='wi' >Wisconsin</option>
							<option value='wy' >Wyoming</option>
						</select>
					</div>
				</div>
			</div>


// -------------------------------------------------------
			<!-- Bank Information-->

			<div id="bank_information">
				<div class="form_row">
					<div class="form_row_half">
						<label id="bank_name_label">Bank Name</label>
						<input type="text" name="bank_name" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="account_type_label">Account Type</label>

						<select name="account_type" class="form_input_half">
							<option value="-1">Select One</option>
							<option value="checking">Checking</option>
							<option value="savings">Savings</option>
						</select>
					</div>
				<div class="form_row">
					<div class="form_row_half">

						<label id="routing_number_label">ABA/Routing Number</label>
						<input type="text" name="routing_number" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="account_number_label">Account Number</label>
						<input type="text" name="account_number" class="form_input_half"/>
					</div>
				</div>

			</div>

// -------------------------------------------------------

			<!-- Employment Information-->
			<div id="employment_information">
				<div class="form_row">
					<div class="form_row_half">
						<label id="income_source_label">Primary Income Source</label>
						<input type="text" name="income_source" class="form_input_half"/>
					</div>

					<div class="form_row_half">
						<label id="direct_deposit_label">Direct Deposit</label>
						<select name="directDeposit" class="form_input_half">
							<option value="-1">Select One</option>
							<option value="1">True</option>
							<option value="0">False</option>
						</select>

					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="company_name_label">Company Name</label>
						<input type="text" name="company_name" class="form_input_half"/>
					</div>
					<div class="form_row_half">

						<label id="job_title_label">Job Title</label>
						<input type="text" name="job_title" class="form_input_half"/>
					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">
						<label id="time_at_employer_label">Time At Employer</label>
						<select name="time_at_employer" class="form_input_half">

							<option value="-1">Select One</option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>

							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10+</option>

						</select>
					</div>
					<div class="form_row_half">
						<label id="work_phone_label">Work Phone</label>
						<input type="text" name="work_phone" class="form_input_half"/>
					</div>
				</div>
				<div class="form_row">
					<div class="form_row_half">

						<label id="pay_frequency_label">Pay Frequency</label>
						<select name="pay_frequency" class="form_input_half">
							<option value="-1">Select One</option>
							<option value="weekly">Weekly</option>
							<option value="bi_weekly">Every 2 Weeks</option>
							<option value="twice_monthly">Twice Monthly</option>

							<option value="monthly">Monthly</option>
						</select>
					</div>
					<div class="form_row_half">
						<label id="monthly_income_label">Total Monthly Income</label>
						<input type="text" name="monthly_income" class="form_input_half"/>
					</div>
				</div>

				<div class="form_row">
					<div class="form_row_half">
						<label id="next_pay_date_label">Next Pay Date</label>
						<input type="text" name="next_pay_date" id="next_pay_date" class="form_input_half"/>
					</div>
					<div class="form_row_half">
						<label id="following_pay_date_label">Following Pay Date</label>
						<input type="text" name="following_pay_date" id="following_pay_date" class="form_input_half"/>

					</div>
				</div>
			</div>
*/
