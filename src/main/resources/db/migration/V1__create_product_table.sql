CREATE TABLE products
(
    id                   VARCHAR(50) PRIMARY KEY,
    active               BOOLEAN,
    created              BIGINT,
    default_price        DECIMAL,
    description          TEXT,
    name                 VARCHAR(100),
    shippable            BOOLEAN,
    statement_descriptor VARCHAR(200),
    tax_code             VARCHAR(50),
    unit_label           VARCHAR(50),
    updated              BIGINT,
    url                  VARCHAR(500)
);