-- duplicated schema (subset) changes from the CRM app (only the tables needed to run code in this app; the CRM app has additional tables exclusive to it; those are not duplicated here)

-- these are the tables needed to look up a broker to be assigned to a lead based on advertiser of the lead match, and state of the lead
-- (tables are exposed via the AdvertiserModel)
-- the broker record can be used to warm transfer the lead (one possible use)
create table states (id int(11) not null auto_increment, state_code varchar(2) not null, created_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table states add unique key(state_code);

create table callcenter_brokers (id int(11) not null auto_increment, advertiser_id int(11) not null, first_name varchar(50) not null, last_name varchar(50), email varchar(255), broker_id varchar(25), phone_number varchar(25), created_at datetime, primary key(id), foreign key (advertiser_id) references advertisers(id) ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table callcenter_brokers add unique key advertiser_broker (advertiser_id, broker_id);

create table state_brokers (id int(11) not null auto_increment, state_id int(11) not null, callcenter_broker_id int(11) not null, created_at datetime, primary key(id), foreign key (state_id) references states(id), foreign key (callcenter_broker_id) references callcenter_brokers(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
alter table state_brokers add unique key state_broker (state_id, callcenter_broker_id);

-- ========
-- new tables for order filtering
create table state_filters (id int(11) not null auto_increment,
    order_id int(11) not null,
    is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
    states text not null,
    created_at datetime not null, updated_at datetime, primary key(id), foreign key (order_id) references orders(id) ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table day_filters (id int(11) not null auto_increment,
    order_id int(11) not null,
    is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
    days varchar(15) not null,
    created_at datetime not null, updated_at datetime, primary key(id), foreign key (order_id) references orders(id) ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table time_filters (id int(11) not null auto_increment,
    order_id int(11) not null,
    is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
    times varchar(255) not null,
    created_at datetime not null, updated_at datetime, primary key(id), foreign key (order_id) references orders(id) ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table zip_filters (id int(11) not null auto_increment,
    order_id int(11) not null,
    is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
    zipcodes text not null,
    created_at datetime not null, updated_at datetime, primary key(id), foreign key (order_id) references orders(id) ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
