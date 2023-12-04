
-- Script pour créer la table Account
CREATE TABLE IF NOT EXISTS Account (
    accountId VARCHAR(50) PRIMARY KEY,
    balance DOUBLE PRECISION NOT NULL,
    accountName VARCHAR(50) NOT NULL ,
    currencyCode VARCHAR(3) REFERENCES Currency(currencyCode),
    CONSTRAINT positiveBalance CHECK (balance >= 0)
);

INSERT INTO Account (accountId ,balance ,accountName , currencyCode)
VALUES
('QSDF12345' , 45000.0 ,'Depos', 'EUR'),
('12345dsdf',  60000.0 ,'nice', 'AR');
