-- add column to attributes to signal where to store/look for the attribute value
alter table attributes add column is_large tinyint not null default 0 after input_type;

-- add new table for large attribute values (store value here if attributes.is_large =1)
CREATE TABLE profile_xl_attributes (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_profile_id int(11) NOT NULL,
  attribute_id int(11) NOT NULL,
  value text NOT NULL,
  created_at datetime DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY idx_profileXL_attribute (user_profile_id,attribute_id),
  FOREIGN KEY (attribute_id) REFERENCES attributes (id),
  FOREIGN KEY (user_profile_id) REFERENCES user_profiles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert ignore into attributes (name, is_large, created_at) values('xl_bag', 1, now());

-- new lead type RealEstate
insert into lead_types values(null, 'RealEstate', now() , null);

insert into publishers (name, user_name, password, allow_duplicates, domain_name, domain_token, created_at) values('HomeFindify', '', '', 1, 'homefindify.com', md5('homeAtLast'), now());
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( (select id from lead_types where name ='RealEstate'), 'HomeFindify Leads', 'hff_2_score', 1, 0, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'HomeFindify Leads'), (select id from publishers where name = 'HomeFindify'),1,now());

insert into advertisers (name, created_at) values('Coupon Josh', now());

insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('RealEstate: scored leads', (select id from advertisers where name ='Coupon Josh'), (select id from lead_types where name = 'RealEstate'), 'noSend=true;u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, now());

insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( (select id from lead_types where name ='RealEstate') , (select id from advertisers where name ='Coupon Josh'), 'com.pony.advertiser.writers.RealEstateEmailWriter', now());


-- ready for prod:

-- switch list to direct
update publisher_lists set is_direct=1 where ext_list_id = 'hff_2_score';
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('Real Estate: test posts', (select id from publisher_lists where ext_list_id = 'hff_2_score'), (select id from orders where code = 'RealEstate: scored leads'), 1, now());
update orders set code = 'RealEstate: scored leads (test)' where code = 'RealEstate: scored leads';

-- create prod list and order
insert into publisher_lists (lead_type_id, name, ext_list_id, status, is_direct, created_at) values( (select id from lead_types where name ='RealEstate'), 'HomeFindify Leads Production', 'hff_2_score_prod', 1, 1, now());
insert into publisher_list_members (publisher_list_id, publisher_id, status, created_at) values((select id from publisher_lists where name = 'HomeFindify Leads Production'), (select id from publishers where name = 'HomeFindify'),1,now());
insert into orders (code, advertiser_id, lead_type_id, source_id, status, created_at) values('Scored Lead', (select id from advertisers where name ='Coupon Josh'), (select id from lead_types where name = 'RealEstate'), 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=josh@acquisition-sciences.com;bcc=martin@acquisition-labs.com', 1, now());
insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('Real Estate: production posts', (select id from publisher_lists where ext_list_id = 'hff_2_score_prod'), (select id from orders where code = 'Scored Lead'), 1, now());
