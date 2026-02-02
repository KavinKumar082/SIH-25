USE sop_sla_db;
INSERT INTO SOP_Rule (sop_id, action_type, description) VALUES
(1,'Call','Initial reminder call to customer'),
(2,'Call','Follow-up call after missed promise'),
(3,'Email','Payment reminder email'),
(4,'Email','Legal notice email'),
(5,'Visit','Field visit to customer location'),
(6,'Visit','Final warning visit before legal escalation');

INSERT INTO Case_SLA_Rule
(case_sla_id, collection_stage, max_resolution_days, severity)
VALUES
(1,'Pre_DCA',30,'Low'),
(2,'Active',45,'Medium'),
(3,'Active',30,'High'),
(4,'Legal',90,'High');

INSERT INTO Action_SLA_Rule
(action_sla_id, action_type, max_days_allowed, mandatory_flag)
VALUES
(1,'Call',1,TRUE),
(2,'Call',2,FALSE),
(3,'Email',2,TRUE),
(4,'Email',5,FALSE),
(5,'Visit',7,TRUE),
(6,'Visit',10,FALSE);

INSERT INTO SOP_Action_SLA_Map
(map_id, sop_id, action_sla_id, collection_stage)
VALUES
-- Pre_DCA stage
(1,1,1,'Pre_DCA'),   -- Initial call within 1 day
(2,3,3,'Pre_DCA'),   -- Reminder email within 2 days

-- Active stage
(3,2,2,'Active'),   -- Follow-up call within 2 days
(4,3,3,'Active'),   -- Reminder email mandatory
(5,5,5,'Active'),   -- Field visit within 7 days

-- Legal stage
(6,4,4,'Legal'),    -- Legal notice email
(7,6,6,'Legal');    -- Final visit before legal action


select * from action_sla_rule;
select * from case_sla_rule;
select * from sop_action_sla_map;
select * from sop_rule;
