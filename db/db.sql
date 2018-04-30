CREATE DATABASE account_manager ENCODING 'UTF8';

CREATE TABLE IF NOT EXISTS users (
  id                      SERIAL             NOT NULL PRIMARY KEY,
  username                VARCHAR(60) UNIQUE NOT NULL,
  password                VARCHAR(60)        NOT NULL,
  account_non_expired     BOOLEAN DEFAULT TRUE,
  account_non_locked      BOOLEAN DEFAULT TRUE,
  credentials_non_expired BOOLEAN DEFAULT TRUE,
  enabled                 BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY NOT NULL,
  role VARCHAR(60) UNIQUE NOT NULL,
  username VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id INTEGER REFERENCES users (id),
  role_id INTEGER REFERENCES roles (id)
);

CREATE UNIQUE INDEX ue_ix_user_role ON users_roles (user_id, role_id);

INSERT INTO users (username, password) VALUES ('user_1', 'pass');
INSERT INTO roles (role, username) VALUES ('ROLE_USER', 'user_1');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

CREATE TABLE IF NOT EXISTS vds (
  id          SERIAL       NOT NULL PRIMARY KEY,
  ip          VARCHAR(45)  NOT NULL,
  login       VARCHAR(25)  NOT NULL,
  password    VARCHAR(255) NOT NULL,
  activated   TIMESTAMP    NOT NULL,
  deactivated TIMESTAMP    NOT NULL,
  note        TEXT
);

CREATE TABLE IF NOT EXISTS phone (
  id             SERIAL      NOT NULL PRIMARY KEY,
  reg_date       TIMESTAMP   NOT NULL DEFAULT now(),
  number         VARCHAR(25) NOT NULL,
  operator_url   VARCHAR(255),
  operator_login VARCHAR(25),
  operator_pass  VARCHAR(255),
  operator_name  VARCHAR(25),
  status         VARCHAR(25),
  note           TEXT
);

CREATE TABLE IF NOT EXISTS social_account (
  id          SERIAL       NOT NULL PRIMARY KEY,
  reg_date    TIMESTAMP    NOT NULL DEFAULT now(),
  social_type VARCHAR(25)  NOT NULL,
  login       VARCHAR(25)  NOT NULL,
  password    VARCHAR(255) NOT NULL,
  status      VARCHAR(25)  NOT NULL,
  note        TEXT,

  phone_id    INTEGER REFERENCES phone (id),
  vds_id      INTEGER REFERENCES vds (id)
);
