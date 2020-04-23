CREATE TABLE `pony_leads`.`async_tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `label` VARCHAR(500) NULL,
  `runs` INT NULL,
  `repeats` VARCHAR(45) NULL,
  `retry_on_fail` BOOLEAN NULL,
  `status` VARCHAR(45) NULL,
  `next_run` DATETIME NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `pony_leads`.`async_task_attributes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `async_task_id` INT NULL,
  `attribute` VARCHAR(45) NULL,
  `value` VARCHAR(500) NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `task_fk_idx` (`async_task_id` ASC),
  CONSTRAINT `task_fk`
    FOREIGN KEY (`async_task_id`)
    REFERENCES `pony_leads`.`async_tasks` (`id`));

CREATE TABLE `pony_leads`.`mandrill_emails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_profile_id` INT NOT NULL,
  `mandrill_id` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NULL,
  `subject` VARCHAR(500) NULL,
  `opens` INT NULL,
  `clicks` INT NULL,
  `sender` VARCHAR(100) NULL,
  `template` VARCHAR(100) NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `mandrill_id_UNIQUE` (`mandrill_id` ASC),
  INDEX `user_profile_email_fk_idx` (`user_profile_id` ASC),
  CONSTRAINT `user_profile_email_fk`
    FOREIGN KEY (`user_profile_id`)
    REFERENCES `pony_leads`.`user_profiles` (`id`));

CREATE TABLE `pony_leads`.`mandrill_templates` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `advertiser_id` INT NULL,
  `buyer_id` INT NULL,
  `template_name` VARCHAR(45) NULL,
  `delay_minutes` INT NULL,
  `order` INT NULL,
  `domain` VARCHAR(45) NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `advertiser_mandrill_template_fk_idx` (`advertiser_id` ASC),
  INDEX `buyer_mandrill_template_fk_idx` (`buyer_id` ASC),
  CONSTRAINT `advertiser_mandrill_template_fk`
    FOREIGN KEY (`advertiser_id`)
    REFERENCES `pony_leads`.`advertisers` (`id`),
  CONSTRAINT `buyer_mandrill_template_fk`
    FOREIGN KEY (`buyer_id`)
    REFERENCES `pony_leads`.`buyers` (`id`));

INSERT INTO async_tasks (`type`, `label`, `runs`, `repeats`, `retry_on_fail`, `status`, `next_run`, `created_at`, `updated_at`)
  VALUES ('com.tapquality.email.MandrillRefreshAsyncTask', 'Process email data - refresh Mandrill data in the mandrill_emails table', 0, true, true, 'Pending', now(), now(), now());
 
# Uncomment to include test templates that will be sent if a test is posted. 
#INSERT INTO mandrill_templates (`advertiser_id`, `template_name`, `delay_minutes`, `order`, `created_at`, `updated_at`) 
#  VALUES (22, 'test_template', 2, 0, now(), now());

#INSERT INTO mandrill_templates (`advertiser_id`, `template_name`, `delay_minutes`, `order`, `created_at`, `updated_at`) 
#  VALUES (23, 'test_template', 5, 0, now(), now());

#INSERT INTO mandrill_templates (`advertiser_id`, `template_name`, `delay_minutes`, `order`, `created_at`, `updated_at`) 
#  VALUES (24, 'test_template', 10, 0, now(), now());
  
# Boston solar accepted template for easiersolar (Make sure 25 is BostonSolar advertiser id)
INSERT INTO mandrill_templates (`advertiser_id`, `template_name`, `delay_minutes`, `order`, `domain`, `created_at`, `updated_at`)
  VALUES (25, 'es_accepted_bos_001', 40, 0, 'easiersolar', now(), now());
  
# Boston solar accepted template for homesolarPro (Make sure 25 is BostonSolar advertiser id)
INSERT INTO mandrill_templates (`advertiser_id`, `template_name`, `delay_minutes`, `order`, `domain`, `created_at`, `updated_at`)
  VALUES (25, 'hsp_accepted_bos_002', 40, 0, 'homesolarpro', now(), now());
  
# SolarCity accepted template for easiersolar (Make sure buyer id is SolarCity for MediaAlpha)
INSERT INTO mandrill_templates (`buyer_id`, `template_name`, `delay_minutes`, `order`, `domain`, `created_at`, `updated_at`)
  VALUES (7, 'es_accepted_scty_003', 5, 0, 'easiersolar', now(), now());
  
# SolarCity accepted template for homesolarpro (Make sure buyer id is SolarCity for MediaAlpha)
INSERT INTO mandrill_templates (`buyer_id`, `template_name`, `delay_minutes`, `order`, `domain`, `created_at`, `updated_at`)
  VALUES (7, 'hsp_accepted_scty_004', 5, 0, 'homesolarpro', now(), now());