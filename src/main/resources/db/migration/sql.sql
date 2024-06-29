CREATE TABLE wallet (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        balance DECIMAL(19, 2) NOT NULL
);

CREATE TABLE transaction (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             amount DECIMAL(19, 2) NOT NULL,
                             type VARCHAR(50) NOT NULL,
                             date TIMESTAMP NOT NULL,
                             description VARCHAR(255),
                             wallet_id BIGINT,
                             CONSTRAINT fk_wallet FOREIGN KEY (wallet_id) REFERENCES wallet(id)
);