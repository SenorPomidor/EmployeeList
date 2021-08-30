ALTER TABLE employees ADD login varchar(255) NOT NULL default 'login';
ALTER TABLE employees ADD password varchar(255) NOT NULL default '$2a$08$wQuxbz5vH0cDmHplydGR8O9vE51afmdYRvpJCm0WE6Onxx/d1aEdm';

UPDATE employees SET login = 'login' WHERE id = 1;
UPDATE employees SET login = 'login2' WHERE id = 2;
UPDATE employees SET login = 'login3' WHERE id = 3;
UPDATE employees SET login = 'login4' WHERE id = 4;
UPDATE employees SET login = 'login5' WHERE id = 5;

-- login = 'login' ; password = 'a'
UPDATE employees SET password = '$2a$08$YXtI0BjhmFGoiLog7P7y/e/rpe1cWMDKKdB5onu9OYzIZNnDeV1Vq' WHERE id = 1;
-- login = 'login2' ; password = 'b'
UPDATE employees SET password = '$2a$08$.flwewQIYzIumjO5GXNbYOrOV0c0jHvKST8Ai8AMz2KGkdf4GFPoW' WHERE id = 2;
-- login = 'login3' ; password = 'c'
UPDATE employees SET password = '$2a$08$XiD4tT5J6j7oCI.y55mciuJks2PZHIuiXdhmf9Xt9NCKlBju0gZDG' WHERE id = 3;
-- login = 'login4' ; password = 'd'
UPDATE employees SET password = '$2a$08$UQyqalbCLYU8.gFagoe7JuEaKdK02MLv5HqXxIMovvb60/XllqZ8O' WHERE id = 4;
-- login = 'login5' ; password = 'e'
UPDATE employees SET password = '$2a$08$s9/F4oLP4SkdPyaVnSKci.D79cAuh6NCqcVtK2LV2AUmFSseEWW1G' WHERE id = 5;


create table user_role (
    user_id int8 not null,
    role varchar(255)
);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references employees;

-- password = 'dinar0'
INSERT INTO employees (id, name, surname, department, salary, login, password)
VALUES (nextval('hibernate_sequence'),
        'Dinar', 'Zaripov', 'Director', 100000, 'dinar0',
        '$2a$08$v42.AJHd9w9wM3Pve90bDO8ln4YgpA5iY/qJDPfTjD5S1EP785ybi');

INSERT INTO user_role (user_id, role)
VALUES (1, 'EMPLOYEE'),
       (2, 'EMPLOYEE'),
       (3, 'EMPLOYEE'),
       (4, 'EMPLOYEE'),
       (5, 'EMPLOYEE'),
       (6, 'DIRECTOR');
