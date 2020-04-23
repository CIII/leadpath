--drop table form_impressions;
--drop table select_values;
--drop table form_attributes;
--drop table forms;

-- catching up with some oversights
alter table attributes add unique key(name);
alter table publishers add unique key(name);

-- changing existing tables:
alter table attributes add column input_type varchar(50) not null default 'text' after name;
alter table leads add unique u_user_lead (user_profile_id, arrival_id, lead_type_id);

-- unrelated: table to keep a log of manual changes to hosts (sign up for feedback loop, etc)
create table host_logs (id int(11) not null auto_increment, host_id int(11) not null, comment varchar(255) not null, primary key(id), foreign key(host_id) references hosts(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- new stuff for forms
create table forms (id int(11) not null auto_increment, name varchar(255) not null, call_to_action varchar(255), submit_text varchar(255), publisher_list_id int(11) not null, publisher_id int(11) not null
                     , created_at datetime, updated_at datetime
                     , primary key(id), foreign key (publisher_list_id) references publisher_lists(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table form_impressions (id int(11) NOT NULL AUTO_INCREMENT, form_id int(11) NOT NULL, arrival_id int(11) DEFAULT NULL,
  impression_count int(11) not null default 0,
  ip_address varchar(30) DEFAULT NULL,
  user_agent varchar(255) DEFAULT NULL,
  referrer varchar(255) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  last_seen_at datetime DEFAULT NULL,
  last_uuid varchar(40),
  PRIMARY KEY (id),
  FOREIGN KEY (form_id) references forms(id),
  FOREIGN KEY (arrival_id) references arrivals(id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--insert into lead_types values(null, 'Registration Form', now(), null);
--insert into publisher_lists values(null, (select id from lead_types where name='Registration Form'), 'Registration Form', '', 0, now(), null );
--insert into forms values(null, 'my first form', 'register', 'please answer these questions', (select id from publisher_lists where name = 'Registration Form'), (select id from publishers where name = 'test'), now(), null);

-- wizard config from here
create table form_steps(id int(11) not null auto_increment,
                 form_id int(11) not null,
                 name varchar(255) null,
                 sort_order int(11) not null default 0,
                 initial_enabled_state int(11) not null default 1, -- by default we enable
                 fade_enabled int(11) not null default 1, -- allow groups to fade in / out
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key(form_id) references forms(id),
                unique key(form_id, name))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table form_step_groups(id int(11) not null auto_increment,
                 form_step_id int(11) not null,
                 name varchar(255) null,
                 sort_order int(11) not null default 0,
                 initial_enabled_state int(11) not null default 1, -- by default we enable
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key(form_step_id) references form_steps(id),
                unique key(form_step_id, name))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

alter table form_impressions add column form_step_id int(11) null after form_id, add column form_step_group_id int(11) null after form_step_id, add foreign key (form_step_id) references form_steps(id), add foreign key(form_step_group_id) references form_step_groups(id), add unique key(ip_address, form_id, form_step_id, form_step_group_id);

create table form_step_attributes(id int(11) not null auto_increment,
                 form_step_id int(11) not null,
                 form_step_group_id int(11) not null,
                 attribute_id int(11) not null,
                 label varchar(255) null,
                 required_flag int(11) not null default 0,
                 sort_order int(11) not null default 0,
                 initial_enabled_state int(11) not null default 1, -- by default we enable
                 submit_on_change int(11) not null default 0,
                 default_value varchar(50),
                 placeholder varchar(50),
                 validation_message varchar(100),
                 input_size int(11),
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key (form_step_id) references form_steps(id),
                foreign key (form_step_group_id) references form_step_groups(id),
                foreign key (attribute_id) references attributes(id),
                unique key(form_step_id, attribute_id))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table select_values (id int(11) not null auto_increment,
                form_step_attribute_id int(11) not null,
                select_key varchar(50) not null,
                select_value varchar(100) not null,
                pre_selected int(11) not null default 0,
                created_at datetime,
                updated_at datetime,
                primary key (id),
                foreign key (form_step_attribute_id) references form_step_attributes(id),
                unique key (form_step_attribute_id, select_key)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ===========================================================================
-- filters to enable / disable form steps, form step groups, or form step attributes, based on selections made in other form step attributes (select values)

-- disable other form steps
create table form_step_filters(id int(11) not null auto_increment,
                 form_id int(11) not null,
                 select_value_id int(11) not null,
                 target_form_step_id int(11) not null,
                 enabled_state int(11) not null default 0, -- by default we disable
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key (target_form_step_id) references form_steps(id),
                foreign key (form_id) references forms(id),
                foreign key (select_value_id) references select_values(id),
                unique key(form_id, select_value_id))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- disable form step groups
create table form_step_group_filters(id int(11) not null auto_increment,
                 form_id int(11) not null,
                 select_value_id int(11) not null,
                 target_form_step_group_id int(11) not null,
                 enabled_state int(11) not null default 0, -- by default we disable
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key (target_form_step_group_id) references form_step_groups(id),
                foreign key (form_id) references forms(id),
                foreign key (select_value_id) references select_values(id),
                unique key (form_id, select_value_id))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- disable form step attributes
create table form_step_attribute_filters(id int(11) not null auto_increment,
                 form_id int(11) not null,
                 select_value_id int(11) not null,
                 target_form_step_attribute_id int(11) not null,
                 enabled_state int(11) not null default 0, -- by default we disable
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key (target_form_step_attribute_id) references form_step_attributes(id),
                foreign key (form_id) references forms(id),
                foreign key (select_value_id) references select_values(id),
                unique key(form_id, select_value_id, target_form_step_attribute_id) )  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- filter select options in any other form step attribute in this form (allow master/detail relationships)
-- if there is a detail attribute, it will by default disable all it's select values until a select value is selected in the master
-- for the select_value_id and target_form_step_attribute_id we then look up all target_select_values and show them
create table form_select_filters(id int(11) not null auto_increment,
                 form_id int(11) not null,
                 form_step_attribute_id int(11) not null,
                 select_value_id int(11) not null,
                 target_form_step_attribute_id int(11) not null,
                 target_select_value_id int(11) not null,
                 created_at datetime, updated_at datetime,
                primary key(id),
                foreign key (form_step_attribute_id) references form_step_attributes(id),
                foreign key (target_form_step_attribute_id) references form_step_attributes(id),
                foreign key (form_id) references forms(id),
                foreign key (select_value_id) references select_values(id),
                foreign key (target_select_value_id) references select_values(id),
                unique key (form_id, select_value_id, form_step_attribute_id, target_form_step_attribute_id, target_select_value_id ))  ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ================================================
-- dropping all of it
-- drop table form_select_filters;
--drop table form_step_attribute_filters;
--drop table form_step_group_filters;
--drop table form_step_filters;
--drop table select_values;
--drop table form_step_attributes;
--drop table form_step_groups;
--drop table form_steps;
-- ================================================

