CREATE TABLE aeroportos (

        id_aeroporto INT PRIMARY KEY AUTO_INCREMENT,

        nome_aeroporto VARCHAR(255) NOT NULL,

        codigo_iata CHAR(3) UNIQUE,

        cidade VARCHAR(100) NOT NULL,

        codigo_pais_iso CHAR(2) NOT NULL,

        latitude DECIMAL(9, 6) NOT NULL,

        longitude DECIMAL(9, 6) NOT NULL,

        altitude DECIMAL(10, 2)

);