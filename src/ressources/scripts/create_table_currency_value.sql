CREATE TABLE IF NOT EXISTS CurrencyValue (
    id SERIAL PRIMARY KEY,
    currency_from INT REFERENCES Currency(id),
    currency_to INT REFERENCES Currency(id),
    exchange_rate DECIMAL(18, 5) NOT NULL,
    value_date DATE NOT NULL
);


INSERT INTO CurrencyValue (currency_from, currency_to, exchange_rate, value_date) VALUES
    ((SELECT id FROM Currency WHERE code = 'EUR'),
     (SELECT id FROM Currency WHERE code = 'MGA'),
     4500.00000, '2023-12-18');

INSERT INTO CurrencyValue (currency_from, currency_to, exchange_rate, value_date) VALUES
    ((SELECT id FROM Currency WHERE code = 'EUR'),
     (SELECT id FROM Currency WHERE code = 'MGA'),
     4600.00000, '2023-12-19');
