alter table offers add column bcc_address varchar(255) after from_personal;
alter table orders add column target_url varchar(512) after pixel_id;

-- add new posting client config
-- or: pwd = C0up0nz11 ??
insert ignore into publishers (name, allow_duplicates, domain_name, domain_token, created_at) values('coupon-hound.com', 1, 'coupon-hound.com', md5('c0upons'), now());
insert into publisher_lists (lead_type_id, name, ext_list_id, max_lead_units, status, created_at) values(-1, 'coupon-hound: leadConduit', 'lc-1', 1, 1, now());
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'coupon-hound: leadConduit'), (select id from publishers where name = 'coupon-hound.com'),0,1,now(),null);

insert into advertisers values(null, 'LeadConduit', now(), null);
insert into orders (code, advertiser_id, lead_type_id, source_id, target_url, status, created_at) values('leadConduit-20140122', (select id from advertisers where name ='LeadConduit'), -1, "xxAccountId=054ynedgy;xxCampaignId=000q7cfmh;xxTest=1", "https://app.leadconduit.com/v2/PostLeadAction", 1, now());

-- set lead conduit posts up for email
insert into advertiser_writers values(null, -1, (select id from advertisers where name ='LeadConduit'), 'com.pony.advertiser.writers.LeadConduitWriter', now(), null);

-- add newsletter columns to email_target_queues
alter table email_target_queues add column open_within_days int(11) default 0 after max_arrival_id, add column send_frequency_days int(11) default 7 after open_within_days;

-- add coupon newsletter lists
-- samples
insert into publisher_lists (lead_type_id, name, ext_list_id, max_lead_units, status, created_at) values(-1, 'Coupon-Hound Newsletter', 'ch_newsletter', 1, 1, now());
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'Coupon-Hound Newsletter'), (select id from publishers where name = 'coupon-hound.com'),0,1,now(),null);

-- coupons
insert into publisher_lists (lead_type_id, name, ext_list_id, max_lead_units, status, created_at) values(-1, 'Coupon-Hound Newsletter: Coupons', 'ch_newsletter_coupons', 1, 1, now());
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'Coupon-Hound Newsletter: Coupons'), (select id from publishers where name = 'coupon-hound.com'),0,1,now(),null);

-- add the list targeting
-- coupon-hound newsletter => ch_welcome
-- coupon-hound newsletter: coupons => ch_welcome_coupons
insert into email_target_queues (name, from_publisher_list_id, to_publisher_list_id, open_within_days, send_frequency_days, status, created_at) values('', (select id from publisher_lists where name = 'Coupon-Hound Welcome'), (select id from publisher_lists where name = 'Coupon-Hound Newsletter'), 0, 4, 1, now());
insert into email_target_queues (name, from_publisher_list_id, to_publisher_list_id, open_within_days, send_frequency_days, status, created_at) values('', (select id from publisher_lists where name = 'Coupon-Hound Welcome: Coupons'), (select id from publisher_lists where name = 'Coupon-Hound Newsletter: Coupons'), 0, 4, 1, now());

-- new subject line
insert into subject_lines (subject_line, created_at) values('Coupon Hound Savings for @@date@@', now());

-- new offer
insert into offers (name, advertiser_id, lead_type_id, target_url, status, created_at) values('Coupon-Hound NewsLetter', (select id from advertisers where name = 'Coupon-Hound.com'), (select id from lead_types where name = 'Email'), 'http://coupon-hound.com/coupons', 1, now());
insert into offers (name, advertiser_id, lead_type_id, target_url, status, created_at) values('Coupon-Hound NewsLetter: Coupons', (select id from advertisers where name = 'Coupon-Hound.com'), (select id from lead_types where name = 'Email'), 'http://coupon-hound.com/coupons', 1, now());

