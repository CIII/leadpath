alter table advertisers add unique key(name);
alter table lead_types add unique key(name);
alter table hosts add unique key(domain_name);
alter table smtp_providers add unique key(name);

alter table offers add column from_personal varchar(255) after from_address, add column click_url varchar(255) after target_url;
alter table offers add unique key (name);

-- changes for email delivery
alter table hosts add column smtp_host_name varchar(128) default 'smtp.sendgrid.net' after max_sends_daily;
alter table hosts add column smtp_auth_user varchar(128) default 'mholzner' after smtp_host_name;
alter table hosts add column smtp_auth_pwd varchar(128) default 'woodler' after smtp_auth_user;
alter table hosts add column smtp_port int(11) default 587 after smtp_auth_user;

-- separate out (normalize) subject lines and message contents
-- new separated columns
create table subject_lines(id int(11) not null auto_increment, subject_line varchar(255) not null, created_at datetime, updated_at datetime, primary key(id), unique key(subject_line)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
create table message_templates(id int(11) not null auto_increment, name varchar(128), html_content text, text_content text, created_at datetime, updated_at datetime, primary key(id), unique key(name)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
create table from_addresses(id int(11) not null auto_increment, name varchar(128), from_address varchar(255) not null, from_personal varchar(255), created_at datetime, updated_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- drop separated out columns from creatives
alter table creatives drop column subject_line, drop column html_content, drop column text_content;

-- create new foreign keys
alter table creatives add column subject_line_id int(11), add column message_template_id int(11), add column from_address_id int(11), add foreign key (subject_line_id) references subject_lines(id), add foreign key (message_template_id) references message_templates(id), add foreign key (from_address_id) references from_addresses(id), add unique key(from_address_id, subject_line_id, message_template_id);

-- add an external id to creatives to allow passing on configurable tokens when we create the message, or when ewe redirect a mail click to it's final destination
alter table creatives add column external_id varchar(64);

-- new table to keep track of what was done last per target list
create table email_target_queues(id int(11) not null auto_increment, name varchar(255) not null, from_publisher_list_id int(11) not null, to_publisher_list_id int(11) not null, max_arrival_id int(11) not null default 0, status int(11) not null default 0, created_at datetime, updated_at datetime, primary key(id), unique key(from_publisher_list_id, to_publisher_list_id), foreign key(from_publisher_list_id) references publisher_lists(id), foreign key(to_publisher_list_id) references publisher_lists(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- set up email template and links for LeadKarma auto insurance
-- unsub link: http://www.insurance-offers-unsubscribe.com/71460de0e7f32668ca3e8005acd4a1d7c168eb507e9af5f57633ebf82cdbe82f?emailaddr=@@email@@
-- You are receiving this email because you have visited our site, or the site of one of our affiliates, in the past. If you'd like to unsubscribe or received this email in error, please click here.
-- Feel free to review our privacy policy at any of our sites by clicking on the link above. 222 Third Street, Suite 2300, Cambridge, MA 02142

-- tracking / target url: http://Easierinsurancequote.com/?id=295&pid=3

-- ====================================================================================
-- Note: this is specific for ec2-user@ec2-23-22-211-224.compute-1.amazonaws.com
-- ====================================================================================
insert ignore into advertisers (name, created_at) values('LeadKarma', now());
insert ignore into lead_types values(null, 'Email', now(), null);

insert ignore into smtp_providers values(null, 'SendGrid', now());

insert ignore into hosts (smtp_provider_id, domain_name, max_sends_daily, status, created_at)
        values( (select id from smtp_providers where name = 'SendGrid'), 'car-insurance-quotes.me', 10000, 1, now()) ;

-- list to tie the offers to
insert ignore into publisher_lists (lead_type_id, name, ext_list_id, max_lead_units, status, created_at)
    values((select id from lead_types where name = 'Email'), 'LeadKarma Auto email 1', 'lk_auto_email1', 0, 1, now()) ;

-- setup local publisher and list privileges
insert ignore into publishers(name, domain_name, domain_token, user_name, password, created_at)
        values('local async', 'localhost', md5('SendItNow!'), 'u', 'p', now());

insert ignore into publisher_list_members (publisher_list_id, publisher_id, created_at)
        values((select id from publisher_lists where name = 'LeadKarma Auto email 1'), (select id from publishers where name = 'local async'), now());

-- provide a hook for the code to get started
-- !!! Note: for production the value of max_arrival_id should be set close to the real max to limit the # of messages produced!
insert ignore into email_target_queues (name, from_publisher_list_id, to_publisher_list_id, max_arrival_id, status, created_at)
        values('Lead Karma Auto Insurance async transactional',
        (select id from publisher_lists where lead_type_id=-1 and  ext_list_id = 'lk_auto'),
        (select id from publisher_lists where lead_type_id=(select id from lead_types where name = 'Email') and  ext_list_id = 'lk_auto_email1'),
        1000, 1 , now());


-- =========================================================================================
-- TODO : finish here
-- offers for the same publisher list
-- Note: the click url is the one pointing back to the sending instance for tracking; the target url points to the final destination
insert ignore into offers (name, advertiser_id, lead_type_id, target_url, unsubscribe_url, status, created_at)
        values('LeadKarma Auto Insurance 1',
        (select id from advertisers where name = 'LeadKarma'),
        (select id from lead_types where name ='Email'),
        'http://Easierinsurancequote.com/?id=295&pid=3',
        'http://www.insurance-offers-unsubscribe.com/71460de0e7f32668ca3e8005acd4a1d7c168eb507e9af5f57633ebf82cdbe82f?emailaddr=@@email@@', 1, now()) ;

-- create the list split
insert ignore into list_splits (publisher_list_id, offer_id, percentage, status, created_at)
        values((select id from publisher_lists where lead_type_id=(select id from lead_types where name = 'Email') and  ext_list_id = 'lk_auto_email1'),
               (select id from offers where name = 'LeadKarma Auto Insurance 1'),
               100, 1, now());

-- from lines:
--Easierinsurancequote
insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Easier Insurance Quote', now(), null);

--Insurance Savings
insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Insurance Savings', now(), null);

--United States INS
--insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'United States INS', now(), null);

--Auto Insurance
insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Auto Insurance', now(), null);

--Auto_Coverage
--insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Auto Coverage', now(), null);

--Car.Coverage
insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Car Coverage', now(), null);

--Affordable.Coverage
insert into from_addresses values(null, 'name here', 'info@car-insurance-quotes.me', 'Affordable Coverage', now(), null);


-- Subject Lines:
--Save 50% on Auto Insurance in @@city@@!
insert into subject_lines (subject_line, created_at) values('Save 50% on Auto Insurance in @@city@@!', now()) ;

--Stop Overpaying for Auto Insurance! See What Discounts You Qualify For!
insert into subject_lines (subject_line, created_at) values('Stop Overpaying for Auto Insurance! See What Discounts You Qualify For!', now()) ;

--Stop Overpaying for Auto Insurance! Get Quotes From Easierinsurancequote Now!
--insert into subject_lines (subject_line, created_at) values('Stop Overpaying for Auto Insurance! Get Quotes From Easierinsurancequote Now!', now()) ;

--Drivers Fail To Take Advantage Of New Auto Insurance Discounts. See If You Qualify!
--No Tickets In the Last 3 Years?  Then Qualify to Save with Easierinsurancequote!
--Find Low Rate Auto Insurance  - 2013 Update!
--You could qualify for Half Price Auto Insurance in 2013!
--Save up to 50% on Auto Insurance - Find out if you qualify now! 
--ATTN Drivers - United States Auto Insurance discounts available in your area!
--You Could be Entitled to Pay Half Price Auto Insurance Now!
--Stop Overpaying for Auto Insurance! See What Discounts You Qualify For!
--No Tickets In the Last 3 Years?  Then You Are Paying Too Much For Auto Insurance!
--No DUI? Then You Pay Too Much. Get Auto Insurance Now with Easierinsurancequote!
--Compare Free Auto Coverage Quotes From United States Auto Insurance!
--Find Top Auto Coverage Deals From United States Auto Insurance
--United States Auto Insurance Could Save You 50% on Car Insurance!
--Get Free Auto Coverage Quotes Fast
--Lowest Auto Coverage Rates – Free Quotes
--Three easy steps: Free auto coverage quotes
--Where do you find the lowest auto coverage rates?
--Free Car Coverage Quotes: 3 Easy Steps
--Easierinsurancequote: 3 Easy Steps
--Need Auto Coverage? Free Quotes in 3 Easy Steps
--Get Top Auto Insurance For Less
--Compare Auto Coverage Options & Save
--Don't have a perfect driving record? Still get the perfect coverage.
--Drivers: Finding car coverage is easy
--US Drivers Can Stop Paying Overpaying For Car Insurance
--Feel free to send additional subject lines for approval that have worked in other campaigns

-- ??? what should we use as sub_id (s)


insert ignore into message_templates (name, html_content, text_content, created_at)
        values('test message', 'html here', 'text here', now());


-- create the creatives and the splits
insert into creatives (name, offer_id, status, subject_line_id, from_address_id, message_template_id, created_at)
        values('LK Auto1: 1',
        (select id from offers where name = 'LeadKarma Auto Insurance 1'), 1,
        (select id from subject_lines where subject_line = 'Save 50% on Auto Insurance in @@city@@!'),
        (select id from from_addresses where name="EasierInsuranceQuote"),
        (select id from message_templates where name = 'test message'),
        now());

insert into creatives (name, offer_id, status, subject_line_id, from_address_id, message_template_id, created_at)
        values('LK Auto1: 2',
        (select id from offers where name = 'LeadKarma Auto Insurance 1'), 1,
        (select id from subject_lines where subject_line = 'Stop Overpaying for Auto Insurance! See What Discounts You Qualify For!'),
        (select id from from_addresses where name="EasierInsuranceQuote"),
        (select id from message_templates where name = 'test message'),
        now());


--
--        (select id from from_addresses where name="InsuranceSavings")
--        (select id from from_addresses where name="UnitedStatesINS")
--        (select id from from_addresses where name="AutoInsurance")
--        (select id from from_addresses where name="AutoCoverage")
--        (select id from from_addresses where name="CarCoverage")
--        (select id from from_addresses where name="AffordableCoverage")

-- splits
-- !!! TODO: !!! replace ids with the local ids
insert into offer_splits (list_split_id, creative_id, host_id, percentage, status, created_at)
        (select 1, id, (select id from hosts where domain_name ='car-insurance-quotes.me'), 50, 1, now() from creatives where offer_id = (select id from offers where name = 'LeadKarma Auto Insurance 1'));


