alter table orders add column is_exclusive tinyint default 0 after source_id;
alter table orders add UNIQUE KEY(code);

alter table publisher_lists add column max_lead_units int(3) default 1 after ext_list_id;

alter table publishers add column domain_name varchar(255) after allow_duplicates;
alter table publishers add column domain_token varchar(100) after domain_name;

-- new default lead type
insert into lead_types values(-1,'Pony Attribute Map Type', now(), null);

-- for brandeis posts
insert into publishers values(null, 'Brandeis GPS', '', '', 1, 'gpsonline.brandeis.edu', md5('gpsonline'), 0, now(), null);
insert into publisher_lists values(null, -1, 'Brandeis GPS', 'gpsonline', 1, 0, now(), null);
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'Brandeis GPS'), (select id from publishers where name = 'Brandeis GPS'),0,0,now(),null);

insert into advertisers values(null, 'Brandeis', now(), null);
insert into orders values(null, 'gpsonline', (select id from advertisers where name ='Brandeis'), -1, 0, null, 1, 1, 0, 0,0,now(),null);

-- set brandeis posts up for email
insert into advertiser_writers values(null, -1, (select id from advertisers where name ='Brandeis'), 'com.pony.advertiser.writers.EmailWriter', now(), null);

-- ============================
-- everything needed for cars
insert into lead_types values(null, 'New Cars', now() , null);

insert into publishers values(null, 'Wiser Auto', '', '', 1, 'wiserauto.com', md5('wiserOrNot'), 0, now(), null);
insert into publisher_lists values(null, (select id from lead_types where name ='New Cars'), 'New Cars', 'new_cars', 4, 1, now(), null);
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'New Cars'), (select id from publishers where name = 'Wiser Auto'),0,1,now(),null);

insert into advertisers values(null, 'DTX', now(), null);
insert into advertisers values(null, 'Reply.com', now(), null);

insert into orders values(null, 'dtx-1', (select id from advertisers where name ='DTX'), (select id from lead_types where name = 'New Cars'), 0, null, 0, 1, 0,0,0,now(),null);
insert into orders values(null, 'reply-1', (select id from advertisers where name ='Reply.com'), (select id from lead_types where name = 'New Cars'), 0, null, 0, 1, 0,0,0,now(),null);

insert into advertiser_writers values(null, (select id from lead_types where name = 'New Cars'), (select id from advertisers where name ='DTX'), 'com.pony.advertiser.writers.DTXWriter', now(), null);
insert into advertiser_writers values(null, (select id from lead_types where name = 'New Cars'), (select id from advertisers where name ='Reply.com'), 'com.pony.advertiser.writers.ReplyWriter', now(), null);

-- changes to allow empty user profiles on leads
alter table leads modify column user_profile_id int(11) null;
alter table leads modify column arrival_id int(11) not null;

create table buyers (id int(11) not null auto_increment,
                      buyer_id varchar(100) not null,
                      name varchar(255) not null,
                      state varchar(3),
                      zip varchar(15),
                      city varchar(100),
                      street varchar(150),
                      contact_name varchar(100),
                      contact_phone varchar(50),
                      created_at datetime not null, updated_at datetime,
                      primary key(id),
                      unique key (buyer_id, zip, name)
                      ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table advertiser_disposition_buyers (id int(11) not null auto_increment, buyer_id int(11) not null, advertiser_disposition_id int(11) not null,
                      reservation_id varchar(100),
                      price decimal(12,2),
                      distance varchar(20),
                      group_id varchar(100),
                      max_posts int(4),
                      created_at datetime not null, updated_at datetime,
                      primary key(id),
                      foreign key (buyer_id) references buyers(id),
                      foreign key (advertiser_disposition_id) references advertiser_dispositions(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE ping_attributes (id int(11) NOT NULL AUTO_INCREMENT,
  lead_id int(11) NOT NULL,
  attribute_id int(11) NOT NULL,
  value varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (lead_id, attribute_id),
  KEY attribute_id (attribute_id),
  FOREIGN KEY (lead_id) REFERENCES leads (id),
  FOREIGN KEY (attribute_id) REFERENCES attributes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table advertiser_disposition_buyers add column status tinyint(4) default 0 after max_posts;
-- park the old records so we can look at them and update them correctly
update advertiser_disposition_buyers set status = -1;

alter table advertiser_dispositions add column pony_phase tinyint(4) not null default -1 after category;

alter table arrivals add column arrival_source_id int(11) NULL after user_profile_id;
alter table arrivals add key (created_at, arrival_source_id, publisher_id);

alter table advertiser_disposition_buyers add column external_lead_id varchar(50) after max_posts;

alter table lead_posts add column pony_phase tinyint(4) not null default -1 after lead_match_id;

alter table advertiser_dispositions add column price decimal(12,2) after pony_phase;

alter table orders add column pixel_id int(11) after cap_total;

-- ============================
-- everything needed for insurance
insert into lead_types values(-1, 'Pony', now() , null);

insert into publishers values(null, 'LeadKarma', '', '', 1, 0, 'leadkarma.com', md5('karma2013'), now(), null);

insert into publisher_lists values(null, (select id from lead_types where name ='Pony'), 'health', 'lk_health', 4, 1, now(), null);
insert into publisher_lists values(null, (select id from lead_types where name ='Pony'), 'auto', 'lk_auto', 4, 1, now(), null);

insert into publisher_list_members values(null, (select id from publisher_lists where name = 'health'), (select id from publishers where name = 'LeadKarma'),0,1,now(),null);
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'auto'), (select id from publishers where name = 'LeadKarma'),0,1,now(),null);

insert into advertisers values(null, 'Acquisition-Labs', now(), null);

insert into orders values(null, 'Acquisition-Labs CatchAll {email}', (select id from advertisers where name ='Acquisition-Labs'), (select id from lead_types where name = 'Pony'), 0, null, 0, 1, 0,0,0,null, now(),null);

insert into advertiser_writers values(null, (select id from lead_types where name = 'Pony'), (select id from advertisers where name ='Acquisition-Labs'), 'com.pony.advertiser.writers.EmailWriter', now(), null);
