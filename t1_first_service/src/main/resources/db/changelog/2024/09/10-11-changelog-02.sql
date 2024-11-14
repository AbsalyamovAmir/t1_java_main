-- liquibase formatted sql

-- changeset a_absa:update_transaction_table
ALTER TABLE transaction
    ADD COLUMN account_id BIGINT;
ALTER TABLE transaction
    ADD CONSTRAINT fk_status FOREIGN KEY (account_id) REFERENCES account(id);