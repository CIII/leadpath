-- monthly performance:

--clicks.net
-- UN: dualcity
-- PW: passw0rd
--
--popularmarketing.com
--jeff@dualcityinc.com
--dualinccity1
--
----**thankyoupath.com
----** USER:jeff@dualcityinc.com
----** PASS: 86west
--


-- daily performance by host and creative
select date(convert_tz(m.created_at, 'UTC', 'America/New_York')) day, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
  from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  group by date(convert_tz(m.created_at, 'UTC', 'America/New_York')) , m.host_id, m.creative_id order by date(convert_tz(m.created_at, 'UTC', 'America/New_York')) , m.host_id, m.creative_id;

-- send stats per day
select date(convert_tz(m.created_at, 'UTC', 'America/New_York'))  day, count(distinct m.id) messages, sum(if(m.status=4, 1, 0)) delivered, count(distinct b.user_profile_id) bounces, sum(if(m.status=3, 1, 0)) deferres, sum(if(m.status=5, 1, 0)) dropps, count(distinct u.user_profile_id) unsubs, count(distinct o.message_id) d_opens, sum(if(o.open_count = 0, 1, o.open_count)) opens, count(distinct c.message_id) d_clicks, sum(c.click_count) clicks
 , round(100*sum(if(m.status=4, 1, 0))/count(distinct m.id), 2)  del_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate, round(100*count(distinct u.user_profile_id)/(sum(if(m.status=4, 1, 0))),2) unsub_rate, round(100*sum(if(o.open_count = 0, 1, o.open_count))/( sum(if(m.status=4, 1, 0)) - (count(distinct b.user_profile_id)) ),2) open_rate, round(100*count(distinct o.message_id)/( sum(if(m.status=4, 1, 0)) - (count(distinct b.user_profile_id)) ),2) d_open_rate, round(100*sum(c.click_count)/( sum(if(m.status=4, 1, 0)) - (count(distinct b.user_profile_id)) ),2) click_rate, round(100*count(distinct c.message_id)/( sum(if(m.status=4, 1, 0)) - (count(distinct b.user_profile_id)) ),2) d_click_rate, round(100*sum(c.click_count)/sum(if(o.open_count = 0, 1, o.open_count)),2) click_rate_open
  from messages m join arrivals a on a.user_profile_id = m.user_profile_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  where date(convert_tz(m.created_at, 'UTC', 'America/New_York')) >= '2013-12-05'
  group by date(convert_tz(m.created_at, 'UTC', 'America/New_York'))  order by date(convert_tz(m.created_at, 'UTC', 'America/New_York')) , m.host_id, m.creative_id;

-- send stats per day and offer
select date(convert_tz(m.created_at, 'UTC', 'America/New_York')) day, o.id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
  from messages m join arrivals a on a.user_profile_id = m.user_profile_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  where m.created_at > '2013-03-01'
    and a.validation_code = 0
    and a.publisher_id in (2,7)
  group by date(convert_tz(m.created_at, 'UTC', 'America/New_York')) order by date(convert_tz(m.created_at, 'UTC', 'America/New_York')), m.host_id, m.creative_id;

-- add end to end ctr
select date(convert_tz(m.created_at, 'UTC', 'America/New_York')) , m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
  from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  where m.created_at > curdate()-7
  group by date(convert_tz(m.created_at, 'UTC', 'America/New_York')) , m.host_id, m.creative_id order by date(convert_tz(m.created_at, 'UTC', 'America/New_York')), m.host_id, m.creative_id;
  --and m.host_id in (1,2,18) and m.creative_id in (99,109,117,118)

-- timezone adjusted
-- convert_tz(now(), 'UTC','America/New_York')
select date(convert_tz(m.created_at,'UTC','America/New_York')) day, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
  from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  -- where convert_tz(m.created_at,'UTC','America/New_York') > '2012-12-18'
  where convert_tz(m.created_at,'UTC','America/New_York') > '2012-12-10'  and m.host_id in (1,2,18) and m.creative_id in (99,109,117,118)
  group by date(convert_tz(m.created_at,'UTC','America/New_York')), m.host_id, m.creative_id order by date(m.created_at), m.host_id, m.creative_id;

