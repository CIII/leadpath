alter table offers add column from_address varchar(255) after unsubscribe_url;

create table auto_correct_domains(id int(11) not null auto_increment, from_mail_host_id int(11) not null, to_mail_host_id int(11) not null, created_at datetime, updated_at datetime, primary key(id), foreign key (from_mail_host_id) references mail_hosts(id), foreign key (to_mail_host_id) references mail_hosts(id), unique key(from_mail_host_id, to_mail_host_id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

alter table bounces add column status_code int(11) after external_id;

alter table mail_hosts add column mx_validated int(11) default 1;

insert into smtp_providers (name, created_at) values('SendGrid',now());


-- reverse:
delete from smpt_providers where name = 'SendGrid';
alter table mail_hosts drop column mx_validated;
alter table bounces drop column status_code;
drop table auto_correct_domains;
alter table offers drop column from_address;
