USE dca_case_db;
INSERT INTO DCA (dca_id, dca_name, commission_rate, status) VALUES
(1,'Chennai Recoveries Pvt Ltd',12.50,'Active'),
(2,'South India Collections',10.00,'Active'),
(3,'Urban Field Services',15.00,'Active');


INSERT INTO DCA_Geo
(geo_id, dca_id, latitude, longitude, coverage_region)
VALUES
(1,1,13.082680,80.270718,'Chennai North'),
(2,1,13.067439,80.237617,'Chennai Central'),

(3,2,13.035822,80.209213,'Chennai South'),
(4,2,13.050000,80.250000,'Tambaram'),

(5,3,13.090000,80.280000,'Avadi'),
(6,3,13.060000,80.260000,'Velachery');


INSERT INTO DCA_Capacity
(capacity_id, dca_id, month, max_cases, allocated_cases)
VALUES
(1,1,'2024-11-01',15,8),
(2,2,'2024-11-01',10,6),
(3,3,'2024-11-01',12,7);


INSERT INTO DCA_Case
(case_id, account_id, dca_id, allocated_date, case_status, case_sla_id, case_sla_due_date)
VALUES
(1,1,1,'2024-11-01','Open',2,'2024-12-16'),
(2,2,1,'2024-11-01','Open',3,'2024-12-01'),
(3,3,2,'2024-11-02','Open',3,'2024-12-02'),
(4,4,2,'2024-11-02','Closed',1,'2024-12-02'),
(5,5,3,'2024-11-03','Open',2,'2024-12-18'),

(6,6,1,'2024-11-03','Open',3,'2024-12-03'),
(7,7,1,'2024-11-04','Closed',1,'2024-12-04'),
(8,8,2,'2024-11-04','Open',2,'2024-12-19'),
(9,9,3,'2024-11-05','Closed',1,'2024-12-05'),
(10,10,3,'2024-11-05','Open',3,'2024-12-05'),

(11,11,1,'2024-11-06','Closed',1,'2024-12-06'),
(12,12,2,'2024-11-06','Open',2,'2024-12-21'),
(13,13,3,'2024-11-07','Open',4,'2025-02-05'),
(14,14,1,'2024-11-07','Closed',1,'2024-12-07'),
(15,15,2,'2024-11-08','Open',2,'2024-12-23'),

(16,16,3,'2024-11-08','Open',2,'2024-12-23'),
(17,17,1,'2024-11-09','Closed',1,'2024-12-09'),
(18,18,2,'2024-11-09','Open',4,'2025-02-07'),
(19,19,3,'2024-11-10','Open',2,'2024-12-25'),
(20,20,1,'2024-11-10','Closed',1,'2024-12-10'),

(21,21,2,'2024-11-11','Closed',1,'2024-12-11'),
(22,22,3,'2024-11-11','Open',2,'2024-12-26'),
(23,23,1,'2024-11-12','Open',3,'2024-12-12'),
(24,24,2,'2024-11-12','Open',2,'2024-12-27'),
(25,25,3,'2024-11-13','Closed',1,'2024-12-13');


INSERT INTO Case_Allocation_Decision
(decision_id, case_id, selected_dca_id, priority_score, reason)
VALUES
(1,1,1,85,'{"geo_match":true,"capacity_ok":true,"risk":"High"}'),
(2,2,1,78,'{"geo_match":true,"capacity_ok":true,"risk":"Medium"}'),
(3,3,2,90,'{"geo_match":true,"capacity_ok":true,"risk":"High"}'),
(4,4,2,40,'{"low_amount":true}'),
(5,5,3,70,'{"visit_required":true}'),

(6,6,1,88,'{"overdue_days":90}'),
(7,7,1,30,'{"paid":true}'),
(8,8,2,65,'{"medium_risk":true}'),
(9,9,3,35,'{"closed_fast":true}'),
(10,10,3,92,'{"large_amount":true}');

INSERT INTO DCA_Action_Log
(action_id, case_id, sop_id, action_date, outcome, remarks, action_sla_due_date, sla_breach_flag)
VALUES
(1,1,1,'2024-11-02','Promise to Pay','Customer promised payment','2024-11-03',FALSE),
(2,2,2,'2024-11-04','No Response','Follow-up needed','2024-11-05',TRUE),
(3,3,5,'2024-11-06','Visited','Customer unavailable','2024-11-13',FALSE),
(4,5,1,'2024-11-05','Paid','Partial payment done','2024-11-06',FALSE),
(5,6,6,'2024-11-10','Escalated','Final warning given','2024-11-20',FALSE);


INSERT INTO Case_Priority_Score
(priority_id, case_id, score, reason)
VALUES
(1,1,90,'{"sla_days_left":10,"risk":"High"}'),
(2,2,75,'{"sla_days_left":5,"risk":"Medium"}'),
(3,3,95,'{"sla_breach_risk":true}'),
(4,5,70,'{"visit_pending":true}'),
(5,6,98,'{"legal_risk":true}');


select * from case_allocation_decision;
select * from dca;
select * from dca_action_log;
select * from dca_capacity;
select * from dca_case;
select * from dca_geo;


