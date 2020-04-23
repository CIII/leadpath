-- log the actual messages that go across the wire
create table lead_posts (id int(11) not null auto_increment, lead_match_id int(11) not null, sent_message text, response_message text , created_at datetime, primary key(id), foreign key(lead_match_id) references lead_matches(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
