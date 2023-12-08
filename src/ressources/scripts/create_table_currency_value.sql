CREATE TABLE IF NOT EXISTS CurrencyValue (
    id SERIAL PRIMARY KEY,
    currency_from INT REFERENCES Currency(id),
    currency_to INT REFERENCES Currency(id),
    exchange_rate DECIMAL(18, 5) NOT NULL,
    value_date DATE NOT NULL
);

