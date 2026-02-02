USE commission_analytics_db;
INSERT INTO DCA_Commission
(commission_id, dca_id, case_id, payment_id, recovered_amount, commission_amount)
VALUES
(1,3,10,8,100000,15000),   -- 15% of 100000
(2,3,19,15,40000,6000),    -- 15% of 40000
(3,1,23,19,100000,12500);  -- 12.5% of 100000


INSERT INTO DCA_Performance_Metrics
(metric_id, dca_id, recovery_rate, avg_resolution_time, period)
VALUES
(1,1,42.50,18,'2024-11'),
(2,2,38.00,22,'2024-11'),
(3,3,55.75,15,'2024-11');

select * from dca_commission;
select * from dca_performance_metrics;
