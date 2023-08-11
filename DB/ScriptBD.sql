-- Database: currencies
CREATE DATABASE currencies
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Mexico.1252'
    LC_CTYPE = 'Spanish_Mexico.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	
--Tabla para historial llamados
CREATE TABLE history(
	id SERIAL PRIMARY KEY,
	date DATE DEFAULT now(),
	time TIME DEFAULT now(),
	url CHARACTER(200) NOT NULL,
	time_request_ms bigint NOT NULL,
	information CHARACTER(20000) NOT NULL
);


--Tabla datos actualizados
CREATE TABLE currencies(
	id SERIAL,
	code CHARACTER(5) NOT NULL,
	value double precision NOT NULL,
	lastUpdate TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	requestDate TIMESTAMP WITHOUT TIME ZONE NOT NULL
);