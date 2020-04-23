ALTER TABLE `pony_leads`.`mandrill_emails` 
DROP FOREIGN KEY `user_profile_email_fk`;
ALTER TABLE `pony_leads`.`mandrill_emails` 
CHANGE COLUMN `user_profile_id` `user_profile_id` INT(11) NULL ,
CHANGE COLUMN `mandrill_id` `mandrill_id` VARCHAR(45) NULL ,
ADD COLUMN `email` VARCHAR(45) NULL AFTER `mandrill_id`,
ADD INDEX `user_profile_fk_idx` (`user_profile_id` ASC),
DROP INDEX `user_profile_email_fk_idx` ;
ALTER TABLE `pony_leads`.`mandrill_emails` 
ADD CONSTRAINT `user_profile_fk`
  FOREIGN KEY (`user_profile_id`)
  REFERENCES `pony_leads`.`user_profiles` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
