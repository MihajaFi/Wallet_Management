DROP FUNCTION IF EXISTS get_category_amounts(VARCHAR(50), TIMESTAMP, TIMESTAMP);

-- Créez la nouvelle fonction avec le type de retour correct
CREATE OR REPLACE FUNCTION get_category_amounts(
    account_id VARCHAR(50),
    start_date TIMESTAMP,
    end_date TIMESTAMP
) RETURNS TABLE (
    category_id VARCHAR(50),
    category_label VARCHAR(255),
    total_amount DOUBLE PRECISION
) AS $$
BEGIN
RETURN QUERY
SELECT
    c.id AS category_id,
    c.name AS category_label,
    COALESCE(SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), 0) -
    COALESCE(SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END), 0) AS total_amount
FROM Category c
         LEFT JOIN Transaction t ON c.id = t.id_category AND t.id_account = account_id AND t.date BETWEEN start_date AND end_date
GROUP BY c.id, c.name;

RETURN;
END;
$$ LANGUAGE plpgsql;