CREATE TABLE IF NOT EXISTS BalanceHistory (
    id SERIAL PRIMARY KEY,
    id_account VARCHAR(50) REFERENCES Account(id),
    transaction_id INT REFERENCES Transaction(id),
    balance DECIMAL(18, 5) NOT NULL,
    date TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION update_balance_history()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO BalanceHistory (id_account, transaction_id, balance, date)
    VALUES (NEW.id_account, NEW.id,
            (SELECT COALESCE(MAX(balance), 0) FROM BalanceHistory WHERE id_account = NEW.id_account) +
            CASE WHEN NEW.type = 'CREDIT' THEN NEW.amount ELSE -NEW.amount END,
            NEW.date);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER balance_history_trigger
AFTER INSERT ON Transaction
FOR EACH ROW
EXECUTE FUNCTION update_balance_history();


SELECT
    date,
    balance
FROM
    BalanceHistory
WHERE
    id_account = 'Fifaliana'
    AND date BETWEEN '2023-12-08 06:00:00' AND '2023-12-08 09:00:00'
ORDER BY
    date;
