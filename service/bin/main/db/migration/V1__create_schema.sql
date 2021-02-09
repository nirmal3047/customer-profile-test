CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID NOT NUll,
    user_id UUID NOT NULL UNIQUE,
    name VARCHAR(45),
    gstin CHAR(15) NOT NULL,
    pan CHAR(10) NOT NULL,
    udyog_aadhaar CHAR(12) NOT NULL,
    email VARCHAR(45) NOT NULL,
    mobile CHAR(10),
    turnover DECIMAL,
    gst_details VARCHAR(1000),
    PRIMARY KEY (customer_id)
);