-- grouped by delay between send and open
select timestampdiff(DAY, m.created_at, o.created_at) open_delay, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
  from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id
  where m.created_at > date_add(now(), interval -7 day)
   and (o.id is null or convert_tz(o.created_at, 'UTC','America/New_York') > curdate())
  group by timestampdiff(DAY, m.created_at, o.created_at) , m.host_id, m.creative_id
  order by m.host_id, m.creative_id, timestampdiff(DAY, m.created_at, o.created_at);


-- monthly reporting
select a.name advertiser, of.name offer, cr.id creative_id, cr.subject_line, count(distinct m.id) sends, count(distinct o.message_id) opens, count(distinct c.message_id) clicks, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr
--  , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
 from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join creatives cr on cr.id = m.creative_id join offers of on of.id = cr.offer_id join advertisers a on a.id = of.advertiser_id
 where advertiser_id not in (1,6,7)
  and m.created_at > '2013-03-01'  and m.created_at < '2013-04-01'
  group by of.advertiser_id, of.id, m.creative_id
  order by advertiser_id, of.id, m.creative_id

-- more details by creative
select cr.id creative_id, cr.subject_line, count(distinct m.id) sends, count(distinct o.message_id) opens, count(distinct c.message_id) clicks, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr
  , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate
 from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join creatives cr on cr.id = m.creative_id join offers of on of.id = cr.offer_id join advertisers a on a.id = of.advertiser_id
 where advertiser_id not in (1,6,7)
  and m.created_at > '2013-03-01'  and m.created_at < '2013-04-01'
  group by m.creative_id
  order by m.creative_id

-- details by advertiser
select a.name, count(distinct m.id) sends, count(distinct o.message_id) opens, count(distinct c.message_id) clicks, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.id)/count(distinct m.id) ,2) ctr   , round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate  from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join creatives cr on cr.id = m.creative_id join offers of on of.id = cr.offer_id join advertisers a on a.id = of.advertiser_id
  where advertiser_id not in (1,6,7)   and m.created_at > '2013-01-01'  and m.created_at < '2013-02-01'
  group by a.id order by a.name;

-- publisher performance
select p.name, count(*) total, sum(if(validation_code = 0, 1, 0)) success, sum(if(validation_code = 7,1,0)) dups, sum(if(validation_code = 2,1,0)) suppressed, sum(if(validation_code = 10,1,0)) capped,      100*(round( sum(if(validation_code = 0,1,0))/count(*)  ,2)) success_rate,  100*(round( sum(if(validation_code = 7,1,0))/count(*)  ,2)) dup_rate, 100*(round( sum(if(validation_code = 2,1,0))/count(*)  ,2)) supression_rate, 100*(round( sum(if(validation_code =10,1,0))/count(*)  ,2)) capped_rate   from arrivals a join publishers p on p.id = a.publisher_id
  where year(a.created_at) = 2013 and month(a.created_at) = 3 and publisher_id != 4
  group by publisher_id  having count(*) > 10   order by publisher_id;

-- bounce analysis

-- basic bounces
select mh.name, status_code, count(distinct(b.user_profile_id)) bounces
  from  user_profiles up join bounces b on up.id = b.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id join messages m on m.user_profile_id = up.id
 -- where
 -- m.host_id in (1,2)  and
 -- m.created_at > '2013-02-01' and m.created_at < '2013-03-01'
group by mh.name , status_code having count(*) > 10 order by mh.name, count(*) desc

-- bounce rate by ESP
select mh.name, count(distinct m.id) messages, count(distinct b.user_profile_id) bounces, round(100*count(b.user_profile_id)/count(distinct m.id), 2) bounce_rate
  from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id left join bounces b on b.user_profile_id = up.id
  group by mh.name having count(b.id) > 5
  order by count(distinct m.id)  desc

