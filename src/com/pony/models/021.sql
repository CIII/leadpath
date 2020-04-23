CREATE TABLE whitepages_filters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    pony_phase INT,
    parameter_name varchar(255) NOT NULL,
    accept_value varchar(255),
    reject_value varchar(255),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP(),
    last_updated timestamp DEFAULT CURRENT_TIMESTAMP()
    );
    
CREATE TRIGGER whitepages_filters_last_updated
BEFORE UPDATE
    ON whitepages_filters FOR EACH ROW
    set new.last_updated = current_timestamp();