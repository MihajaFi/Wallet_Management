DROP FUNCTION IF EXISTS get_category_sum(VARCHAR(50), TIMESTAMP, TIMESTAMP);

CREATE OR REPLACE FUNCTION get_category_sum(
    account_id VARCHAR,
    start_date TIMESTAMP,
    end_date TIMESTAMP
) RETURNS TABLE (
    restaurant DOUBLE PRECISION,
    salary DOUBLE PRECISION
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN c.name = 'Restaurant' THEN t.amount END), 0) AS restaurant,
        COALESCE(SUM(CASE WHEN c.name = 'Salary' THEN t.amount END), 0) AS salary
    FROM
        Account a
        LEFT JOIN Transaction t ON a.id = t.id_account AND t.date BETWEEN start_date AND end_date
        LEFT JOIN Category c ON t.id_category = c.id
    WHERE
        a.id = account_id;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM get_category_sum('Koto', '2023-12-14', '2023-12-15');

