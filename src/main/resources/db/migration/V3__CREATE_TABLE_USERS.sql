CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP(6),
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    updated_at TIMESTAMP(6)
);
