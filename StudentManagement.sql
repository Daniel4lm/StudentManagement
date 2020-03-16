DROP DATABASE IF EXISTS StudentManagement;
CREATE DATABASE StudentManagement;

USE StudentManagement;

CREATE TABLE student(

	ID_student INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(20) CHECK(LENGTH(firstName) > 0),
	lastName VARCHAR(20) CHECK(LENGTH(lastName) > 0),
	dob DATE DEFAULT '2000.01.01',
    indexNumber VARCHAR(20) NOT NULL,
    mjesto_rodjenja INT,
    FOREIGN KEY(mjesto_rodjenja) REFERENCES mjesto(ID_mjesto)
);

INSERT INTO student(firstName,lastName,dob,indexNumber,mjesto_rodjenja) VALUES
('Amer','Hasanodjic','1981.05.18','I-788',14),
('Daniel','Molnar','1983.11.30','II-44',17),
('Omer','Karahodzic','1994.01.28','II-78',14),
('Sabina','Salihovic','1991.05.18','I-38',12),
('Zoran','Kulovic','1995.09.10','III-13',6),
('Zrinka','Milovic','1989.04.22','I-234',4),
('Anja','Brkic','1992.08.07','II-034',1),
('Amina','Slavinovic','1987.12.12','I-144',5),
('Adrijan','Zrinkovic','1997.04.12','III-64',17),
('Jelena','Babovic','1988.02.24','II-32',4)
;

CREATE TABLE mjesto (
	ID_mjesto INT PRIMARY KEY AUTO_INCREMENT,
    naziv VARCHAR(20) NOT NULL,
    drzava VARCHAR(20) NOT NULL
);

INSERT INTO mjesto(naziv, drzava) VALUES
('Banovici','BiH'),
('Bihac','BiH'),
('Banja Luka','BiH'),
('Bijeljina','BiH'),
('Brcko','BiH'),
('Doboj','BiH'),
('Gradacac','BiH'),
('Gracanica','BiH'),
('Jajce','BiH'),
('Kladanj','BiH'),
('Livno','BiH'),
('Lukavac','BiH'),
('Mostar','BiH'),
('Sarajevo','BiH'),
('Sanski Most','BiH'),
('Travnik','BiH'),
('Tuzla','BiH'),
('Zenica','BiH');
