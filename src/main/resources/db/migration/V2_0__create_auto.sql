INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('98aeb101-bdee-46c7-978f-70d783b1d242', 'BMW', 'Model_111', 1111.11, null);
INSERT INTO engine(id, brand, volume)
VALUES ('6835ec7f-e13d-4a80-90a0-355be28f68ee', 'Brand_123', 1111);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('98aeb101-bdee-46c7-978f-70d783b1d242', 'Body_type_111', 1, '6835ec7f-e13d-4a80-90a0-355be28f68ee', '$', now());

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('0abf03b5-c7fa-460e-8c13-55bf9d9b3711', 'OPEL', 'Model_222', 222.22, null);
INSERT INTO engine(id, brand, volume)
VALUES ('a7470c32-d71e-4bfb-ba35-b37265ee21b2', 'Brand_222', 2222);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('0abf03b5-c7fa-460e-8c13-55bf9d9b3711', 'Body_type_222', 2, 'a7470c32-d71e-4bfb-ba35-b37265ee21b2', '₴', now());

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('bb9013a8-c3ac-42e0-96b5-a7d258065f11', 'BMW', 'Model_333', 33.3333, null);
INSERT INTO engine(id, brand, volume)
VALUES ('42c0cf84-4d4c-4ed0-a437-d490402794c2', 'Brand_334', 3333);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('bb9013a8-c3ac-42e0-96b5-a7d258065f11', 'Body_type_111', 3, '42c0cf84-4d4c-4ed0-a437-d490402794c2', '$', now());

