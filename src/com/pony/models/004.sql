alter table offer_splits add column list_split_id int(11) after offer_id;
alter table offer_splits add foreign key (list_split_id) references list_splits(id);
update offer_splits os, list_splits ls set os.list_split_id = ls.id where ls.offer_id = os.offer_id;
alter table offer_splits change list_split_id list_split_id int(11) not null;

alter table offer_splits drop foreign key offer_splits_ibfk_1;
alter table offer_splits drop column offer_id;

alter table opens add index (created_at);
alter table messages add index (created_at, host_id, creative_id);
alter table messages add index (external_id, host_id);
