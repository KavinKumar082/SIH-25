SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

USE dca_case_db;
DELETE FROM case_priority_score;
DELETE FROM dca_action_log;
DELETE FROM case_allocation_decision;
DELETE FROM dca_case;
DELETE FROM dca_capacity;
DELETE FROM dca_geo;
DELETE FROM dca;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;







SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

USE sop_sla_db;
DELETE FROM sop_action_sla_map;
DELETE FROM action_sla_rule;
DELETE FROM case_sla_rule;
DELETE FROM sop_rule;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;








SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

USE customer_db;
DELETE FROM account_address;
DELETE FROM customer_account;
DELETE FROM customer_priority_score;
DELETE FROM customer;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;



SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

USE payment_db
DELETE FROM payment;
DELETE FROM invoice;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;





SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

USE commission_analytics_db;
DELETE FROM dca_commission;
DELETE FROM dca_performance_metrics;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;