-- new creative
insert into creatives (name, offer_id, status, created_at, subject_line_id, message_template_id, from_address_id) values ('Coupon-Hound Newsletter', (select id from offers where name ='Coupon-Hound NewsLetter'), 1, now(), (select id from subject_lines where subject_line = 'Coupon Hound Savings for @@date@@'), (select id from message_templates where name = 'Coupon-Hound Newsletter'), (select id from from_addresses where from_address = 'jenny@coupon-hound.com'));
insert into creatives (name, offer_id, status, created_at, subject_line_id, message_template_id, from_address_id) values ('Coupon-Hound Newsletter: Coupons', (select id from offers where name ='Coupon-Hound NewsLetter: Coupons'), 1, now(), (select id from subject_lines where subject_line = 'Coupon Hound Savings for @@date@@'), (select id from message_templates where name = 'Coupon-Hound Newsletter: Coupons'), (select id from from_addresses where from_address = 'jenny@coupon-hound.com'));

-- list_splits
insert into list_splits (publisher_list_id, offer_id, percentage, status, created_at) values((select id from publisher_lists where name = 'Coupon-Hound Newsletter'), (select id from offers where name = 'Coupon-Hound NewsLetter'), 100, 1, now());
insert into list_splits (publisher_list_id, offer_id, percentage, status, created_at) values((select id from publisher_lists where name = 'Coupon-Hound Newsletter: Coupons'), (select id from offers where name = 'Coupon-Hound NewsLetter: Coupons'), 100, 1, now());

-- offer splits
insert into offer_splits (list_split_id, creative_id, host_id, percentage, status, created_at) values((select ls.id from list_splits ls join publisher_lists pl on pl.id = ls.publisher_list_id where pl.name = 'Coupon-Hound Newsletter'), (select id from creatives where name = 'Coupon-Hound Newsletter'), 3, 100, 1, now());
insert into offer_splits (list_split_id, creative_id, host_id, percentage, status, created_at) values((select ls.id from list_splits ls join publisher_lists pl on pl.id = ls.publisher_list_id where pl.name = 'Coupon-Hound Newsletter: Coupons'), (select id from creatives where name = 'Coupon-Hound Newsletter: Coupons'), 3, 100, 1, now());

