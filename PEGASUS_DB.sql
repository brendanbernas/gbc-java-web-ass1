DROP DATABASE IF EXISTS COMP3095;

CREATE DATABASE IF NOT EXISTS COMP3095;
USE COMP3095;
grant all on COMP3095.* to 'admin'@'localhost' identified by 'admin'; 

CREATE TABLE USERS 
( 
	id int(11) AUTO_INCREMENT PRIMARY KEY, 
	firstname varchar(255),
	lastname varchar(255),
	email varchar(255), 
	role varchar(20),
	username varchar(20) UNIQUE,
	password varchar(20)	
);

CREATE TABLE Logged_In_T
(
	id char(36),
    UserId int(11),
    PRIMARY KEY(id),
    FOREIGN KEY (UserId) REFERENCES USERS(id)
);

INSERT INTO USERS (firstname, lastname, email, role, username, password) VALUES
	('Admin', 'Adams', 'aadams@gbc.ca', NULL, 'admin', 'admin');

CREATE TABLE Employee_T
(
	EmployeeID int(9) AUTO_INCREMENT PRIMARY KEY,
	EmployeeFirst varchar(255),
	EmployeeLast varchar(255),
	EmployeeEmail varchar(255),
	DateHired DateTime,
	EmployeePosition varchar(255)
);

CREATE TABLE Department_T
(
	DepartmentID int(2) AUTO_INCREMENT PRIMARY KEY,
	DepartmentName varchar(255) UNIQUE,
	DepartmentLocation varchar(255)
);

CREATE TABLE Group_T
(
	GroupID int(4) AUTO_INCREMENT PRIMARY KEY,
	GroupName varchar(255) UNIQUE,
	GroupDepartmentID int(2),
	FOREIGN KEY (GroupDepartmentID) REFERENCES Department_T(DepartmentID)
);

CREATE TABLE GroupMembers_T
(
	GroupID int(9),
	EmployeeID int(9),
	FOREIGN KEY (GroupID) REFERENCES Group_T(GroupID),
	FOREIGN KEY (EmployeeID) REFERENCES Employee_T(EmployeeID)		
);

CREATE TABLE report_template
(
	id int(5) AUTO_INCREMENT PRIMARY KEY,
    department_id int(2),
    mapping_id int(9),
    template_type tinyint(1),
    template_name varchar(255),
    FOREIGN KEY (DepartmentID) REFERENCES Department_T(DepartmentID)
);

CREATE TABLE report_section_template
(
	id int(8) AUTO_INCREMENT PRIMARY KEY,
	report_template_id int(5),
    template_name varchar(255),
    section_number tinyint(1),
    FOREIGN KEY (report_template_id) REFERENCES report_template(id)
);

CREATE TABLE report_criteria_template
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_section_template_id int(8),
    template_name varchar(255),
    criteria_number tinyint(1),
    max_evaluation tinyint(1),
    FOREIGN KEY (report_section_template_id) REFERENCES report_section_template(id)
);

CREATE TABLE report
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_template_id int(5),
    report_name varchar(255),
    report_date datetime,
    FOREIGN KEY (report_template_id) REFERENCES report_template(id)
);

CREATE TABLE evaluation
(
	id int(11) AUTO_INCREMENT PRIMARY KEY,
    report_id int(11),
    report_criteria_template_id int(11),
    grade int(3),
    evaluation_comment varchar(255),
    FOREIGN KEY (report_id) REFERENCES report(id),
    FOREIGN KEY (report_criteria_template_id) REFERENCES report_criteria_template(id)
);

#Dummy data
INSERT INTO Employee_T (EmployeeFirst, EmployeeLast, EmployeeEmail, DateHired, EmployeePosition)
  VALUES ('Brendan', 'Bernas', 'brendan.bernas@georgebrown.ca', '2017/10/17', 'Manager');

INSERT INTO Department_T(DepartmentName, DepartmentLocation) 
  VALUES ('Human Resources', 'Casa Loma');