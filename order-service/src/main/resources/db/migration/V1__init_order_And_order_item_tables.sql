CREATE TABLE IF NOT EXISTS mst_order
(
id 					BIGSERIAL PRIMARY KEY,
user_id 			BIGINT NOT NULL,
total_price			FLOAT NOT NULL CHECK (total_price >= 0),
created_at 			TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
modified_at 		TIMESTAMP,
deleted_at 			TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mst_order_item
(
id 					BIGSERIAL PRIMARY KEY,
product_id	  		BIGINT NOT NULL,
order_id 			BIGINT NOT NULL,
quantity 			INTEGER NOT NULL,
total_price			FLOAT NOT NULL CHECK (total_price >= 0)
);

-- mst_order_item table foreign keys
ALTER TABLE mst_order_item
    ADD CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id) REFERENCES mst_order(id)