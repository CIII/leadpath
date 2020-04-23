CREATE TABLE lead_reporting (
    advertiser_name varchar(100) NOT NULL,
    lead_id bigint NOT NULL,
    lead_match_id bigint NOT NULL,
    order_id bigint NOT NULL,
    lynx_session_id varchar(72) NULL,
    attempt int NOT NULL DEFAULT 0,
    success int NOT NULL DEFAULT 0,
    rejected int NOT NULL DEFAULT 0,
    returned int NOT NULL DEFAULT 0,
    price decimal(8,2) NOT NULL DEFAULT 0.00,
    conf_part int NOT NULL DEFAULT 0,
    zip_code varchar(500) NULL DEFAULT '',
    state varchar(500) NULL DEFAULT '',
    first_name varchar(500) NULL DEFAULT '',
    last_name varchar(500) NULL DEFAULT '',
    email varchar(255) NULL DEFAULT '',
    city varchar(500) NULL DEFAULT '',
    electric_bill varchar(500) NULL DEFAULT '',
    utm_source varchar(500) NULL DEFAULT '',
    phone_score varchar(500) NULL DEFAULT '',
    address_score varchar(500) NULL DEFAULT '',
    email_score varchar(500) NULL DEFAULT '',
    credit_range varchar(500) NULL DEFAULT '',
    phone_is_valid varchar(500) NULL DEFAULT '',
    phone_is_commercial varchar(500) NULL DEFAULT '',
    times_sold int NOT NULL DEFAULT 0,
    conu int NOT NULL DEFAULT 0,
    sold_boston_solar int NOT NULL DEFAULT 0,
    sold_nece int NOT NULL DEFAULT 0,
    sold_ma INT NOT NULL DEFAULT 0,
    last_updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (lead_match_id));
    
CREATE TABLE lead_reporting_status (
    last_run timestamp DEFAULT CURRENT_TIMESTAMP());
    
INSERT INTO lead_reporting_status VALUES ('2016-06-01');