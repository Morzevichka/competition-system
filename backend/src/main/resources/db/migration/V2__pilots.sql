CREATE TABLE pilots (
    id UUID PRIMARY KEY,
    nickname VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    tg_username VARCHAR(100) UNIQUE
);
