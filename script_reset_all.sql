delete from public.ord_prd;
delete from public.orders;
delete from public.employees;
delete from public.product;

alter sequence employees_id_seq restart with 1;
update employees set id=nextval('employees_id_seq');

alter sequence orders_id_seq restart with 1;
update employees set id=nextval('orders_id_seq');

alter sequence employees_id_seq restart with 1;
update employees set id=nextval('employees_id_seq');