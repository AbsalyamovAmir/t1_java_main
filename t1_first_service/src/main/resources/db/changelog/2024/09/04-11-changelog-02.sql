-- liquibase formatted sql

-- changeset a_absa:add_account_sequence
CREATE SEQUENCE IF NOT EXISTS account_seq START WITH 1 INCREMENT BY 50;

-- changeset a_absa:add_transaction_sequence
CREATE SEQUENCE IF NOT EXISTS transaction_seq START WITH 1 INCREMENT BY 50;