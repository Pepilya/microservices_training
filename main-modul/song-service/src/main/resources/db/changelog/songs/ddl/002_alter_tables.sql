--liquibase formatted sql
--changeset pepeliaevi:alter-tables

ALTER TABLE songs ALTER COLUMN name TYPE VARCHAR (200);