-- liquibase formatted sql

-- changeset a_absa:create_account_table
CREATE TABLE IF NOT EXISTS account (
    id BIGINT NOT NULL PRIMARY KEY,
    account_type VARCHAR(10) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);

-- changeset a_absa:create_transaction_table
CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT NOT NULL PRIMARY KEY,
    sum_transaction DECIMAL(10, 2) NOT NULL,
    time_transaction TIMESTAMP NOT NULL
);