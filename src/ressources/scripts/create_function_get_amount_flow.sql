CREATE OR REPLACE FUNCTION get_amount_flow(
    account_id VARCHAR,
    start_date TIMESTAMP,
    end_date TIMESTAMP
) RETURNS TABLE (
    total_credit DOUBLE PRECISION,
    total_debit DOUBLE PRECISION
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount END), 0) AS total_credit,
        COALESCE(SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount END), 0) AS total_debit
    FROM
        Account a
        LEFT JOIN Transaction t ON a.id = t.id_account AND t.date BETWEEN start_date AND end_date
    WHERE
        a.id = account_id;
END;
$$ LANGUAGE plpgsql;

SELECT get_amount_flow('votre_id_de_compte', '2023-01-01'::TIMESTAMP, '2023-12-31'::TIMESTAMP);