-- new table (and changes) for direct orders (allow the publisher to target a particular order (or set of orders)
alter table publisher_lists add column is_direct tinyint default 0 after status;

create table publisher_list_orders (id int(11) not null auto_increment, name varchar(255) not null, publisher_list_id int(11) not null, order_id int(11) not null, status tinyint, created_at datetime, updated_at datetime, primary key(id)) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- for each leadConduit order
--insert into publisher_lists values(null, -1, 'lc-inbox_dollar_2', 'lc-2', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 20, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-inbox_$', 11, -1, 0, 'affiliate_auth=pexONzK12907797;sub_affiliate=xx;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-2', 20, 7, 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'lc-5in5now', 'lc-3', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 21, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-5in5now', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=052krnzo6;affkey=???;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-3', 21, 8, 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'lc-honest_inside', 'lc-4', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 22, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-honest_inside', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=000myq7p0;affkey=??;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-4', 22, 9, 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'lc-savvyFork', 'lc-5', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 23, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-savvy_fork', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=054yndugo;affkey=??;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-5', 23, 10, 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'lc-netspend', 'lc-6', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 24, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-netspend', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=052pdja2b;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-6', 24, 11, 1, now(), null);

--insert into publisher_lists values(null, -1, 'lc-gofreebies', 'lc-7', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 25, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140129-gofreebies', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=054ecwmnu;affkey=67700', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-7', 25, 12, 1, now(), null);

-- add 3 new offers and two new writers
--insert into publisher_lists values(null, -1, 'lc-10', 'lc-10', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 26, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140204-nextStepU', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=051dumw9v;affkey=67700;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-10', 26, 13, 1, now(), null);
--
---- new advertiser
--insert into advertisers values(null, 'PrizmMedia', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='PrizmMedia'), 'com.pony.advertiser.writers.PrizmMediaWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'prizm-1', 'prizm-1', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 27, 11, 0, 1, now(), null);
--insert into orders values(null, 'prizmMedia-20140204-1', 12, -1, 0, 'leadtype=NONMEDICARE;pub=asc_pi_pub', 0, 1, 0, 0, 0, null, 'http://www.homecareerleads.com/xfer/index.php', now(), null);
--insert into publisher_list_orders values(null, 'prizm-1', 27, 14, 1, now(), null);
--
---- new advertiser
--insert into advertisers values(null, 'ArcaMax', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='ArcaMax'), 'com.pony.advertiser.writers.ArcaMaxWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'arca-1', 'arca-1', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 28, 11, 0, 1, now(), null);
--insert into orders values(null, 'arcaMax-20140204-1', 13, -1, 0, 'listid.1=15;listid.2=52;source=3725', 0, 1, 0, 0, 0, null, 'http://www.arcamax.com/cgi-bin/autosub', now(), null);
--insert into publisher_list_orders values(null, 'arca-1', 28, 15, 1, now(), null);
--
---- ======
---- new advertiser
--insert into advertisers values(null, 'TmgInteractive', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='TmgInteractive'), 'com.pony.advertiser.writers.TmgInteractiveWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'tmg-1', 'tmg-1', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 30, 11, 0, 1, now(), null);
--insert into orders values(null, 'tmg-holidaycruise', 14, -1, 0, 'publisherId=161710;cCampId=10485', 0, 1, 0, 0, 0, null, 'http://process.tmginteractive.com/10485/Camp10485.aspx', now(), null);
--insert into publisher_list_orders values(null, 'tmg-1', 30, 17, 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'tmg-2', 'tmg-2', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 31, 11, 0, 1, now(), null);
--insert into orders values(null, 'tmg-harpersbazaar', 14, -1, 0, 'publisherId=161710;cCampId=10391', 0, 1, 0, 0, 0, null, 'http://process.tmginteractive.com/10391/Camp10391.aspx', now(), null);
--insert into publisher_list_orders values(null, 'tmg-2', 31, 18, 1, now(), null);

-- more lead conduit
--insert into publisher_lists values(null, -1, 'lc-shadowShopper', 'lc-shasho', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 32, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140225-shadowShopper', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=de0ea2;affkey=67700;id=15;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-shadowShopper', 32, 19, 1, now(), null);

-- med care
--insert into advertisers values(null, 'MedCare', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='MedCare'), 'com.pony.advertiser.writers.MedCareWriter', now(), null);
--insert into publisher_lists values(null, -1, 'MedCare', 'medcare', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'MedCare'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--insert into orders values(null, 'medcare', (select id from advertisers where name = 'MedCare'), -1, 0, 'Token=--------qxKHl0C2rloF8FphlEOCx5TIDrwU1GyNb7PH3K4DAEHDd4QjDeg7Iw4uMcd3EQb--------;Products=SA', 0, 1, 0, 0, 0, null, 'https://api.medcareinc.net/lead?format=json', now(), null);
--insert into publisher_list_orders values(null, 'medcare', (select id from publisher_lists where name = 'MedCare'), (select id from orders where code = 'medcare'), 1, now(), null);

-- Castle
--insert ignore into publishers (name, allow_duplicates, domain_name, domain_token, created_at) values('AMN-SecureLeads', 1, 'amn', md5('amn-123'), now());
--
--insert into advertisers values(null, 'CastleMortgage', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='CastleMortgage'), 'com.pony.advertiser.writers.CastleMortgageWriter', now(), null);
--insert into publisher_lists values(null, -1, 'CastleMortgage', 'ccc', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'CastleMortgage'), (select id from publishers where name = 'AMN-SecureLeads'), 0, 1, now(), null);
--
--insert into orders values(null, 'castle', (select id from advertisers where name = 'CastleMortgage'), -1, 0, 'pixel_id=1168;crm_url=https://www.as-leads.com', 0, 1, 0, 0, 0, null, 'https://ourFive9-instance.xxx', now(), null);
--insert into publisher_list_orders values(null, 'castle', (select id from publisher_lists where name = 'CastleMortgage'), (select id from orders where code = 'castle'), 1, now(), null);


-- VikingMagazinWriter
--insert into advertisers values(null, 'VikingMagazin', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='VikingMagazin'), 'com.pony.advertiser.writers.VikingMagazinWriter', now(), null);
--insert into publisher_lists values(null, -1, 'VikingMagazin', 'viking', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'VikingMagazin'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--insert into orders values(null, 'VikingMagazin', (select id from advertisers where name = 'VikingMagazin'), -1, 0, '', 0, 1, 0, 0, 0, null, 'http://www.vikingmagazine.com/vici/create_lead_as1.asp', now(), null);
--insert into publisher_list_orders values(null, 'VikingMagazin', (select id from publisher_lists where name = 'VikingMagazin'), (select id from orders where code = 'VikingMagazin'), 1, now(), null);

--insert into publisher_lists values(null, -1, 'lc-petInsurance', 'lc-petIns45', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, 36, 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140331-petInsuranceD45', 11, -1, 0.4, 'xxAccountId=054ynedgy;xxCampaignId=0554jpcrt;affkey=67700;offer_id=11;sub_id=LBM1;category=Pet Insurance;xxTest=true', 0, 1, 100, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-shadowShopper', 32, 19, 1, now(), null);
--
---- TMG Interactive for GodVine
---- campaign = 10337
--insert into publisher_lists values(null, -1, 'tmg-GodVine', 'tmg-GodVine', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'tmg-GodVine'), 11, 0, 1, now(), null);
--insert into orders values(null, 'tmg-GodVine', 14, -1, 0, 'publisherId=161710;cCampId=10337', 0, 1, 0, 0, 0, null, 'http://process.tmginteractive.com/10337/Camp10337.aspx', now(), null);
--insert into publisher_list_orders values(null, 'tmg-GodVine', (select id from publisher_lists where name = 'tmg-GodVine'), (select id from orders where code = 'tmg-GodVine'), 1, now(), null);

