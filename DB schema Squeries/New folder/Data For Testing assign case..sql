USE dca_case_db;

INSERT INTO DCA (dca_id, dca_name, commission_rate, status) VALUES
(1,'Chennai North DCA',12.50,'Active'),
(2,'Chennai South DCA',10.00,'Active');


INSERT INTO DCA_Geo (geo_id, dca_id, latitude, longitude, coverage_region) VALUES
(1,1,13.0827,80.2707,'Chennai North'),
(2,2,13.0067,80.2570,'Chennai South');



INSERT INTO DCA_Capacity (capacity_id, dca_id, month, max_cases, allocated_cases) VALUES
(1,1,'2024-11-01',10,2),
(2,2,'2024-11-01',8,1);


# -----------------------------------------------------------------------------------------------

USE customer_db;

INSERT INTO Customer (customer_id, customer_name, customer_type, country, status) VALUES
(1,'A','Individual','India','Active'),
(2,'B','Individual','India','Active'),
(3,'C','Individual','India','Active'),
(4,'D','Individual','India','Active'),
(5,'E','Individual','India','Active'),
(6,'F','Individual','India','Active'),
(7,'G','Individual','India','Active'),
(8,'H','Individual','India','Active'),
(9,'I','Individual','India','Active'),
(10,'J','Individual','India','Active');




INSERT INTO Customer_Account
(account_id, customer_id, outstanding_amount, ageing_bucket, risk_segment, delinquency_score, collection_stage)
VALUES
(1,1,45000,'0-30','Low',120,'Pre_DCA'),
(2,2,78000,'31-60','Medium',310,'Active'),
(3,3,180000,'61-90','High',720,'Active'),
(4,4,22000,'0-30','Low',100,'Pre_DCA'),
(5,5,90000,'31-60','Medium',340,'Active'),
(6,6,160000,'61-90','High',700,'Active'),
(7,7,40000,'0-30','Low',140,'Pre_DCA'),
(8,8,60000,'31-60','Medium',280,'Active'),
(9,9,25000,'0-30','Low',110,'Pre_DCA'),
(10,10,170000,'61-90','High',690,'Active');



INSERT INTO Account_Address
(account_id, city, state, country, latitude, longitude)
VALUES
(1,'Chennai','TN','India',13.0900,80.2700),
(2,'Chennai','TN','India',13.0700,80.2500),
(3,'Chennai','TN','India',13.0100,80.2600),
(4,'Chennai','TN','India',13.0500,80.2300),
(5,'Chennai','TN','India',13.0000,80.2550),
(6,'Chennai','TN','India',13.0300,80.2400),
(7,'Chennai','TN','India',13.0850,80.2750),
(8,'Chennai','TN','India',13.0150,80.2650),
(9,'Chennai','TN','India',13.0600,80.2450),
(10,'Chennai','TN','India',13.0250,80.2500);

# -----------------------------------------------------------------------------------------------

USE sop_sla_db;

INSERT INTO case_sla_rule
(case_sla_id, collection_stage, severity, max_resolution_days)
VALUES
(1, 'Pre_DCA', 'Low', 30),
(2, 'Pre_DCA', 'Medium', 20),
(3, 'Pre_DCA', 'High', 10),

(4, 'Active', 'Low', 20),
(5, 'Active', 'Medium', 14),
(6, 'Active', 'High', 7);


# ----------------------------------------------------------------------------------------