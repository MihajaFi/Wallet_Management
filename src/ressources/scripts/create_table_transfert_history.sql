CREATE TABLE IF NOT EXISTS TransferHistory (
    id SERIAL PRIMARY KEY,
    sender_id INT REFERENCES Transaction(id),
    receiver_id INT REFERENCES Transaction(id),
    transfer_date TIMESTAMP NOT NULL
);
