CREATE SEQUENCE employee_seq;
CREATE TABLE employee (
    id  	integer PRIMARY KEY,
    name	text NOT NULL
);

INSERT INTO employee VALUES(nextval('employee_seq'), 'Иванов Петр Семенович');
INSERT INTO employee VALUES(nextval('employee_seq'), 'Петров Семен Иванович');
INSERT INTO employee VALUES(nextval('employee_seq'), 'Семенов Иван Петрович');

CREATE SEQUENCE job_seq;
CREATE TABLE job (
    id  	integer PRIMARY KEY,
    position	text NOT NULL
);

INSERT INTO job VALUES(nextval('job_seq'), 'инженер-программист I разряда');
INSERT INTO job VALUES(nextval('job_seq'), 'инженер-программист II разряда');
INSERT INTO job VALUES(nextval('job_seq'), 'инженер-программист III разряда');
INSERT INTO job VALUES(nextval('job_seq'), 'ведущий инженер-программист');

CREATE SEQUENCE absence_seq;
CREATE TABLE absence_history (
    id         	integer PRIMARY KEY,
    employee_id	int NOT NULL REFERENCES employee(id),
    job_id     	int NOT NULL REFERENCES job(id),
    date       	date NOT NULL,
    start_time 	time NOT NULL,
    end_time   	time NOT NULL,
    cause      	text NULL
);

INSERT INTO absence_history VALUES(nextval('absence_seq'), 1, 1, CAST('2017-10-08' AS date), CAST('09:00' AS time), CAST('09:40' AS time), 'попал в пробку');
INSERT INTO absence_history VALUES(nextval('absence_seq'), 2, 2, CAST('2017-10-08' AS date), CAST('17:30' AS time), CAST('18:00' AS time), 'отпросился забрать ребенка из садика');
INSERT INTO absence_history VALUES(nextval('absence_seq'), 3, 3, CAST('2017-10-09' AS date), CAST('17:00' AS time), CAST('18:00' AS time), 'отпросился по личным обстоятельствам');