CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID NOT NUll,
    name VARCHAR(45),
    email VARCHAR(45) NOT NULL,
    mobile CHAR(10),
    gst
)