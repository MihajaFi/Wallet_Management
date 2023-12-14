CREATE TABLE IF NOT EXISTS Category (
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    is_income BOOLEAN NOT NULL,
    is_expense BOOLEAN NOT NULL
);


INSERT INTO Category (id, name, is_income, is_expense)
SELECT 'Cat1', 'Restaurant', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat1' AND name = 'Restaurant'
);


INSERT INTO Category (id, name, is_income, is_expense)
SELECT 'Cat2', 'Telephone', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat2' AND name = 'Telephone'
);

INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat3' , 'Salary', true, false
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat3' AND name = 'Salary'
)

