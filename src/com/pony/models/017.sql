alter table states add column state_name varchar(50) after state_code;
load data local infile '/work/ponyleads/resources/us_states.tsv' into table states (state_name, state_code) set created_at=now();


-- ================================
-- create GI site posts
--	'domtok' => 'labs66',
--	'ref' => 'http://www.general-indemnity.com'
-- ================================

-- ================================
-- publishers and advertisers
-- ================================
insert into publishers (name, user_name, password, allow_duplicates, domain_name, domain_token, created_at) values('GI', '', '', 1, 'www.general-indemnity.com', md5('homeAtLast'), now());
insert into publishers (name, user_name, password, allow_duplicates, domain_name, domain_token, created_at) values('TheBillboardSite', '', '', 1, 'www.thebillboardsite.com', md5('homeAtLast'), now());
insert into advertisers (name, created_at) values('GI', now());
insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( -1 , (select id from advertisers where name ='GI'), 'com.pony.advertiser.writers.EmailWriter', now());

-- ================================
-- forms
-- ================================

-- reg_form
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:registration', 'reg_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:registration'), (select id from publishers where name = 'GI'),1,now());

-- contact_form
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:contact', 'contact_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:contact'), (select id from publishers where name = 'GI'),1,now());

-- create_form ??
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:create_account', 'create_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:create_account'), (select id from publishers where name = 'GI'),1,now());

-- billboard_form
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:billboard_form', 'billboard_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:billboard_form'), (select id from publishers where name = 'GI'),1,now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:billboard_form'), (select id from publishers where name = 'TheBillboardSite'),1,now());

-- general_insurance_form
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:general_insurance_form', 'general_insurance_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:general_insurance_form'), (select id from publishers where name = 'GI'),1,now());

-- claim_form
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'GI Insurance Leads:claim_form', 'claim_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'GI Insurance Leads:claim_form'), (select id from publishers where name = 'GI'),1,now());


-- ================================
-- orders
-- ================================
-- reg_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:registration', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: reg posts', (select id from publisher_lists where ext_list_id = 'reg_form'), (select id from orders where code = 'GI:registration'), 1, now());

-- contact_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:contact', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: concat posts', (select id from publisher_lists where ext_list_id = 'contact_form'), (select id from orders where code = 'GI:contact'), 1, now());

-- create_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:create_account', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: create posts', (select id from publisher_lists where ext_list_id = 'create_form'), (select id from orders where code = 'GI:create_account'), 1, now());

-- billboard_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:billboard', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: billboards', (select id from publisher_lists where ext_list_id = 'billboard_form'), (select id from orders where code = 'GI:billboard'), 1, now());

-- general_insurance_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:general_insurance', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: general_insurance', (select id from publisher_lists where ext_list_id = 'general_insurance_form'), (select id from orders where code = 'GI:general_insurance'), 1, now());

-- claim_form
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('GI:claim', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('GI: claims', (select id from publisher_lists where ext_list_id = 'claim_form'), (select id from orders where code = 'GI:claim'), 1, now());


-- ===============================
-- Solar leads
-- ===============================

insert into publishers (name, user_name, password, allow_duplicates, domain_name, domain_token, created_at) values('ASC', '', '', 1, 'www.americansolarclub.com', md5('sunnyS0lar'), now());
insert into advertisers (name, created_at) values('ASC', now());
insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( -1 , (select id from advertisers where name ='ASC'), 'com.pony.advertiser.writers.solar.CleanEnergyExperts', now());

-- full form quote
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'ASC-Leads:SolarQuote-FullForm', 'solar_full_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'ASC-Leads:SolarQuote-FullForm'), (select id from publishers where name = 'ASC'),1,now());

-- wizard form quote
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'ASC-Leads:SolarQuote-WizardForm', 'solar_wizard_form', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'ASC-Leads:SolarQuote-WizardForm'), (select id from publishers where name = 'ASC'),1,now());

-- orders
insert into orders (code, advertiser_id, lead_type_id, source_id, target_url, status, created_at) values('ASC:Solar', (select id from advertisers where name ='ASC'), -1, 'campid=xxxTODOxxx', 'http://receiver.ceeleads.info/leads/post2', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('ASC:SolarFullForm-Link', (select id from publisher_lists where ext_list_id = 'solar_full_form'), (select id from orders where code = 'ASC:Solar'), 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('ASC:SolarWizardForm-Link', (select id from publisher_lists where ext_list_id = 'solar_wizard_form'), (select id from orders where code = 'ASC:Solar'), 1, now());

-- =============================================
-- 4/20/16: TheBillboardSite: other lead types

-- billboard-new_owner
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'TheBillboardSite:new-owner', 'billboard-new_owner', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'TheBillboardSite:new-owner'), (select id from publishers where name = 'TheBillboardSite'),1,now());
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('TheBillboardSite:newSignOwner', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('TBS: new billboard owner', (select id from publisher_lists where ext_list_id = 'billboard-new_owner'), (select id from orders where code = 'TheBillboardSite:newSignOwner'), 1, now());

-- billboard-new_post
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'TheBillboardSite:new-post', 'billboard-new_post', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'TheBillboardSite:new-post'), (select id from publishers where name = 'TheBillboardSite'),1,now());
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('TheBillboardSite:newBillboard', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('TBS: new billboard', (select id from publisher_lists where ext_list_id = 'billboard-new_post'), (select id from orders where code = 'TheBillboardSite:newBillboard'), 1, now());

-- billboard-contractor_request
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( -1, 'TheBillboardSite:contractorRequest', 'billboard-contractor_req', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'TheBillboardSite:contractorRequest'), (select id from publishers where name = 'TheBillboardSite'),1,now());
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('TheBillboardSite:contractorRequest', (select id from advertisers where name ='GI'), -1, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('TBS: contractor request', (select id from publisher_lists where ext_list_id = 'billboard-contractor_req'), (select id from orders where code = 'TheBillboardSite:contractorRequest'), 1, now());
