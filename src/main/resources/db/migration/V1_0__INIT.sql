create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE employees (
    id int primary key,
    name varchar(15),
    surname varchar(25),
    department varchar(20),
    salary int
) ;

INSERT INTO employees (id, name, surname, department, salary)
VALUES (nextval('hibernate_sequence'), 'Oleg', 'Minin', 'Sales', 700),
       (nextval('hibernate_sequence'), 'Nina', 'Sidorova', 'HR', 850),
       (nextval('hibernate_sequence'), 'Ivan', 'Ivanov', 'IT', 800),
       (nextval('hibernate_sequence'), 'Mark', 'Gorbunov', 'Manager', 1100),
       (nextval('hibernate_sequence'), 'Alexandr', 'Petrov', 'IT', 900);

