-- liquibase formatted sql

-- changeset a_absa:add_columns_in_transaction_and_account_and_client_tables
ALTER TABLE transaction
    ADD COLUMN transaction_status VARCHAR(255),
    ADD COLUMN transaction_id BIGINT,
    ADD COLUMN timestamp TIMESTAMP;

ALTER TABLE account
    ADD COLUMN account_status VARCHAR(255),
    ADD COLUMN account_id BIGINT,
    ADD COLUMN frozen_amount DECIMAL(19, 4);

ALTER TABLE client
    ADD COLUMN client_id BIGINT;