-- AllianceYachts (via separate site)
--insert into publishers values(null, 'AllianceYachts', '', '', 1, 'www.allianceYachts.com', MD5('alliance'), 0, now(), null);
--insert into publisher_lists values(null, -1, 'AllianceYachts', 'allianceYachts', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'AllianceYachts'), (select id from publishers where name = 'AllianceYachts'), 0, 1, now(), null);
--insert into advertisers values(null, 'AllianceYachts', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name = 'allianceYachts'), 'com.pony.advertiser.writers.EmailWriter', now(), null);
--insert into orders values(null, 'allianceYachts', (select id from advertisers where name = 'allianceYachts'), -1, 0, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, 1, 0, 0, 0, null, null, now(), null);
--insert into publisher_list_orders values(null, 'allianceYachts', (select id from publisher_lists where name = 'allianceYachts'), (select id from orders where code = 'allianceYachts'), 1, now(), null);

-- Amerifund Mortgage (via seprate site)
--insert into publishers values(null, 'Amerifund', '', '', 1, 'www.amerifund-mortgage.com', MD5('m0rtgage'), 0, now(), null);
--insert into publisher_lists values(null, -1, 'Amerifund', 'amerifund', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'Amerifund'), (select id from publishers where name = 'Amerifund'), 0, 1, now(), null);
--insert into advertisers values(null, 'AmerifundMortgage', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name = 'AmerifundMortgage'), 'com.pony.advertiser.writers.EmailWriter', now(), null);
--insert into orders values(null, 'Amerifund-Mortgage: New Registration', (select id from advertisers where name = 'AmerifundMortgage'), -1, 0, 'u=leads@acquisition-labs.com;p=ABC!!456;from=leads@acquisition-labs.com;to=martin@acquisition-labs.com', 1, 1, 0, 0, 0, null, null, now(), null);
--insert into publisher_list_orders values(null, 'Amerifund', (select id from publisher_lists where name = 'Amerifund'), (select id from orders where code = 'Amerifund-Mortgage: New Registration'), 1, now(), null);

