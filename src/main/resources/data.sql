CREATE TABLE PRODUCT (
                         ID UUID PRIMARY KEY,
                         BUSINESS_ID UUID NOT NULL,
                         TITLE VARCHAR(255) NOT NULL,
                         QUANTITY_IN_STOCK INT NOT NULL,
                         CURRENCY VARCHAR(10) NOT NULL,
                         PRICE DECIMAL(10, 2) NOT NULL,
                         ROW_VERSION INT NOT NULL DEFAULT 0
);

-- INSERT INTO PRODUCT (ID, BUSINESS_ID, TITLE, QUANTITY_IN_STOCK, CURRENCY, PRICE, ROW_VERSION)
-- VALUES ('123e4567-e89b-12d3-a456-426614174000', '223e4567-e89b-12d3-a456-426614174000', 'Product 1', 100, 'USD', 29.99, X'01');
