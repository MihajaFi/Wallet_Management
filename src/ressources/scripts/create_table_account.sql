DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'AccountType') THEN
        CREATE TYPE AccountType AS ENUM ('BANK', 'MM', 'CASH', 'SPECIES');
    END IF;
END $$;

-- Script pour créer la table Account
CREATE TABLE IF NOT EXISTS Account (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL ,
    balance DECIMAL(18 , 5) NOT NULL,
    id_currency INT REFERENCES Currency(id),
    type AccountType NOT NULL,
    CONSTRAINT positiveBalance CHECK (balance >= 0),
    id_transaction INT REFERENCES Transaction(id)
);

INSERT INTO Account (accountId ,balance ,accountName , currencyCode)
VALUES
('QSDF12345' , 45000.0 ,'Depos', 'EUR'),
('12345dsdf',  60000.0 ,'nice', 'AR');
