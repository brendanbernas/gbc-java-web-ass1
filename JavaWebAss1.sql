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
	DepartmentID int(2) auto_increment,
	DepartmentName varchar(255),
	DepartmentLocation varchar(255),
	PRIMARY KEY (DepartmentID)
);

CREATE TABLE Group_T
(
	GroupID int(4) auto_increment,
	GroupName varchar(255) UNIQUE,
	GroupDepartmentID int(2),
	PRIMARY KEY (GroupID),
	FOREIGN KEY (GroupDepartmentID) REFERENCES Department_T(DepartmentID)
);

CREATE TABLE GroupMembers_T
(
	GroupID int(4),
	EmployeeID int(9),
	FOREIGN KEY (GroupID) REFERENCES Group_T(GroupID),
	FOREIGN KEY (EmployeeID) REFERENCES Employee_T(EmployeeID)		
);

#Dummy data
INSERT INTO Employee_T (EmployeeFirst, EmployeeLast, EmployeeEmail, DateHired, EmployeePosition)
  VALUES ('Brendan', 'Bernas', 'brendan.bernas@georgebrown.ca', '2017/10/17', 'Manager');

INSERT INTO Department_T(DepartmentName, DepartmentLocation) 
  VALUES ('Human Resources', 'Casa Loma');