-- by ESP and message (bounce reason)
select mh.name, count(b.user_profile_id) bounces, if(b.message is null, 'n/a', substr(b.message, 1, 45)) msg
  from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id join bounces b on b.user_profile_id = up.id
 group by mh.name, if(b.message is null, 'n/a', substr(b.message, 1, 45)) having count(b.user_profile_id)  > 5
 order by count(distinct m.id)  desc

-- by traffic type: yahoo, gmail : user account does not exist
select pa.value traffic_type, mh.name ISP, count(distinct m.id) send, count(distinct(b.user_profile_id)) bounces, round(100*count(distinct b.user_profile_id)/count(distinct m.id),2) bounce_rate
 from  user_profiles up left join bounces b on up.id = b.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id join profile_attributes pa on pa.user_profile_id = up.id and pa.attribute_id = 9 join messages m on m.user_profile_id = up.id
 where m.host_id in (1,2) and (status_code is null or status_code in(550, 554)) and m.created_at > '2013-03-01' and (message is null or message like '%account%') and mh.id in (1,2,18,20,32)
 group by pa.value, mh.name having count(distinct m.id) > 40
 order by mh.name, count(*) desc

-- ========================
-- leads
-- ========================
-- lead performance (ping and buyer based / for cars)
select a.arrival_source_id, date(convert_tz(l.created_at,'UTC','America/New_York')) day, lm.lead_id, lm.id lead_match_id, lm.price, adb.status, sum(adb.price)
  from lead_matches lm join advertiser_dispositions ad on ad.lead_match_id = lm.id join advertiser_disposition_buyers adb on adb.advertiser_disposition_id = ad.id join leads l on l.id = lm.lead_id join arrivals a on a.id = l.arrival_id
 where adb.status = 1
 group by lm.lead_id, lm.id, lm.price, adb.status, a.arrival_source_id, date(convert_tz(l.created_at,'UTC','America/New_York'))
 order by arrival_source_id, date(convert_tz(l.created_at,'UTC','America/New_York')), lead_id;

--
-- interesting subject lines:
-- Subject: =?utf-8?Q?=E2=9C=88_=31=30_Days_in_Italy_=2B_Flights?=
--	    (shows as: ✈ 10 Days in Italy + Flights)

-- Subject: =?utf-8?Q?Divas=20We=20=E2=99=A5=20=3A=20Latham=20Thomas=E2=80=94The=20sky=20isn=E2=80=99t=20the=20limit=3B=20it=E2=80=99s=20the=20view.?=
--      (shows as: Divas We ♥ : Latham Thomas—The sky isn’t the limit; it’s the view.)

-- subject: =?UTF-8?B?8J+alyAkMTYrL2RheSBSZW50YWwgQ2FyIERlYWxzISBCb29rIFRv?=
--             =?UTF-8?B?ZGF5Lg==?=
--      (shows as: 🚗 $16+/day Rental Car Deals! Book Today.)

-- ============
-- WiserAuto
-- ============

-- no coverage (by order, zip , make, model)
select o.code order_code,
   (select pa.value from ping_attributes pa where pa.lead_id = l.id and attribute_id = 35) zip,
   (select pa.value from ping_attributes pa where pa.lead_id = l.id and attribute_id = 36) make,
   (select pa.value from ping_attributes pa where pa.lead_id = l.id and attribute_id = 34) model,
   lm.id lead_match_id
  from lead_matches lm join leads l on l.id = lm.lead_id join advertiser_dispositions ad on ad.lead_match_id = lm.id and ad.pony_phase = 1 join orders o on o.id = lm.order_id
 where l.created_at > curdate()-2 and ad.status in(1,3)
 order by o.code, zip

