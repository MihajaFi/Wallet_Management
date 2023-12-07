DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'TransactionType') THEN
        CREATE TYPE TransactionType AS ENUM ('DEBIT', 'CREDIT');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS Transaction (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255),
    amount DOUBLE PRECISION NOT NULL,
    date TIMESTAMP NOT NULL,
    type TransactionType NOT NULL,
    id_account VARCHAR(50) REFERENCES Account(id)
);

