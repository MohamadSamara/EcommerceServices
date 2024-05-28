CREATE TABLE IF NOT EXISTS mst_discount
(
    id 					BIGINT NOT NULL PRIMARY KEY,
    name				VARCHAR (255) NOT NULL UNIQUE,
    description 		VARCHAR (255) NOT NULL,
    discount_percent    FLOAT NOT NULL,
    is_active			BOOLEAN NOT NULL,
    created_at 			TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    modified_at 		TIMESTAMP,
    deleted_at 	    	TIMESTAMP
);