ALTER TABLE `pony_leads`.`arrivals`
ADD COLUMN `robot_id` VARCHAR(225) NULL DEFAULT 'null' AFTER `validation_code`;
