DROP DATABASE IF EXISTS COMP3095;

CREATE DATABASE IF NOT EXISTS COMP3095;
USE COMP3095;
grant all on COMP3095.* to 'admin'@'localhost' identified by 'admin'; 

CREATE TABLE admin
( 
	id int(11) AUTO_INCREMENT PRIMARY KEY, 
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255), 
	role varchar(20),
	username varchar(20) UNIQUE NOT NULL,
	password varchar(20) NOT NULL	
);

CREATE TABLE logged_in
(
	id char(36) PRIMARY KEY,
    admin_id int(11) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES admin(id)
);

INSERT INTO admin (first_name, last_name, email, role, username, password) VALUES
	('Admin', 'Adams', 'aadams@gbc.ca', NULL, 'admin', 'admin');

CREATE TABLE department
(
	id int(2) AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) UNIQUE NOT NULL,
	location varchar(255) NOT NULL
);

CREATE TABLE employee
(
	id int(9) AUTO_INCREMENT PRIMARY KEY,
    department_id int(2) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
	date_hired DateTime NOT NULL,
	position varchar(255) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE groups
(
	id int(4) AUTO_INCREMENT PRIMARY KEY,
	department_id int(2) NOT NULL,
	name varchar(255) UNIQUE NOT NULL,
	FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE group_member
(
	group_id int(9) NOT NULL,
	employee_id int(9) NOT NULL,
	FOREIGN KEY (group_id) REFERENCES groups(id),
	FOREIGN KEY (employee_id) REFERENCES employee(id)		
);

CREATE TABLE report_template
(
	id int(5) AUTO_INCREMENT PRIMARY KEY,
    department_id int(2) NOT NULL,
    template_name varchar(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE report_section_template
(
	id int(8) AUTO_INCREMENT PRIMARY KEY,
	report_template_id int(5) NOT NULL,
    template_name varchar(255) NOT NULL,
    section_number tinyint(1) NOT NULL,
    FOREIGN KEY (report_template_id) REFERENCES report_template(id)
);

CREATE TABLE report_criteria_template
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_section_template_id int(8) NOT NULL,
    template_name varchar(255) NOT NULL,
    criteria_number tinyint(1) NOT NULL,
    max_evaluation tinyint(1) NOT NULL,
    FOREIGN KEY (report_section_template_id) REFERENCES report_section_template(id)
);

CREATE TABLE report
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_template_id int(5) NOT NULL,
	mapping_id int(9) NOT NULL,
    report_type tinyint(1) NOT NULL,
    report_name varchar(255) NOT NULL,
    report_date date NOT NULL,
    FOREIGN KEY (report_template_id) REFERENCES report_template(id)
);

CREATE TABLE criteria_evaluation
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_id int(11) NOT NULL,
    report_criteria_template_id int(11) NOT NULL,
    grade int(3) NOT NULL,
    FOREIGN KEY (report_id) REFERENCES report(id),
    FOREIGN KEY (report_criteria_template_id) REFERENCES report_criteria_template(id)
);

CREATE TABLE section_evaluation
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_id int(11) NOT NULL,
    report_section_template_id int(8) NOT NULL,
    comment VARCHAR(255),
    FOREIGN KEY (report_id) REFERENCES report(id),
    FOREIGN KEY (report_section_template_id) REFERENCES report_section_template(id)
);

#Dummy data
INSERT INTO department(name, location) 
  VALUES ('Human Resources', 'Casa Loma');
  
INSERT INTO department(name, location) 
  VALUES ('Accounting', 'St. James');

INSERT INTO employee(first_name, last_name, email, date_hired, position, department_id)
  VALUES ('Brendan', 'Bernas', 'brendan.bernas@georgebrown.ca', '2017/10/17', 'Manager', 1);
  
INSERT INTO groups(department_id, name)
 VALUES (1, 'Human Resourcers');
 
INSERT INTO groups(department_id, name)
 VALUES (1, 'Resourceful Humans');