-- Provide Media (coupons)
--insert into advertisers values(null, 'ProvideMedia', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name ='ProvideMedia'), 'com.pony.advertiser.writers.ProvideMediaWriter', now(), null);
--insert into publisher_lists values(null, -1, 'ProvideMedia:StressCenter', 'StressCenter.com', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'ProvideMedia:StressCenter'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--insert into orders values(null, 'ProvideMedia:StressCenter', (select id from advertisers where name = 'ProvideMedia'), -1, 0, 'pid=a3AxeWR6MnVWa3F0WHk0d2tXNitjQT09;oid=ang4N3IwNlNkVFM0Ny85U1NNMCtXQT09', 0, 1, 0, 0, 0, null, 'http://providemedia.leadshot.net/gateway/gateway.php', now(), null);
--insert into publisher_list_orders values(null, 'ProvideMedia:StressCenter', (select id from publisher_lists where name = 'ProvideMedia:StressCenter'), (select id from orders where code = 'ProvideMedia:StressCenter'), 1, now(), null);

-- Child Health Safety
--insert into publisher_lists values(null, -1, 'lc-childHealth', 'lc-childHealth', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'lc-childHealth'), 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140414-childHealthSafety', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=0558hx8a5;CampaignId=E8E04E34319481DEFAA06B64EEBB1C7A;affkey=67700;Source=DPM20;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-childHealth', (select id from publisher_lists where name = 'lc-childHealth'), (select id from orders where code = 'leadConduit-20140414-childHealthSafety'), 1, now(), null);

-- Safe at home
--insert into publisher_lists values(null, -1, 'lc-safeAtHome', 'lc-safeAtHome', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'lc-safeAtHome'), 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140414-safeAtHome', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=0558hx8a8;affkey=67700;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-safeAtHome', (select id from publisher_lists where name = 'lc-safeAtHome'), (select id from orders where code = 'leadConduit-20140414-safeAtHome'), 1, now(), null);

-- RealTimeData - Debt

-- new advertiser: theBillCoach
-- requires new attribute debt_amount
--insert into advertisers values(null, 'TheBillCoach', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name = 'TheBillCoach'), 'com.pony.advertiser.writers.RealTimeDataWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'RTData-debt', 'RTData-debt', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'RTData-debt'), 11, 0, 1, now(), null);
--insert into orders values(null, 'RTData-credit_counseling', (select id from advertisers where name = 'TheBillCoach'), -1, 0, 'source_id=1696;source_password=06CA767908DC', 0, 1, 0, 0, 0, null, 'http://www.realtimedata.biz/Post.aspx', now(), null);
--insert into publisher_list_orders values(null, 'RTData-credit_counseling', (select id from publisher_lists where name = 'RTData-debt'), (select id from orders where code = 'RTData-credit_counseling'), 1, now(), null);
--
--insert into publisher_lists values(null, -1, 'RTData-studentLoan', 'RTData-studentLoan', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'RTData-studentLoan'), 11, 0, 1, now(), null);
--insert into orders values(null, 'RTData-studentLoan', (select id from advertisers where name = 'TheBillCoach'), -1, 0, 'source_id=1697;source_password=0C17A2EA34EF', 0, 1, 0, 0, 0, null, 'http://www.realtimedata.biz/Post.aspx', now(), null);
--insert into publisher_list_orders values(null, 'RTData-studentLoan', (select id from publisher_lists where name = 'RTData-studentLoan'), (select id from orders where code = 'RTData-studentLoan'), 1, now(), null);


-- new advertiser: MocanMedia
--insert into advertisers values(null, 'MocanMedia', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name = 'MocanMedia'), 'com.pony.advertiser.writers.MocanMediaWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'mocan', 'mocan', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'mocan'), 11, 0, 1, now(), null);
--insert into orders values(null, 'mocan', (select id from advertisers where name = 'MocanMedia'), -1, 0, 'an=couponhound;ap=XAx482t!22;cid=418', 0, 1, 0, 0, 0, null, 'http://post.consent2call.com/postdata.aspx', now(), null);
--insert into publisher_list_orders values(null, 'mocan', (select id from publisher_lists where name = 'mocan'), (select id from orders where code = 'mocan'), 1, now(), null);

