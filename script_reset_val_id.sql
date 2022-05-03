SELECT MAX(id) FROM employees e;
SELECT nextval('employee_id_seq');
BEGIN;
-- protect against concurrent inserts while you update the counter
LOCK TABLE employees IN EXCLUSIVE MODE;
-- Update the sequence
SELECT setval('employee_id_seq', COALESCE((SELECT MAX(id)+1 FROM employees), 1), false);
COMMIT;