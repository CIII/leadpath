alter table orders add column is_bind_eligible bit default 0 after email;
alter table lead_reporting add column is_bind_eligible bit default 0;
alter table lead_reporting add column contacted bit default 0;
alter table lead_reporting add column appointment_set bit default 0;
alter table lead_reporting add column contract_signed bit default 0;