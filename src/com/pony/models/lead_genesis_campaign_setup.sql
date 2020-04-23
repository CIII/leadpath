SELECT * FROM pony_leads.orders;

insert into orders (code, advertiser_id, lead_type_id, vpl, source_id, is_exclusive, status, cap_daily, cap_monthly, cap_total, target_url, weight, created_at, updated_at)
    values ('LeadGenesis:Special', 20, -1, 0, 'camp_id=0000__camp_key=asdf__is_test=1', 1, 1, 0, 0, 0, 'https://api.leadgenesis.info/v1/leads/', 100, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
    
    select * from publishers;
    insert into publishers (name, user_name, password, allow_duplicates, domain_name, domain_token, extended_validation, created_at, updated_at)
        values ('lg.easiersolar.com', '', '', 1, 'lg.easiersolar.com', '1234', 0, current_timestamp(), current_timestamp());
        
select * from publisher_lists;
insert into publisher_lists (lead_type_id, name, ext_list_id, max_lead_units, status, is_direct, created_at, updated_at)
    values (-1, 'LeadGenesis Campaign', 'lg_solar_form', 1, 1, 1, current_timestamp(), current_timestamp());
    
select * from publisher_list_members;
insert into publisher_list_members (publisher_list_id, publisher_id, cpl, status, created_at, updated_at)
    values (22, 18, 0, 1, current_timestamp(), current_timestamp());
    
select * from publisher_list_orders;
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at, updated_at)
    values ('LeadGenesis Campaign', 22, 35, 1, current_timestamp(), current_timestamp());