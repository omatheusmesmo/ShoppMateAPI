
ALTER TABLE itens RENAME TO items;


ALTER TABLE items RENAME COLUMN nome TO name;
ALTER TABLE items RENAME COLUMN quantidade TO quantity;
ALTER TABLE items RENAME COLUMN categoria TO category;
ALTER TABLE items RENAME COLUMN comprado TO bought;
