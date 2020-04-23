insert into publishers values (default, 'MediaAlpha', '', '', 0, 'www.mediaalpha.com', MD5('test'), 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into publisher_lists values (default, '-1', 'MediaAlpha', 'mediaalpha_form', 1, 1, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into publisher_list_members values (default, 23, 19, 0, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into publisher_list_orders values (default, 'MediaAlpha:LG', 23, 18, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into publisher_list_orders values (default, 'MediaAlpha:RGR', 23, 19, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into publisher_list_orders values (default, 'MediaAlpha:BostonSolar', 23, 23, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());