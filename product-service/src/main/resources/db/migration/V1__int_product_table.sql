CREATE TABLE IF NOT EXISTS mst_product
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    name        	VARCHAR (255) NOT NULL,
    description	    VARCHAR (255) NOT NULL,
    category_id 	BIGINT NOT NULL,
    inventory_id 	BIGINT NOT NULL,
    discount_id	    BIGINT NOT NULL,
    price   		FLOAT NOT NULL,
    sale_price      FLOAT NOT NULL,
    created_at   	TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    modified_at     TIMESTAMP,
    deleted_at   	TIMESTAMP
);