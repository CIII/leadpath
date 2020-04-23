-- solar lookup tables
create table utility_lookup(
id int(11) not null auto_increment
, state varchar(3) not null
, lg_name varchar(100) not null default 'other'
, rgr_name varchar(100) not null default 'other'
, frequent tinyint default 0
, created_at datetime
, updated_at datetime
, primary key(id)
, unique key(state, lg_name, rgr_name)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--    import from file: in excel: save as tab delimited, then vi: :%s/<Ctrl-V><Ctrl-M>/\r/g
--    Where <Ctrl-V><Ctrl-M> means type Ctrl+V then Ctrl+M.
--    save file and import:
mysql -u root -p pony_leads -e "load data local infile '/home/ec2-user/LGUtilityProviderList_w_RGR_Mapping_20160623.txt' into table utility_lookup fields terminated by '\t' optionally enclosed by '\"' ignore 1 lines (state,lg_name,rgr_name,frequent) set created_at=now()"


create table rgr_exclusive_pricing(
id int(11) not null auto_increment
, zip_code varchar(10) not null
, state varchar(3) not null
, price decimal(12,2) default 0
, created_at datetime
, updated_at datetime
, primary key(id)
, unique key(zip_code, state)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

mysql -u root -p pony_leads -e "load data local infile '/home/ec2-user/RGR_Solar_Exclusive_Price_Points-06-01-2016.txt' into table rgr_exclusive_pricing fields terminated by '\t' optionally enclosed by '\"' ignore 1 lines (zip_code, state, price) set created_at=now()"
update rgr_exclusive_pricing set zip_code = LPAD(zip_code, 5, '0');

create table rgr_semi_exclusive_pricing(
id int(11) not null auto_increment
, zip_code varchar(10) not null
, state varchar(3) not null
, price decimal(12,2) default 0
, created_at datetime
, updated_at datetime
, primary key(id)
, unique key(zip_code, state)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

mysql -u root -p pony_leads -e "load data local infile '/home/ec2-user/RGR_Semi-Exclusive_Cumulative_Solar_Price_Points-06-01-2016.txt' into table rgr_semi_exclusive_pricing fields terminated by '\t' optionally enclosed by '\"' ignore 1 lines (zip_code, state, price) set created_at=now()"
update rgr_semi_exclusive_pricing set zip_code = LPAD(zip_code, 5, '0');

drop table if exists lg_exclusive_pricing;
create table lg_exclusive_pricing(
id int(11) not null auto_increment
, state varchar(3) not null
, county varchar(100)
, zip_code varchar(10) not null
, highest_rpt_100 decimal(12,2) default 0
, highest_rpt_150 decimal(12,2) default 0
, county_rpm  decimal(12,2) default 0
, power_adj  decimal(12,2) default 0
, rpt_adjusted  decimal(12,2) default 0
, created_at datetime
, updated_at datetime
, primary key(id)
, unique key(zip_code, state)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- steps to update the LG pricing lookup table (via tmp staging table)
create table lg_exclusive_pricing_tmp like lg_exclusive_pricing;

mysql -u root -p pony_leads -e "load data local infile '/home/ec2-user/LG_Rev_Share_Zip_List_Wk25_06-20-16.txt' into table lg_exclusive_pricing fields terminated by '\t' optionally enclosed by '\"' ignore 1 lines (state, county, zip_code, highest_rpt_100, highest_rpt_150, county_rpm, power_adj, rpt_adjusted) set created_at=now()"

-- pad the zip code with leading zeros to length=5
update lg_exclusive_pricing set zip_code = LPAD(zip_code, 5, '0');
alter table lg_exclusive_pricing_tmp rename to lg_exclusive_pricing;
--drop table lg_exclusive_pricing;


-- widen arrivals.external_id
alter table arrivals change external_id external_id varchar(72) DEFAULT NULL;

-- adding unique key for publisher_list_id, publisher_id, and external_id to make sure we collect all data from the same publisher and external_id
alter table arrivals add unique key idx_external_id (publisher_id, publisher_list_id, external_id);
