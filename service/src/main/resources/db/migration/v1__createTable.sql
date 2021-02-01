CREATE TABLE IF NOT EXISTS lsp.customer (
    customer_id UUID NOT NUll,
    name VARCHAR(45),
    gstin CHAR(15) NOT NULL,
    pan CHAR(10) NOT NULL,
    udyog_aadhaar CHAR(12) NOT NULL,
    email VARCHAR(45) NOT NULL,
    mobile CHAR(10),
    turnover DECIMAL,
    PRIMARY KEY (customer_id)
);