-- pings and posts (raw):
-- phase 1 = ping, 3 = post
select date(convert_tz(created_at, 'UTC', 'America/New_York')), pony_phase, count(*)
  from lead_posts
 where date(convert_tz(created_at, 'UTC', 'America/New_York')) >= '2013-10-24'
  group by date(convert_tz(created_at, 'UTC', 'America/New_York')), pony_phase
  order by date(convert_tz(created_at, 'UTC', 'America/New_York')), pony_phase

-- lead posts (ping and post)
select date(convert_tz(lp.created_at, 'UTC', 'America/New_York')) day, pony_phase, count(distinct lead_id) leads, count(*) legs
  from lead_posts lp join lead_matches lm on lm.id = lp.lead_match_id
  where date(convert_tz(lp.created_at, 'UTC', 'America/New_York')) > '2013-10-24'
-- only successful posts
-- and lm.status = 4
 group by date(convert_tz(lp.created_at, 'UTC', 'America/New_York')), pony_phase;

-- using lead_posts
select date(convert_tz(lp.created_at, 'UTC', 'America/New_York')) day, pony_phase, count(distinct lead_id) leads, count(*) legs   from lead_posts lp join lead_matches lm on lm.id = lp.lead_match_id join leads l on l.id = lm.lead_id join arrivals a on a.id = l.arrival_id  where date(convert_tz(a.created_at, 'UTC', 'America/New_York')) > '2014-01-24'    group by date(convert_tz(lp.created_at, 'UTC', 'America/New_York')), pony_phase;

-- find failed posts and their response details
-- Note; there can be successful ones mixed in for lead ids that have both successful and failed posts!
 select lm.lead_id, lm.order_id, lm.external_id, ad.status, ad.price, lp.response_message
  from advertiser_dispositions ad join lead_matches lm on lm.id = ad.lead_match_id join lead_posts lp on lp.lead_match_id = lm.id and lp.pony_phase = 3
  where ad.pony_phase = 3 and date(convert_tz(ad.created_at, 'UTC', 'America/New_York')) = '2013-10-25' and ad.status = 0
  order by lm.id desc

-- advertiser dispositions
select date(convert_tz(ad.created_at, 'UTC', 'America/New_York')) day, pony_phase, status, count(*) dealer_cnt, count(distinct lead_match_id) partner_cnt
  from advertiser_dispositions ad
 where date(convert_tz(ad.created_at, 'UTC', 'America/New_York')) = '2013-10-25'
 group by date(convert_tz(ad.created_at, 'UTC', 'America/New_York')), pony_phase, status
 order by date(convert_tz(ad.created_at, 'UTC', 'America/New_York')), pony_phase, status;

-- posts with potential false response
select lead_match_id, response_message
  from lead_posts
 where date(convert_tz(created_at, 'UTC', 'America/New_York')) = '2013-10-26' and pony_phase = 3 and response_message like '%false%';

-- revenue from advertiser disposition buyers
select a.external_id, b.id buyer_id, lm.id match_id, lm.lead_id, convert_tz(lm.created_at, 'UTC', 'America/New_York') created_at, lm.order_id, b.price, ad.price, lm.price
  from lead_matches lm join advertiser_dispositions ad on ad.lead_match_id = lm.id join advertiser_disposition_buyers b on b.advertiser_disposition_id = ad.id join leads l on lm.lead_id = l.id join arrivals a on a.id = l.arrival_id
 where lm.status =4 and b.status =1 and date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) = '2013-10-26'
 order by lead_id, lm.id

-- dealer coverage:
select order_id, ad.status, count(distinct l.id) pings, count(distinct lm.id) partner_pings, count(adb.id) dealers_returned
  from leads l left join lead_matches lm on l.id = lm.lead_id left join advertiser_dispositions ad on ad.lead_match_id = lm.id left join advertiser_disposition_buyers adb on adb.advertiser_disposition_id = ad.id
 where convert_tz(l.created_at, 'UTC', 'America/New_York') > '2013-10-20'
 group by order_id, ad.status;

