CREATE TABLE currencies (
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(3)  NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    symbol      VARCHAR(10),
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE exchange_rates (
    id              BIGSERIAL PRIMARY KEY,
    from_currency   VARCHAR(3) NOT NULL,
    to_currency     VARCHAR(3) NOT NULL,
    rate            NUMERIC(18, 8) NOT NULL,
    fetched_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_rate_pair UNIQUE (from_currency, to_currency)
);

CREATE TABLE conversion_history (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT,
    from_currency   VARCHAR(3) NOT NULL,
    to_currency     VARCHAR(3) NOT NULL,
    amount          NUMERIC(18, 2) NOT NULL,
    converted_amount NUMERIC(18, 2) NOT NULL,
    rate            NUMERIC(18, 8) NOT NULL,
    converted_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO currencies (code, name, symbol) VALUES
    ('USD', 'US Dollar', '$'),
    ('BRL', 'Brazilian Real', 'R$'),
    ('EUR', 'Euro', '€'),
    ('GBP', 'British Pound', '£'),
    ('JPY', 'Japanese Yen', '¥'),
    ('ARS', 'Argentine Peso', '$'),
    ('CLP', 'Chilean Peso', '$'),
    ('MXN', 'Mexican Peso', '$');