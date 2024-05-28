CREATE TABLE IF NOT EXISTS mst_inventory
(
    id 					BIGINT NOT NULL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL UNIQUE,
    quantity            BIGINT NOT NULL CHECK (quantity >= 0),
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    modified_at         TIMESTAMP,
    deleted_at          TIMESTAMP
    );