-- coverage by advertiser with metrics:
select order_id, ad.status, count(distinct l.id) pings, count(distinct lm.id) partner_pings, count(adb.id) dealers_returned, round(count(adb.id)/count(distinct lm.id),2) dpp, sum(adb.price) rev_potential, round(sum(coalesce(adb.price, 0))/count(distinct l.id), 2) rpp, round(sum(coalesce(adb.price, 0))/count(adb.id), 2) rpd
  from leads l left join lead_matches lm on l.id = lm.lead_id left join advertiser_dispositions ad on ad.lead_match_id = lm.id left join advertiser_disposition_buyers adb on adb.advertiser_disposition_id = ad.id
 where convert_tz(l.created_at, 'UTC', 'America/New_York') > '2013-10-10'
 group by order_id, ad.status

-- more ping coverage
select date(convert_tz(l.created_at, 'UTC', 'America/New_York')) day, count(distinct l.id) pings, count(distinct lm.id) partner_pings, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
  from leads l left join lead_matches lm on lm.lead_id = l.id left join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3
 where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) >= '2013-10-10' group by date(convert_tz(l.created_at, 'UTC', 'America/New_York'))

-- posts and revenue
select date(convert_tz(l.created_at, 'UTC', 'America/New_York')) day, count(distinct l.id) leads, count(distinct lm.id) partners, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
  from leads l join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3
 where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) = '2013-10-29' group by date(convert_tz(l.created_at, 'UTC', 'America/New_York'))

-- posts and revenue per advertiser
select a.name, count(distinct l.id) leads, count(distinct lm.id) partners, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
   from leads l join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3 join advertisers a on a.id = ad.advertiser_id
 where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) >= '2013-12-01' and date(convert_tz(l.created_at, 'UTC', 'America/New_York')) < '2014-01-01'
 group by a.id

-- by advertiser and order: posts and matches with phase, status, and revenue
-- using arrival date (the one date column that has an index)
-- (Coupon-hound host and post)
select adv.name advertiser, io.code, date(convert_tz(a.created_at, 'UTC', 'America/New_York')) day, ad.pony_phase, lm.status, count(distinct a.id) arrivals, count(distinct l.id) leads, count(distinct lm.id) matches, sum(ad.price) revenue from arrivals a left join leads l on l.arrival_id = a.id left join lead_matches lm on lm.lead_id = l.id left join advertiser_dispositions ad on ad.lead_match_id = lm.id left join advertisers adv on adv.id = ad.advertiser_id left join orders io on io.id = lm.order_id where date(convert_tz(a.created_at, 'UTC', 'America/New_York')) > '2014-01-20' group by adv.id, io.id, date(convert_tz(a.created_at, 'UTC', 'America/New_York')), ad.pony_phase, lm.status;

-- de-dup'ed dealers per lead/ping
select day, count(*) pings, sum(total) dealers, sum(un) n_dealers, avg(un) avg_unique_dealers
  from
   (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ucase(name)) un, count(*) total
      from advertiser_disposition_buyers db join buyers b on b.id = db.buyer_id join advertiser_dispositions ad on ad.id = db.advertiser_disposition_id join lead_matches lm on lm.id = ad.lead_match_id
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-01' group by lead_id
   )x
group by day

