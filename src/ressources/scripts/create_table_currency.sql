-- Script pour créer la table Currency
CREATE TABLE IF NOT EXISTS Currency (
    id SERIAL PRIMARY KEY ,
    currencyCode VARCHAR(3) NOT NULL,
    currencyName VARCHAR(50) NOT NULL
);

 insert into Currency (currencyCode , currencyName)
 VALUES 
 ('AR' , 'ARIARY'),
 ('EUR' , 'EURO');