--Pain Cream
--insert into publisher_lists values(null, -1, 'lc-painCream', 'lc-painCream', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name='lc-painCream'), 11, 0, 1, now(), null);
--insert into orders values(null, 'leadConduit-20140418-painCream', 11, -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=0558hx8eu;affkey=67700;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
--insert into publisher_list_orders values(null, 'lc-painCream', (select id from publisher_lists where name = 'lc-painCream'), (select id from orders where code = 'leadConduit-20140418-painCream'), 1, now(), null);

---- new advertiser Achieve Finances
--insert into advertisers values(null, 'AchieveFinances', now(), null);
--insert into advertiser_writers values(null, -1, (select id from advertisers where name = 'AchieveFinances'), 'com.pony.advertiser.writers.AchieveFinancesWriter', now(), null);
--
--insert into publisher_lists values(null, -1, 'achieveFinances', 'achieveFinances', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'achieveFinances'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--insert into orders values(null, 'achieveFinances', (select id from advertisers where name = 'AchieveFinances'), -1, 0, 'VendorId=457;Password=as540f5d', 0, 1, 0, 0, 0, null, 'https://www.achieve-secure-application.com:8080/AchieveCardServiceWeb/AfsGet', now(), null);
--insert into publisher_list_orders values(null, 'achieveFinances', (select id from publisher_lists where name = 'achieveFinances'), (select id from orders where code = 'achieveFinances'), 1, now(), null);

-- new LC offer: AccountNow
insert into publisher_lists values(null, -1, 'AccountNow', 'AccountNow', 1, 1, 1, now(), null);
insert into publisher_list_members values(null, (select id from publisher_lists where name = 'AccountNow'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
insert into orders values(null, 'AccountNow', (select id from advertisers where name = 'LeadConduit'), -1, 0, 'xxAccountId=054ynedgy;xxCampaignId=0558hx8j1;affkey=67700;xxTest=true', 0, 1, 0, 0, 0, null, 'https://app.leadconduit.com/v2/PostLeadAction', now(), null);
insert into publisher_list_orders values(null, 'AccountNow', (select id from publisher_lists where name = 'AccountNow'), (select id from orders where code = 'AccountNow'), 1, now(), null);
insert into publisher_list_orders (select null, 'CH-ping-link', (select id from publisher_lists where name = 'CH-ping'), (select id from orders where code = 'AccountNow'), 1, now(), null);
-- TODO: turn off old offer replaced by this new one
update publisher_list_orders set status = 0 where publisher_list_id = (select id from publisher_lists where name = 'CH-ping') and order_id = (select id from orders where code = 'achieveFinances');


--
--
---- populate the ping list
--insert into publisher_lists values(null, -1, 'CH-ping', 'ch_ping', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'CH-ping'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--insert into publisher_list_orders (select null, 'CH-ping-link', (select id from publisher_lists where name = 'CH-ping'), o.id , 1, now(), null from orders o join publisher_list_orders plo on plo.order_id = o.id join publisher_lists pl on pl.id = plo.publisher_list_id join publisher_list_members plm on plm.publisher_list_id = pl.id join publishers p on p.id = plm.publisher_id where p.name = 'coupon-hound.com' and pl.status = 1 and o.status = 1 and pl.is_direct = 1);


-- add advertiser for Coupon-Hound call center
--insert into advertisers values(null, 'coupon-hound.com Call Center', now(), now());
--insert into advertiser_writers values(null, -1, (select id from advertisers where name='coupon-hound.com Call Center'), 'com.pony.advertiser.writers.Five9Writer', now(), now());
--insert into publisher_lists values(null, -1, 'ch-callcenter', 'ch_cc', 1, 1, 1, now(), null);
--insert into publisher_list_members values(null, (select id from publisher_lists where name = 'ch-callcenter'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
--
--insert into orders values(null, 'ch-callcenter', (select id from advertisers where name = 'coupon-hound.com Call Center'), -1, 0, 'F9domain=Acquisition Sciences;F9list=Call Center List;crm_column=leadId', 0, 1, 0, 0, 0, null, 'https://api.five9.com/web2campaign/AddToList', now(), null);
--insert into publisher_list_orders values(null, 'ch-callcenter', (select id from publisher_lists where name = 'ch-callcenter'), (select id from orders where code = 'ch-callcenter'), 1, now(), null);

-- add generic Coupon-Hound Callcenter advertiser to receive all warm transfers (unless we have an actual writer for that advertiser)
-- TODO
insert into advertisers values(null, 'coupon-hound.com Call Center Warm Transfer Receiver', now(), now());
insert into advertiser_writers values(null, -1, (select id from advertisers where name='coupon-hound.com Call Center Warm Transfer Receiver'), 'com.pony.advertiser.writers.EmailWriter', now(), now());