-- by day break down of partner/dealer performance metrics
-- day, pings, no_coverage, pinged_partners, dealers, pot_revenue, p_dpp, p_rpp, n_dealers, n_revenue, n_dpp, n_rpp, a_dealers, a_revenue, a_rpp, a_dpp
select pings.day, count(distinct pings.lead_id) pings, sum(pings.lead_coverage) no_coverage, round(100*sum(pings.lead_coverage)/count(distinct pings.lead_id)  ,2) nc_pct
, sum(coalesce(pings.total,0)) partners_pinged, sum(coalesce(p.dealers, 0)) p_dealers, sum(coalesce(p.revenue, 0)) p_revenue
, sum(coalesce(p.n_dealers, 0)) n_dealers, round(coalesce(sum((p.n_dealers/p.dealers) * p.revenue),0) , 2) n_revenue
, sum(coalesce(if(b.is_sold =1, b.dealer_posts, 0),0)) s_dealers, sum(coalesce(if(b.is_sold=1, b.leads, 0),0)) s_leads, sum(coalesce(if(b.is_sold=1, b.revenue, 0),0)) s_revenue
, sum(coalesce(if(b.is_sold=0, b.dealer_posts, 0),0)) us_dealers, sum(coalesce(if(b.is_sold=0, b.leads, 0),0)) us_leads
, round(sum(p.dealers)/count(distinct pings.lead_id),2) p_dpp, round((sum(p.revenue)/count(distinct pings.lead_id)),2) p_rpp
, round(sum(p.dealers)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) p_dpp_nc, round((sum(p.revenue)/(count(distinct pings.lead_id)-sum(pings.lead_coverage))),2) p_rpp_nc
, round(sum(p.n_dealers)/count(distinct pings.lead_id),2) n_dpp, round(sum((p.n_dealers/p.dealers) * p.revenue)/count(distinct pings.lead_id),2) n_rpp
, round(sum(p.n_dealers)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) n_dpp_nc, round(sum((p.n_dealers/p.dealers) * p.revenue)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) n_rpp_nc
, round(sum(b.leads)/count(distinct pings.lead_id),2) s_dpp, round(sum(b.revenue)/count(distinct pings.lead_id),2) s_rpp
, round(sum(b.leads)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) s_dpp_nc, round(sum(b.revenue)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) s_rpp_nc
  from
   -- partner pings
   (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ad.id) total, sum(if(ad.status = 3, 1, 0)) no_coverage, if(count(distinct ad.id) = sum(if(ad.status = 3, 1, 0)), 1, 0) lead_coverage
      from advertiser_dispositions ad join lead_matches lm on lm.id = ad.lead_match_id and ad.pony_phase = 1
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id
   )pings
    left join
   -- buyer ping responses
   (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ucase(name)) n_dealers, count(*) dealers, sum(db.price) revenue
      from advertiser_disposition_buyers db join buyers b on b.id = db.buyer_id join advertiser_dispositions ad on ad.id = db.advertiser_disposition_id join lead_matches lm on lm.id = ad.lead_match_id
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id
   )p on pings.lead_id = p.lead_id
    left join
    -- buyer posts
    (select lead_id, ad.status is_sold, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct l.id) leads, count(distinct lm.id) partners, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
       from leads l join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3
      where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id, ad.status
    )b on b.lead_id = pings.lead_id
group by pings.day;

-- different view
-- stg1, stg2, success leads/legs
select pings.day, count(distinct pings.lead_id) stg1_leads, sum(pings.lead_coverage) no_coverage, 100*round(sum(pings.lead_coverage)/count(distinct pings.lead_id)  ,2) nc_pct
 , sum(coalesce(p.n_dealers, 0)) n_dealers, round(sum(p.n_dealers)/count(distinct pings.lead_id),2) n_dpp, round(sum(p.n_dealers)/(count(distinct pings.lead_id)-sum(pings.lead_coverage)),2) n_dpp_nc
 , sum(coalesce(b.leads,0)) stg2_leads, sum(coalesce(if(b.is_sold=1, b.leads, 0),0)) stg2_leads_sold
 , sum(coalesce(b.dealer_posts,0)) stg2_legs, sum(coalesce(if(b.is_sold=1, b.dealer_posts, 0),0)) stg2_legs_sold
 , sum(coalesce(b.partner_posts,0)) stg2_partners, sum(coalesce(if(b.is_sold=1, b.partner_posts, 0),0)) stg2_partners_sold
 , round(sum(coalesce(b.dealer_posts,0))/sum(coalesce(b.leads,0)),2) legs_per_lead, round(sum(coalesce(if(b.is_sold=1, b.dealer_posts, 0),0))/if(sum(coalesce(if(b.is_sold=1, b.leads,0),0))=0, 1, sum(coalesce(if(b.is_sold=1, b.leads,0),0))),2) legs_per_lead_sold
  from
   -- partner pings
   (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ad.id) total, sum(if(ad.status = 3, 1, 0)) no_coverage, if(count(distinct ad.id) = sum(if(ad.status = 3, 1, 0)), 1, 0) lead_coverage
      from advertiser_dispositions ad join lead_matches lm on lm.id = ad.lead_match_id and ad.pony_phase = 1
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id
   )pings
    left join
   -- buyer ping responses
   (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ucase(name)) n_dealers, count(*) dealers, sum(db.price) revenue
      from advertiser_disposition_buyers db join buyers b on b.id = db.buyer_id join advertiser_dispositions ad on ad.id = db.advertiser_disposition_id join lead_matches lm on lm.id = ad.lead_match_id
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id
   )p on pings.lead_id = p.lead_id
    left join
    -- buyer posts
    (select lead_id, ad.status is_sold, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct l.id) leads, count(distinct lm.id) partners, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
       from leads l join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3
      where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_id, ad.status
    )b on b.lead_id = pings.lead_id
