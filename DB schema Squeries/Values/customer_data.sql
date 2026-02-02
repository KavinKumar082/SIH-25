USE customer_db;
INSERT INTO Customer 
(customer_id, customer_name, customer_type, phone_number, email, date_of_birth, industry, country, status)
VALUES
(1,'Arun Kumar','Individual','+91-900000001','arun.kumar1@gmail.com','1990-02-10','IT','India','Active'),
(2,'Balaji S','Individual','+91-900000002','balaji.s2@gmail.com','1988-06-21','Finance','India','Active'),
(3,'Chandru R','Individual','+91-900000003','chandru.r3@gmail.com','1992-11-12','Manufacturing','India','In_Collections'),
(4,'Deepa M','Individual','+91-900000004','deepa.m4@gmail.com','1995-01-05','Education','India','Active'),
(5,'Ezhil V','Individual','+91-900000005','ezhil.v5@gmail.com','1987-09-18','Healthcare','India','Active'),

(6,'Fathima Noor','Individual','+91-900000006','fathima6@gmail.com','1991-03-27','Retail','India','In_Collections'),
(7,'Ganesh P','Individual','+91-900000007','ganesh7@gmail.com','1989-07-14','Logistics','India','Active'),
(8,'Hari Krishnan','Individual','+91-900000008','hari8@gmail.com','1993-12-30','IT','India','Active'),
(9,'Indhu R','Individual','+91-900000009','indhu9@gmail.com','1994-05-19','Media','India','Active'),
(10,'Jagan T','Individual','+91-900000010','jagan10@gmail.com','1986-10-02','Manufacturing','India','In_Collections'),

(11,'Karthik S','Individual','+91-900000011','karthik11@gmail.com','1990-04-11','IT','India','Active'),
(12,'Lakshmi N','Individual','+91-900000012','lakshmi12@gmail.com','1992-08-24','Banking','India','Active'),
(13,'Manoj K','Individual','+91-900000013','manoj13@gmail.com','1985-01-17','Construction','India','In_Collections'),
(14,'Nandhini R','Individual','+91-900000014','nandhini14@gmail.com','1996-06-09','Education','India','Active'),
(15,'Om Prakash','Individual','+91-900000015','om15@gmail.com','1988-03-03','Retail','India','Active'),

(16,'Prakash V','Individual','+91-900000016','prakash16@gmail.com','1991-11-25','IT','India','Active'),
(17,'Revathi S','Individual','+91-900000017','revathi17@gmail.com','1993-02-14','Healthcare','India','Active'),
(18,'Sathish K','Individual','+91-900000018','sathish18@gmail.com','1987-07-07','Transport','India','In_Collections'),
(19,'Tamilselvan','Individual','+91-900000019','tamil19@gmail.com','1994-09-29','Manufacturing','India','Active'),
(20,'Uma Devi','Individual','+91-900000020','uma20@gmail.com','1995-12-01','Finance','India','Active'),

(21,'Vignesh R','Individual','+91-900000021','vignesh21@gmail.com','1990-05-05','IT','India','Active'),
(22,'Yamini S','Individual','+91-900000022','yamini22@gmail.com','1996-08-18','Media','India','Active'),
(23,'Zahir A','Individual','+91-900000023','zahir23@gmail.com','1989-02-26','Retail','India','In_Collections'),
(24,'Ashok L','Individual','+91-900000024','ashok24@gmail.com','1986-10-10','Logistics','India','Active'),
(25,'Bhavani P','Individual','+91-900000025','bhavani25@gmail.com','1992-04-22','Education','India','Active');



INSERT INTO Customer_Account
(account_id, customer_id, credit_limit, outstanding_amount, ageing_bucket, risk_segment, delinquency_score, collection_stage)
VALUES
(1,1,500000,45000,'0-30','Low',120,'Pre_DCA'),
(2,2,400000,78000,'31-60','Medium',310,'Active'),
(3,3,300000,180000,'61-90','High',720,'Active'),
(4,4,600000,22000,'0-30','Low',100,'Pre_DCA'),
(5,5,350000,90000,'31-60','Medium',340,'Active'),

(6,6,250000,160000,'90+','High',820,'Legal'),
(7,7,450000,40000,'0-30','Low',140,'Pre_DCA'),
(8,8,500000,60000,'31-60','Medium',280,'Active'),
(9,9,320000,25000,'0-30','Low',110,'Pre_DCA'),
(10,10,280000,170000,'61-90','High',690,'Active'),

(11,11,600000,50000,'0-30','Low',130,'Pre_DCA'),
(12,12,420000,85000,'31-60','Medium',350,'Active'),
(13,13,300000,190000,'90+','High',830,'Legal'),
(14,14,550000,30000,'0-30','Low',95,'Pre_DCA'),
(15,15,380000,70000,'31-60','Medium',300,'Active'),

(16,16,620000,65000,'31-60','Medium',320,'Active'),
(17,17,480000,42000,'0-30','Low',125,'Pre_DCA'),
(18,18,260000,175000,'90+','High',850,'Legal'),
(19,19,340000,55000,'31-60','Medium',290,'Active'),
(20,20,500000,27000,'0-30','Low',105,'Pre_DCA'),

