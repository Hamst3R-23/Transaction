CREATE TABLE IF NOT EXISTS ACCOUNT (
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    amount double PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS LOG (
    id SERIAL PRIMARY KEY,
    accountId BIGINT,
    operation varchar(50),
    amount double PRECISION,
    log varchar(100),
    time TIMESTAMP
);