group by pings.day;



-- TODO: same by advertiser (past 90 days, past month, past week, yesterday, today)
select pings.day, pings.advertiser, count(distinct pings.lead_id) pings, sum(if(pings.total = pings.no_coverage, 1, 0)) no_coverage, sum(coalesce(pings.total,0)) partners_pinged, sum(coalesce(p.dealers,0)) p_dealers, sum(coalesce(p.revenue,0)) p_revenue
, sum(coalesce(p.n_dealers, 0)) n_dealers, round(coalesce(sum((p.n_dealers/p.dealers) * p.revenue),0) , 2) n_revenue
, sum(coalesce(b.dealer_posts,0)) s_dealers, sum(coalesce(b.leads,0)) s_leads, sum(coalesce(b.revenue,0)) s_revenue
, round(sum(p.dealers)/count(distinct pings.lead_id),2) p_dpp, round((sum(p.revenue)/count(distinct pings.lead_id)),2) p_rpp
, round(sum(p.n_dealers)/count(distinct pings.lead_id),2) n_dpp, round(sum((p.n_dealers/p.dealers) * p.revenue)/count(distinct pings.lead_id),2) n_rpp
, round(sum(b.leads)/count(distinct pings.lead_id),2) s_dpp, round(sum(b.revenue)/count(distinct pings.lead_id),2) s_rpp
  from
   -- partner pings
   (select lead_id, lead_match_id, a.name advertiser, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ad.id) total, sum(if(ad.status = 3, 1, 0)) no_coverage
      from advertiser_dispositions ad join lead_matches lm on lm.id = ad.lead_match_id and ad.pony_phase = 1 join orders o on lm.order_id = o.id join advertisers a on a.id = o.advertiser_id
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_match_id
   )pings
    left join
   -- buyer ping responses
   (select lead_id, lead_match_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ucase(name)) n_dealers, count(*) dealers, sum(db.price) revenue
      from advertiser_disposition_buyers db join buyers b on b.id = db.buyer_id join advertiser_dispositions ad on ad.id = db.advertiser_disposition_id join lead_matches lm on lm.id = ad.lead_match_id
     where  date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' group by lead_match_id
   )p on pings.lead_id = p.lead_id and pings.lead_match_id = p.lead_match_id
    left join
    -- buyer posts
    (select lead_id, lead_match_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct l.id) leads, count(distinct lm.id) partners, count(distinct ad.lead_match_id) partner_posts, count(distinct ad.id) dealer_posts, sum(ad.price) revenue
       from leads l join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on lm.id = ad.lead_match_id and ad.pony_phase = 3
      where date(convert_tz(l.created_at, 'UTC', 'America/New_York')) >= '2013-10-24' and ad.status = 1 group by lead_match_id
    )b on b.lead_id = pings.lead_id and b.lead_match_id = pings.lead_match_id
group by pings.day, pings.advertiser
--order by pings.advertiser, pings.day
order by pings.day, pings.advertiser

