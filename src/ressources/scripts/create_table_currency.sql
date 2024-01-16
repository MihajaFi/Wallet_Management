-- Script pour créer la table Currency
CREATE TABLE IF NOT EXISTS Currency (
    id SERIAL  PRIMARY KEY,    
    name VARCHAR(50) NOT NULL ,
    code VARCHAR(3) NOT NULL
);

 insert into Currency (code ,name)
 VALUES 
 ('MGA' , 'ARIARY'),
 ('EUR' , 'EURO');