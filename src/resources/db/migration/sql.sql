-- V1__Create_wallets_table.sql
CREATE TABLE wallet (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        balance DECIMAL(19, 2) NOT NULL
);

-- V2__Create_transactions_table.sql
CREATE TABLE transaction (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             amount DECIMAL(19, 2) NOT NULL,
                             type VARCHAR(50) NOT NULL,
                             date TIMESTAMP NOT NULL,
                             description VARCHAR(255)
);