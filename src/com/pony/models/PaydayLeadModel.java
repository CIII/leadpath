package com.pony.models;

import com.pony.lead.LeadType;
import com.pony.leadtypes.Payday;
import com.pony.leadtypes.PaydayLead;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 9:38 PM
 */
public class PaydayLeadModel extends Model {
//    private final String firstName, lastName, email;
//    private final int status;

    private PaydayLead lead;

    private PaydayLeadModel(ResultSet rs) throws SQLException {
        super(rs.getLong("id"));

        lead = PaydayLead.create(rs);
    }

    public static PaydayLeadModel find(Long id) throws NamingException, SQLException {
        Payday leadType = (Payday) LeadType.find("Payday");

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select l.id, email, arrival_id, first_name, last_name, direct_deposit, home_phone, address, address2, state, city, zip_code, time_at_address, drivers_license_number, drivers_license_state, bank_name, account_type, routing_number, income_source, company_name, job_title, time_at_employer, work_phone, pay_frequency, monthly_income, date_of_birth, next_pay_date, following_pay_date, active_military, status from leads l left outer join user_profiles up on l.user_profile_id = up.id where lead_type_id = ? and l.id = ?");
            stmt.setLong(1, leadType.getId());
            stmt.setLong(2, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PaydayLeadModel(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public PaydayLead getLead() {
        return lead;
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
						<select name="direct_deposit" class="form_input_half">
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
