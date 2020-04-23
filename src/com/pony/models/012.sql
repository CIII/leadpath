-- add columns to support autobytel pings
alter table advertiser_disposition_buyers add column longitude varchar(15) after price, add column latitude varchar(15) after longitude, add column program_id varchar(20) after latitude, add column buyer_code varchar(20) after program_id;

-- add AutoByTel as an adveriser
insert into advertisers values(null, 'AutoByTel', now(), null);
insert into orders values(null, 'autobytel-1', (select id from advertisers where name ='AutoByTel'), (select id from lead_types where name = 'New Cars'), 0, null, 0, 1, 0,0,0,null, now(),null);
insert into advertiser_writers values(null, (select id from lead_types where name = 'New Cars'), (select id from advertisers where name ='AutoByTel'), 'com.pony.advertiser.writers.AutoByTelWriter', now(), null);

-- add a flag to keep track of preferred buyers we selected
alter table advertiser_disposition_buyers add column is_preferred tinyint default 0 after status;

-- large enough to accommodate UUIDs
alter table arrivals add column external_id varchar(36) after arrival_source_id;
