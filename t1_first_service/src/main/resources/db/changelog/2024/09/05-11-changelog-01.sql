-- liquibase formatted sql

-- changeset a_absa:create_data_source_error_log_table
CREATE TABLE data_source_error_log (
    id BIGINT NOT NULL PRIMARY KEY,
    exception_stack_trace TEXT,
    error_message VARCHAR(255),
    method_signature VARCHAR(255)
);

-- changeset a_absa:add_data_source_error_log_sequence
CREATE SEQUENCE IF NOT EXISTS data_source_error_log_seq START WITH 1 INCREMENT BY 50;