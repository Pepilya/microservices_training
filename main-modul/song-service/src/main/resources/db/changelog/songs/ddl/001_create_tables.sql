--liquibase formatted sql
--changeset pepeliaevi:create-tables

CREATE TABLE songs(
   id SERIAL PRIMARY KEY,
   name VARCHAR(40),
   artist VARCHAR(200),
   album VARCHAR(200),
   song_length VARCHAR(10),
   resource_Id INT,
   song_year VARCHAR(4)
);
