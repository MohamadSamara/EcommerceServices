CREATE TABLE IF NOT EXISTS mst_user
(
    id 				BIGINT PRIMARY KEY,
    username		VARCHAR (255) NOT NULL,
    password 		VARCHAR (255) NOT NULL,
    first_name 		VARCHAR (255) NOT NULL,
    role 		    VARCHAR (255) NOT NULL,
    last_name 		VARCHAR (255) NOT NULL,
    telephone 		VARCHAR(255) NOT NULL,
    created_at 		TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    modified_at 	TIMESTAMP
);