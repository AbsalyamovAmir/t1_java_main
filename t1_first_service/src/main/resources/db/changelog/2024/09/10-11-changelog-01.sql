-- liquibase formatted sql

-- changeset a_absa:update_account_table
ALTER TABLE account
    ADD COLUMN client_id BIGINT;
ALTER TABLE account
    ADD CONSTRAINT fk_status FOREIGN KEY (client_id) REFERENCES client(id);