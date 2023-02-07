--liquibase formatted sql
--changeset pepeliaevi:create-tables

CREATE TABLE resources(
   id SERIAL PRIMARY KEY,
   file_name VARCHAR(200),
   path VARCHAR(200)
);
