INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('e2dd9b56-4c8b-4868-9197-21e9f4cbf0f6', 'BMW', 'Model_311', 333.11, null);
INSERT INTO engine(id, brand, volume)
VALUES ('83f9b357-77da-4a33-9b05-3eb99d3cd412', 'Brand_331', 3311);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('e2dd9b56-4c8b-4868-9197-21e9f4cbf0f6', 'Body_type_111', 31, '83f9b357-77da-4a33-9b05-3eb99d3cd412', '$',
        now());
INSERT INTO truck(id, max_carrying_capacity)
VALUES ('e2dd9b56-4c8b-4868-9197-21e9f4cbf0f6', 331);

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('a8d23e45-88b1-47e5-b14b-293273f05ede', 'OPEL', 'Model_332', 333.22, null);
INSERT INTO engine(id, brand, volume)
VALUES ('806223fb-5fbd-4772-84fd-b9e37e07a588', 'Brand_332', 3322);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('a8d23e45-88b1-47e5-b14b-293273f05ede', 'Body_type_321', 32, '806223fb-5fbd-4772-84fd-b9e37e07a588', '$',
        now());
INSERT INTO truck(id, max_carrying_capacity)
VALUES ('a8d23e45-88b1-47e5-b14b-293273f05ede', 333);

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('e7ff6bd4-651c-42ed-80b4-e2da0884047b', 'ZAZ', 'Model_333', 333.33, null);
INSERT INTO engine(id, brand, volume)
VALUES ('4720ae24-8260-484b-85b1-dff3d50bd216', 'Brand_333', 3333);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('e7ff6bd4-651c-42ed-80b4-e2da0884047b', 'Body_type_333', 33, '4720ae24-8260-484b-85b1-dff3d50bd216', '$',
        now());
INSERT INTO truck(id, max_carrying_capacity)
VALUES ('e7ff6bd4-651c-42ed-80b4-e2da0884047b', 333);