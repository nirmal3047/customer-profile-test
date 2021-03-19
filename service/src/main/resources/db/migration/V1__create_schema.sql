CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID NOT NUll,
    user_id UUID NOT NULL UNIQUE,
    name VARCHAR(45),
    gstin CHAR(15),
    pan CHAR(10),
    udyog_aadhaar CHAR(12),
    email VARCHAR(45) NOT NULL,
    mobile VARCHAR(13),
    turnover DECIMAL,
    PRIMARY KEY (customer_id)
);