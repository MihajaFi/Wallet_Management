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

INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat4' , 'Food', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat4' AND name = 'Food'
)

INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat5' , 'Shopping ', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat5' AND name = 'Shopping '
)
INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat6' , 'Accommodation', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat6' AND name = 'Accommodation'
)
INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat7' , 'Transport', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat7' AND name = 'Transport'
)
INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat8' , 'Investments', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat8' AND name = 'Investments'
)
INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat9' , 'beverages', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat9' AND name = 'beverages'
)
INSERT INTO Category (id , name , is_income , is_expense)
SELECT 'Cat10' , 'online shops', false, true
WHERE NOT EXISTS (
    SELECT 1 FROM Category
    WHERE id = 'Cat10' AND name = 'online shops'
)

