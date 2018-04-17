CREATE DATABASE account_manager ENCODING 'UTF8';

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(60) NOT NULL PRIMARY KEY,
  password VARCHAR(60) NOT NULL,
  enabled  BOOLEAN
);

CREATE TABLE IF NOT EXISTS authorities (
  username  VARCHAR(60) NOT NULL,
  authority VARCHAR(60) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username
  ON authorities (username, authority);
