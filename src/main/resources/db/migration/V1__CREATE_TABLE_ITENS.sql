CREATE TABLE itens (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    quantidade INTEGER NOT NULL,
    categoria VARCHAR(100),
    comprado BOOLEAN DEFAULT FALSE
);
