
-- Script pour créer la table TransactionType
CREATE TYPE TransactionType AS ENUM ('DEBIT', 'CREDIT');

-- Script pour créer la table Transaction
CREATE TABLE IF NOT EXISTS Transaction (
    transactionId SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    description VARCHAR(255),
    amount DOUBLE PRECISION NOT NULL,
    type TransactionType NOT NULL,
    accountId VARCHAR(50) REFERENCES Account(accountId)
);

