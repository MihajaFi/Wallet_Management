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
    updatedDate TIMESTAMP NOT NULL,
    id_currency INT REFERENCES Currency(id),
    type AccountType NOT NULL,
    CONSTRAINT positiveBalance CHECK (balance >= 0)
);

INSERT INTO Account (id ,name ,balance , updatedDate , id_currency , type )
VALUES
('QSDF12345' ,'Account current', 45000.0 ,CURRENT_TIMESTAMP, 1, 'BANK'),
('12345dsdf','Epargne Account',  60000.0 ,CURRENT_TIMESTAMP, 2,'MM');
