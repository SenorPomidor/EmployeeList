CREATE TABLE tasks (
    id int8 not null,
    description varchar(255),
    complete boolean not null default 'false',
    employee_id int8,
    primary key (id)
);

ALTER TABLE tasks ADD CONSTRAINT tasks_employee_fk FOREIGN KEY (employee_id) REFERENCES employees;