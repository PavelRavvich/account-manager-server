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

-- Query to vds
SELECT *
FROM vds
WHERE vds.id = 1;
SELECT *
FROM vds
WHERE vds.note ILIKE '%fun%';
SELECT *
FROM vds
WHERE vds.ip ILIKE '%123.%';
SELECT *
FROM vds
WHERE vds.login ILIKE '%logi%';
SELECT *
FROM vds
WHERE vds.password ILIKE '%s_1%';

SELECT *
FROM vds
WHERE vds.activated BETWEEN
'2012-04-01 23:55:00' :: TIMESTAMP AND
'2018-04-17 12:57:15.449000' :: TIMESTAMP;

SELECT *
FROM vds
WHERE deactivated BETWEEN
'2012-04-01 23:55:00' :: TIMESTAMP AND
'2018-04-17 12:57:15.449000' :: TIMESTAMP;

-- Query for phone
SELECT *
FROM phone
WHERE id = 1;
SELECT *
FROM phone
WHERE phone.number ILIKE '%+7890%';
SELECT *
FROM phone
WHERE phone.operator_url ILIKE '%bee%';
SELECT *
FROM phone
WHERE phone.operator_login ILIKE '%test%';
SELECT *
FROM phone
WHERE phone.operator_pass ILIKE '%pass%';
SELECT *
FROM phone
WHERE phone.operator_name ILIKE '%ee%';
SELECT *
FROM phone
WHERE phone.status = 'Active';
SELECT *
FROM phone
WHERE phone.note ILIKE '%e N%';

SELECT *
FROM phone
WHERE phone.reg_date BETWEEN
'2012-04-01 23:55:00' :: TIMESTAMP AND
'2018-04-17 12:57:15.449000' :: TIMESTAMP;

-- Query for phone with full join
SELECT
  v.id           AS vds_id,
  v.ip,
  v.login        AS vds_login,
  v.password     AS vds_passowd,
  v.activated    AS vds_activated,
  v.deactivated  AS vds_deacteveted,
  v.note         AS vds_notes,
  a.id           AS social_id,
  a.reg_date     AS social_reg_date,
  a.social_type,
  a.login        AS social_login,
  a.password     AS social_pass,
  a.status       AS social_status,
  a.note         AS social_note,
  p.id           AS phone_id,
  p.reg_date     AS phone_reg_date,
  p.number,
  p.operator_url AS op_url,
  p.operator_login,
  p.operator_pass,
  p.operator_name,
  p.status       AS phone_status,
  p.note         AS phone_note
FROM vds AS v
  INNER JOIN social_account a ON v.id = a.vds_id
  INNER JOIN phone p ON a.phone_id = p.id