-- coverage by make
select pa.value, count(distinct l.id) pings, count(distinct x.lead_id) no_coverage, 100*round(count(distinct x.lead_id)/count(distinct l.id),2) no_coverage_pct
  from  leads l
   join ping_attributes pa on l.id = pa.lead_id and pa.attribute_id = 36 -- attribute id 36='make', 'zipcode'=35, 34='model'
   left join
    (select lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, count(distinct ad.id) total, sum(if(ad.status = 3, 1, 0)) no_coverage, if(count(distinct ad.id) = sum(if(ad.status = 3, 1, 0)), 1, 0) lead_coverage from advertiser_dispositions ad join lead_matches lm on lm.id = ad.lead_match_id and ad.pony_phase = 1 where date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) >= '2013-10-24'  group by lead_id, date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) having (count(distinct ad.id) = sum(if(ad.status = 3, 1, 0)))
    )x on x.lead_id = l.id
 group by pa.value
 order by count(distinct l.id) desc

-- LeadConduit / Coupon-Hound host and post
-- lead matches by day (for order id = 6)
select date(convert_tz(lm.created_at, 'UTC', 'America/New_York')) day, lm.order_id, lm.status m_state, ad.status dsp_state, count(*) from user_profiles up join leads l on l.user_profile_id = up.id join lead_matches lm on lm.lead_id = l.id join advertiser_dispositions ad on ad.lead_match_id = lm.id where lm.order_id = 6 group by date(convert_tz(lm.created_at, 'UTC', 'America/New_York')), lm.order_id, lm.status, ad.status;

-- posts by advertiser, io and day
select adv.name advertiser, io.code, date(convert_tz(a.created_at, 'UTC', 'America/New_York')) day, ad.pony_phase, lm.status, count(distinct a.id) arrivals, count(distinct l.id) leads, count(distinct lm.id) matches, sum(ad.price) revenue from arrivals a left join leads l on l.arrival_id = a.id left join lead_matches lm on lm.lead_id = l.id left join advertiser_dispositions ad on ad.lead_match_id = lm.id left join advertisers adv on adv.id = ad.advertiser_id left join orders io on io.id = lm.order_id where date(convert_tz(a.created_at, 'UTC', 'America/New_York')) > '2014-01-20' group by adv.id, io.id, date(convert_tz(a.created_at, 'UTC', 'America/New_York')), ad.pony_phase, lm.status order by adv.id, ad.pony_phase, date(convert_tz(a.created_at, 'UTC', 'America/New_York')), io.id

-- only successful posts, excluding Acqusition-Labs catch all
select adv.name advertiser, io.code, date(convert_tz(a.created_at, 'UTC', 'America/New_York')) day, ad.pony_phase, lm.status, count(distinct a.id) arrivals, count(distinct l.id) leads, count(distinct lm.id) matches, sum(ad.price) revenue from arrivals a left join leads l on l.arrival_id = a.id left join lead_matches lm on lm.lead_id = l.id left join advertiser_dispositions ad on ad.lead_match_id = lm.id left join advertisers adv on adv.id = ad.advertiser_id left join orders io on io.id = lm.order_id where date(convert_tz(a.created_at, 'UTC', 'America/New_York')) > '2014-01-20' and ad.pony_phase=3 and lm.status = 4 and adv.id != 8 group by adv.id, io.id, date(convert_tz(a.created_at, 'UTC', 'America/New_York')), ad.pony_phase, lm.status order by adv.id, ad.pony_phase, date(convert_tz(a.created_at, 'UTC', 'America/New_York')), io.id

-- directly mapped orders (for Coupon-Hound)
select pl.ext_list_id leadpath_list_id, o.code, a.name advertiser from orders o join advertisers a on a.id = o.advertiser_id join publisher_list_orders plo on plo.order_id = o.id join publisher_lists pl on plo.publisher_list_id = pl.id join publisher_list_members plm on plm.publisher_list_id = pl.id  where o.status = 1 and publisher_id = 11;

