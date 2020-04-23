create database pony_leads;
-- dev only:
-- create database sendplex;
--CREATE TABLE `inbound_queue` (
--               `id` int(11) NOT NULL AUTO_INCREMENT,
--               `list_id` int(11) DEFAULT NULL,
--               `query_string` text,
--              `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--               PRIMARY KEY (`id`)
--             ) ENGINE=MyISAM AUTO_INCREMENT=21667513 DEFAULT CHARSET=latin1
-- create table user_profiles(id int(11) not null AUTO_INCREMENT, email varchar(255) not null),PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=latin1

-- create base tables for payday
create table lead_types (id int(11) not null auto_increment, name varchar(255) not null, created_at datetime, updated_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
insert into lead_types values(null,'Email',now(),null);

create table publishers (id int(11) not null auto_increment, name varchar(255) not null, user_name varchar(255) not null, password varchar(255) not null, created_at datetime, updated_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table publishers add index(user_name, password);

create table mail_hosts (id int(11) not null auto_increment, name varchar(255), email_suffix varchar(255), created_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table user_profiles (id int(11) not null auto_increment, email varchar(255) not null, email_md5 varchar(32) not null, mail_host_id int(11) not null, created_at datetime, last_seen_at datetime, primary key(id), foreign key (mail_host_id) references mail_hosts(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table user_profiles add unique index(email);
alter table user_profiles add unique index(email_md5);

create table attributes (id int(11) not null auto_increment, name varchar(255) not null, created_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table profile_attributes (id int(11) not null auto_increment, user_profile_id int(11) not null, attribute_id int(11) not null, value varchar(255) not null, created_at datetime not null, updated_at datetime,
    primary key(id),
    foreign key(user_profile_id) references user_profiles(id),
    foreign key(attribute_id) references attributes(id),
    unique key idx_profile_attribute (user_profile_id, attribute_id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table advertisers (id int(11) not null auto_increment, name varchar(255), created_at datetime, updated_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
insert into advertisers values (null, 'TestAdvertiser', now(), null);

create table advertiser_writers (id int(11) not null auto_increment, lead_type_id int(11) not null, advertiser_id int(11) not null, class_name varchar(255) not null, created_at datetime, updated_at datetime, primary key(id), foreign key (lead_type_id) references lead_types(id), foreign key (advertiser_id) references advertisers(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
--insert into advertiser_writers values(null, 1, 1, 'com.pony.advertiser.writers.PaydayTestWriter', now(), null);

create table arrivals (id int(11) not null auto_increment, publisher_id int(11) not null, user_profile_id int(11),
    ip_address varchar(30), user_agent varchar(255), referrer varchar(255),
    created_at datetime, last_seen_at datetime, validation_code int(11) not null default -1,
    primary key(id),
    foreign key(publisher_id) references publishers(id),
    foreign key(user_profile_id) references user_profiles(id),
    unique key idx_profile_publisher (user_profile_id, publisher_id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table leads (id int(11)not null auto_increment, user_profile_id int(11)not null, arrival_id int(11)null, lead_type_id int(11)not null default 1,
    status tinyint default 0,
    created_at datetime, updated_at datetime,
    primary key(id),
    foreign key(user_profile_id) references user_profiles(id),
    foreign key(arrival_id) references arrivals(id),
    foreign key(lead_type_id) references lead_types(id))
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table orders (id int(11) not null auto_increment, code varchar(255), advertiser_id int(11) not null, lead_type_id int(11) not null default 1, vpl decimal(8,2) default 0, source_id varchar(255), status tinyint default 0, cap_daily int(11) not null default 0, cap_monthly int(11) not null default 0, cap_total int(11) not null default 0, created_at datetime, updated_at datetime,
    primary key(id),
    foreign key(advertiser_id) references advertisers(id),
    foreign key(lead_type_id) references lead_types(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table lead_matches (id int(11) not null auto_increment, lead_id int(11) not null, order_id int(11) not null, status tinyint default 0, price decimal(12,2) default 0, created_at datetime, updated_at datetime, external_id varchar(100),
    primary key(id),
    foreign key(order_id) references orders(id),
    foreign key(lead_id) references leads(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table lead_matches add index (order_id, created_at);
alter table lead_matches add index (external_id, lead_id, order_id);

create table advertiser_dispositions (id int(11) not null auto_increment, advertiser_id int(11) not null, lead_match_id int(11) not null, status tinyint default 0, category tinyint, comment varchar(255), created_at datetime,
    primary key(id),
    foreign key(lead_match_id) references lead_matches(id),
    foreign key(advertiser_id) references advertisers(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- email specific
create table user_lists (id int(11) not null auto_increment, name varchar(255) not null, created_at datetime, status tinyint default 0, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
create table user_list_members (id int(11) not null auto_increment, user_list_id int(11) not null, user_profile_id int(11) not null, primary key(id), foreign key (user_profile_id) references user_profiles(id), foreign key(user_list_id) references user_lists(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table publisher_lists (id int(11) not null auto_increment, lead_type_id int(11) not null, name varchar(255) not null, ext_list_id int(11) not null, status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key(lead_type_id) references lead_types(id), unique key(ext_list_id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
create table publisher_list_members (id int(11) not null auto_increment, publisher_list_id int(11) not null, publisher_id int(11) not null, cpl decimal(12,2) not null default 0, status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key(publisher_id) references publishers(id), foreign key(publisher_list_id) references publisher_lists(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into publisher_lists values(null, 1, 'test', 999, 0, now(), null);

create table offers (id int(11) not null auto_increment, name varchar(255) not null, advertiser_id int(11) not null, lead_type_id int(11) not null, target_url varchar(255), unsubscribe_url varchar(255), status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key (advertiser_id) references advertisers(id), foreign key(lead_type_id) references lead_types(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table creatives (id int(11) not null auto_increment, name varchar(255) not null, offer_id int(11) not null, subject_line varchar(255) not null, html_content text, text_content text, status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key (offer_id) references offers(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table smtp_providers(id int(11) not null auto_increment, name varchar(255) not null, created_at datetime, primary key(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into smtp_providers values(null, 'doublenickels', now());
create table hosts(id int(11) not null auto_increment, smtp_provider_id int(11) not null, domain_name varchar(255) not null, ip_address varchar(30), status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key(smtp_provider_id) references smtp_providers(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table list_splits (id int(11) not null auto_increment, publisher_list_id int(11) not null, offer_id int(11) not null, percentage int(3) default 100, status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key(publisher_list_id) references publisher_lists(id), foreign key (offer_id) references offers(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table offer_splits (id int(11) not null auto_increment, offer_id int(11) not null, creative_id int(11) not null, host_id int(11) not null, percentage int(3) default 100, status tinyint default 0, created_at datetime, updated_at datetime, primary key(id), foreign key(offer_id) references offers(id), foreign key (host_id) references hosts(id), foreign key (creative_id) references creatives(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table messages (id int(11) not null auto_increment, user_profile_id int(11) not null, host_id int(11) not null, creative_id int(11) not null, status tinyint default 0, external_id int(11), created_at datetime, updated_at datetime, primary key(id), foreign key(user_profile_id) references user_profiles(id), foreign key(host_id) references hosts(id), foreign key(creative_id) references creatives(id), unique key (external_id, host_id))  ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table opens(id int(11) not null auto_increment, message_id int(11) not null, external_id int(11), open_count int(3) not null default 0, ip_address varchar(30), user_agent varchar(255), referrer varchar(255), created_at datetime, external_created_at datetime, external_updated_at datetime, primary key(id), foreign key(message_id) references messages(id), unique key(message_id, ip_address), key(external_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table clicks(id int(11) not null auto_increment, message_id int(11) not null, external_id int(11), click_count int(3) not null default 0, created_at datetime, external_created_at datetime, external_updated_at datetime, primary key(id), foreign key(message_id) references messages(id), unique key (message_id), key(external_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table bounces(id int(11) not null auto_increment, user_profile_id int(11) not null, message_id int(11), host_id int(11) not null, external_id int(11), message varchar(255), created_at datetime, external_created_at datetime, primary key(id), foreign key(user_profile_id) references user_profiles(id), foreign key(message_id) references messages(id), unique key(external_id, host_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table unsubscribes(id int(11) not null auto_increment, user_profile_id int(11) not null, message_id int(11), external_id int(11), ip_address varchar(30), user_agent varchar(255), referrer varchar(255), created_at datetime, external_created_at datetime, primary key(id), foreign key(user_profile_id) references user_profiles(id), foreign key(message_id) references messages(id), key(external_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table complaints(id int(11) not null auto_increment, user_profile_id int(11) not null, message_id int(11), external_id int(11), created_at datetime, external_created_at datetime, primary key(id), foreign key(user_profile_id) references user_profiles(id), foreign key(message_id) references messages(id), key(external_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Note: these two can be filled from the existing sendplex tables: md5_suppressions2 and known_traps
create table md5_suppressions(id int(11) NOT NULL AUTO_INCREMENT, md5_email varchar(32) DEFAULT NULL, publisher_id int(11) DEFAULT NULL, created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (id), UNIQUE KEY md5_email(md5_email, publisher_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table known_traps(id int(11) NOT NULL AUTO_INCREMENT, email varchar(255) NOT NULL, created_at datetime NOT NULL,PRIMARY KEY (id), unique key(email)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ========================================================
-- revert it all
-- ========================================================
drop table md5_suppressions;
drop table known_hosts;
drop table complaints;
drop table unsubscribes;
drop table bounces;
drop table clicks;
drop table opens;
drop table messages;
drop table offer_splits;
drop table list_splits;
drop table hosts;
drop table smtp_providers;
drop table creatives;
drop table offers;
drop table publisher_list_members;
drop table publisher_lists;
drop table user_list_members;
drop table user_lists;

drop table advertiser_dispositions;
drop table lead_matches;
drop table orders;
drop table leads;
drop table arrivals;
drop table advertiser_writers;

drop table profile_attributes;
drop table attributes;
drop table user_profiles;
drop table publishers;
drop table advertisers;
drop table lead_types;