(21,21,600000,48000,'0-30','Low',115,'Pre_DCA'),
(22,22,410000,72000,'31-60','Medium',330,'Active'),
(23,23,290000,165000,'61-90','High',710,'Active'),
(24,24,360000,58000,'31-60','Medium',305,'Active'),
(25,25,540000,35000,'0-30','Low',98,'Pre_DCA');


INSERT INTO Account_Address
(address_id, account_id, address_line, city, state, country, latitude, longitude)
VALUES
(1,1,'Velachery Main Road','Chennai','Tamil Nadu','India',12.9756,80.2200),
(2,2,'Anna Nagar West','Chennai','Tamil Nadu','India',13.0850,80.2101),
(3,3,'Tambaram East','Chennai','Tamil Nadu','India',12.9249,80.1275),
(4,4,'T Nagar','Chennai','Tamil Nadu','India',13.0418,80.2341),
(5,5,'Adyar','Chennai','Tamil Nadu','India',13.0067,80.2570),

(6,6,'Perungudi','Chennai','Tamil Nadu','India',12.9650,80.2451),
(7,7,'Ambattur','Chennai','Tamil Nadu','India',13.1143,80.1480),
(8,8,'OMR Sholinganallur','Chennai','Tamil Nadu','India',12.9010,80.2279),
(9,9,'Guindy','Chennai','Tamil Nadu','India',13.0109,80.2121),
(10,10,'Chromepet','Chennai','Tamil Nadu','India',12.9516,80.1395),

(11,11,'Pallavaram','Chennai','Tamil Nadu','India',12.9675,80.1491),
(12,12,'Nungambakkam','Chennai','Tamil Nadu','India',13.0569,80.2425),
(13,13,'Mogappair','Chennai','Tamil Nadu','India',13.0830,80.1745),
(14,14,'Kodambakkam','Chennai','Tamil Nadu','India',13.0525,80.2253),
(15,15,'Thiruvanmiyur','Chennai','Tamil Nadu','India',12.9830,80.2594),

(16,16,'Saidapet','Chennai','Tamil Nadu','India',13.0224,80.2230),
(17,17,'Poonamallee','Chennai','Tamil Nadu','India',13.0480,80.0945),
(18,18,'Avadi','Chennai','Tamil Nadu','India',13.1147,80.1010),
(19,19,'Koyambedu','Chennai','Tamil Nadu','India',13.0730,80.1970),
(20,20,'Porur','Chennai','Tamil Nadu','India',13.0314,80.1588),

(21,21,'Madipakkam','Chennai','Tamil Nadu','India',12.9647,80.1986),
(22,22,'Kelambakkam','Chennai','Tamil Nadu','India',12.7850,80.2200),
(23,23,'Pallikaranai','Chennai','Tamil Nadu','India',12.9363,80.2064),
(24,24,'Royapettah','Chennai','Tamil Nadu','India',13.0522,80.2642),
(25,25,'Triplicane','Chennai','Tamil Nadu','India',13.0586,80.2757);


INSERT INTO Customer_Priority_Score
(priority_id, customer_id, score, reason)
VALUES
(1,1,20,'{"reason":"Low outstanding"}'),
(2,2,45,'{"reason":"Delayed payments"}'),
(3,3,85,'{"reason":"High overdue"}'),
(4,4,15,'{"reason":"New customer"}'),
(5,5,50,'{"reason":"Medium risk"}'),

(6,6,95,'{"reason":"Chronic defaulter"}'),
(7,7,18,'{"reason":"Good repayment"}'),
(8,8,42,'{"reason":"Recent delay"}'),
(9,9,12,'{"reason":"Low risk"}'),
(10,10,80,'{"reason":"High overdue"}'),

(11,11,22,'{"reason":"Stable"}'),
(12,12,48,'{"reason":"Medium overdue"}'),
(13,13,98,'{"reason":"Legal stage"}'),
(14,14,10,'{"reason":"Excellent history"}'),
(15,15,40,'{"reason":"Monitor account"}'),

(16,16,44,'{"reason":"Medium risk"}'),
(17,17,18,'{"reason":"Low overdue"}'),
(18,18,99,'{"reason":"Legal escalation"}'),
(19,19,36,'{"reason":"Payment delays"}'),
(20,20,14,'{"reason":"Low risk"}'),

(21,21,16,'{"reason":"Good standing"}'),
(22,22,47,'{"reason":"Monitor closely"}'),
(23,23,78,'{"reason":"High overdue"}'),
(24,24,35,'{"reason":"Medium risk"}'),
(25,25,11,'{"reason":"Excellent"}');


select * from account_address;
select * from customer;
select * from customer_account;
select * from customer_priority_score;


SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

delete FROM account_address;
DELETE FROM customer;
DELETE FROM customer_account;
delete from customer_priority_score;


SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;



-- repeat for all tables
