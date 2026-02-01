USE customer_db;

SELECT * FROM customer;
SELECT * FROM customer_account;
SELECT * FROM account_address;
SELECT * FROM customer_priority_score;


# ---------------------------------------------------------------------------------------------


USE dca_case_db;

SELECT * FROM dca;
SELECT * FROM dca_geo;
SELECT * FROM dca_capacity;
SELECT * FROM dca_case;
SELECT * FROM case_allocation_decision;
SELECT * FROM dca_action_log;
SELECT * FROM case_priority_score;

#-------------------------------------------------------------------------------------------

USE payment_db;

SELECT * FROM invoice;
SELECT * FROM payment;

#---------------------------------------------------------------------------------------------

USE commission_analytics_db;

SELECT * FROM dca_commission;
SELECT * FROM dca_performance_metrics;

# -------------------------------------------------------------------------------------------

USE sop_sla_db;

SELECT * FROM sop_rule;
SELECT * FROM case_sla_rule;
SELECT * FROM action_sla_rule;
SELECT * FROM sop_action_sla_map;
