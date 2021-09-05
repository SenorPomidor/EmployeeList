alter table employees add column director_id int8;
alter table employees add constraint employees_director_fk foreign key (director_id) references employees;

UPDATE employees SET director_id = '6' WHERE id = 1;
UPDATE employees SET director_id = '6' WHERE id = 2;
UPDATE employees SET director_id = '6' WHERE id = 3;
UPDATE employees SET director_id = '6' WHERE id = 4;
UPDATE employees SET director_id = '6' WHERE id = 5;