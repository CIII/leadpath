create table click_sources(
    id int(11) not null auto_increment,
    publisher_id int(11) not null,
    name varchar(255) not null,
    pixel_back_url varchar(255),
    created_at datetime,
    primary key (id),
    foreign key(publisher_id) references publishers(id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into publishers values(null, 'click_source_test', 'test', md5('test'), now(), null);
insert into click_sources values(null, (select id from publishers where name = 'click_source_test'), 'test_source', 'http://www.acquisition-labs.com?pixel_fire_id={pixel_fire_id}', now());

create table click_targets(
    id int(11) not null auto_increment,
    advertiser_id int(11) not null,
    name varchar(255) not null,
    destination_url varchar(255) not null,
    created_at datetime,
    primary key (id),
    foreign key(advertiser_id) references advertisers(id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into advertisers values(null, 'click_target_test', now(), null);
insert into click_targets values(null, (select id from advertisers where name = 'click_target_test'), 'test_target', 'http://www.acquisition-sciences.com?arrival_id={redirect_id}', now());

create table click_rules(
    id int(11) not null auto_increment,
    click_source_id int(11) not null,
    click_target_id int(11) not null,
    status int(11) default 0,
    created_at datetime,
    updated_at datetime,
    primary key (id),
    foreign key(click_source_id) references click_sources(id),
    foreign key(click_target_id) references click_targets(id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into click_rules values(null, (select id from click_sources where name = 'test_source'), (select id from click_targets where name = 'test_target'), 0, now(), null);

create table domains(
    id int(11) not null auto_increment,
    domain_label varchar(255) not null,
    top_level_domain varchar(255) not null,
    created_at datetime,
    primary key (id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table redirects(
    id int(11) not null auto_increment,
    click_source_id int(11) not null,
    click_target_id int(11) not null,
    destination_path varchar(255),
    destination_query varchar(255),
    referrer_domain_id int(11),
    ip_address varchar(30),
    user_agent varchar(255),
    referrer varchar(255),
    created_at datetime,
    primary key (id),
    foreign key(click_source_id) references click_sources(id),
    foreign key(click_target_id) references click_targets(id),
    foreign key(referrer_domain_id) references domains(id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table pixel_fires(
    id int(11) not null auto_increment,
    redirect_id int(11) not null,
    pixel_type int(11) not null,
    ip_address varchar(30),
    user_agent varchar(255),
    referrer varchar(255),
    created_at datetime,
    primary key (id),
    foreign key(redirect_id) references redirects(id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table tmp (id int(11)) ;
insert into tmp (select max(id) from pixel_fires group by redirect_id, pixel_type having count(*) > 1);
delete from pixel_fires where id in (select id from tmp);

alter table pixel_fires add unique key k_redirect_pixel_type (redirect_id, pixel_type);
alter table pixel_fires add column counter int(11) default 1 after pixel_type;

