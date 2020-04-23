create UNIQUE index `publisher_list_members_unq` on publisher_list_members (`publisher_list_id`,`publisher_id`);
create unique index `publisher_list_orders_unq` on publisher_list_orders (`publisher_list_id`, `order_id`);
