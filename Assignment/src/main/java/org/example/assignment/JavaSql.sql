CREATE DATABASE BarGraph;
USE  BarGraph;


CREATE TABLE province_literacy (
    id INT AUTO_INCREMENT PRIMARY KEY,
    province VARCHAR(100),
    literacy_rate LONG
);
INSERT INTO province_literacy (province ,literacy_rate) VALUES
("Quebec",240),
("Manitoba",270 ),
("British Columbia",265),
("Saskatchewan", 260),
("New Brunswick",250),
("Alberta", 275),
("New Foundland Labrador", 246),
("Ontario",255),
("Nova Scotia",267),
("Prince Edward Island",278),
("OCED",250);
SHOW TABLES;


