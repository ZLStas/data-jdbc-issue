-- liquibase formatted sql
-- changeSet failOnError:true logicalFilePath:schema.sql

CREATE TABLE a
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT

) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE b
(
    id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    a_id BIGINT,

    CONSTRAINT rule_id_fk
        FOREIGN KEY (a_id) REFERENCES a (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

