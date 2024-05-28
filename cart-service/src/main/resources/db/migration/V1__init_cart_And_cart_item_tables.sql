CREATE TABLE IF NOT EXISTS mst_cart
(
id 					BIGINT NOT NULL PRIMARY KEY,
user_id 			BIGINT NOT NULL,
total_price			FLOAT NOT NULL CHECK (total_price >= 0),
created_at 			TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
modified_at 		TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mst_cart_item
(
id 					BIGINT NOT NULL PRIMARY KEY,
cart_id 			BIGINT NOT NULL,
product_id	 		BIGINT NOT NULL,
quantity			INTEGER NOT NULL CHECK (quantity >= 0),
total_price			FLOAT NOT NULL CHECK (total_price >= 0)
);


-- mst_cart_item table foreign keys
ALTER TABLE mst_cart_item
    ADD CONSTRAINT fk_cart_item_cart
        FOREIGN KEY (cart_id) REFERENCES mst_cart(id)
