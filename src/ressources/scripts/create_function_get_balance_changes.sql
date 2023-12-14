CREATE OR REPLACE FUNCTION get_balance_changes(
    account_id VARCHAR(50),
    start_date TIMESTAMP,
    end_date TIMESTAMP
) RETURNS DECIMAL(18, 5) AS $$
DECLARE
total_balance DECIMAL(18, 5) := 0;
BEGIN
SELECT
        COALESCE(SUM(CASE WHEN type = 'CREDIT' THEN amount ELSE 0 END), 0) -
        COALESCE(SUM(CASE WHEN type = 'DEBIT' THEN amount ELSE 0 END), 0)
INTO total_balance
FROM Transaction
WHERE id_account = account_id AND date BETWEEN start_date AND end_date;

RETURN total_balance;
END;
$$ LANGUAGE plpgsql;

SELECT get_balance_changes('votre_id_de_compte', '2023-01-01'::TIMESTAMP, '2023-12-31'::TIMESTAMP);