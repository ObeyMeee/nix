DROP TABLE IF EXISTS sport_cars;
DROP TABLE IF EXISTS trucks;
DROP TABLE IF EXISTS autos;
DROP TABLE IF EXISTS details;
DROP TABLE IF EXISTS engines;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS invoices;


CREATE TABLE IF NOT EXISTS invoices
(
    "id"         VARCHAR NOT NULL PRIMARY KEY,
    "created_at" TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS details
(
    "id"     VARCHAR PRIMARY KEY,
    "detail" VARCHAR
);

CREATE TABLE IF NOT EXISTS vehicles
(
    "id"           VARCHAR NOT NULL PRIMARY KEY,
    "model"        VARCHAR,
    "price"        NUMERIC,
    "manufacturer" VARCHAR,
    "invoice_id"   VARCHAR
);

ALTER TABLE details
    ADD vehicles_id VARCHAR;
ALTER TABLE details
    ADD CONSTRAINT fk_vehicles_id
        FOREIGN KEY (vehicles_id)
            REFERENCES vehicles (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS engines
(
    "id"     VARCHAR NOT NULl PRIMARY KEY,
    "brand"  VARCHAR NOT NULL,
    "volume" INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS autos
(
    "id"         VARCHAR NOT NULL PRIMARY KEY,
    "body_type"  VARCHAR,
    "count"      INTEGER,
    "engine_id"  VARCHAR,
    "currency"   VARCHAR,
    "created"    TIMESTAMP,
    "vehicle_id" VARCHAR NOT NULL,
    CONSTRAINT fk_engine_id FOREIGN KEY (engine_id) REFERENCES engines (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_vehicle_id FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
        ON DELETE CASCADE
        on UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sport_cars
(
    "id"        VARCHAR NOT NULL PRIMARY KEY,
    "max_speed" INTEGER,
    "auto_id"   VARCHAR NOT NULL,
    CONSTRAINT fk_autos_id FOREIGN KEY (auto_id) REFERENCES autos (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS trucks
(
    "id"                VARCHAR NOT NULL PRIMARY KEY,
    "carrying_capacity" INTEGER,
    "auto_id"           VARCHAR NOT NULL,
    CONSTRAINT fk_auto_id FOREIGN KEY (auto_id) REFERENCES autos (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)