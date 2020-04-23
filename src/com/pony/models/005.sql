drop table resend_message_logs;
drop table resend_message_phases;
drop table resend_sequences;

create table resend_sequences(
    id int(11) not null auto_increment,
    name varchar(255) not null,
    status tinyint(4) not null default 0,
    created_at datetime,
    updated_at datetime,
    primary key(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table resend_message_phases(
    id int(11) not null auto_increment,
    resend_sequence_id int(11) not null,
    phase int(4) not null default 0,
    original_message_id int(11) not null,
    message_id int(11) not null,
    created_at datetime,
    primary key (id),
    foreign key(resend_sequence_id) references resend_sequences(id),
    foreign key(original_message_id) references messages(id),
    foreign key(message_id) references messages(id),
    unique key (original_message_id, resend_sequence_id),
    key (resend_sequence_id, original_message_id),
    key (resend_sequence_id, phase, original_message_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table resend_message_logs(
    id int(11) not null auto_increment,
    resend_sequence_id int(11) not null,
    phase int(4) not null default 0,
    original_message_id int(11) not null,
    message_id int(11) not null,
    created_at datetime,
    delay_minutes int(11),
    primary key (id),
    foreign key(resend_sequence_id) references resend_sequences(id),
    foreign key(original_message_id) references messages(id),
    foreign key(message_id) references messages(id),
    key (original_message_id, resend_sequence_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- allow to manage daily send volume
alter table hosts add column max_sends_daily int(11) not null default 2000 after status;

-- allow to flag accounts for trap access (interal trap accounts: this bypasses dup checks)
alter table user_profiles add column is_trap tinyint(1) not null default 0 after mail_host_id;

-- ids happen to be the same for publisher and advertiser table (for the existing rows)
alter table md5_suppressions change publisher_id advertiser_id int(11);

-- add a reference to the publisher_list so we can report by incoming list id
alter table arrivals add column publisher_list_id int(11) after publisher_id;
alter table arrivals add foreign key (publisher_list_id) references publisher_lists(id);

-- normalize leads
alter table leads add unique key(user_profile_id, lead_type_id, arrival_id);
