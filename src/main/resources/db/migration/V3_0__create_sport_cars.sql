INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('5558bbf3-069a-42e1-b1b9-a0760e6f0936', 'ZAZ', 'Model_221', 222.11, null);
INSERT INTO engine(id, brand, volume)
VALUES ('965a7b75-a904-457c-aa7f-4d48e744db4b', 'Brand_221', 2211);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('5558bbf3-069a-42e1-b1b9-a0760e6f0936', 'Body_type_111', 21, '965a7b75-a904-457c-aa7f-4d48e744db4b', '$',
        now());
INSERT INTO sport_car(id, max_speed)
VALUES ('5558bbf3-069a-42e1-b1b9-a0760e6f0936', 221);

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('46b6f09c-dcfd-496e-9d47-c98ddb02f6dd', 'ZAZ', 'Model_222', 222.22, null);
INSERT INTO engine(id, brand, volume)
VALUES ('9225659b-41c5-4f2a-a0c1-58543f423db9', 'Brand_221', 2222);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('46b6f09c-dcfd-496e-9d47-c98ddb02f6dd', 'Body_type_111', 22, '9225659b-41c5-4f2a-a0c1-58543f423db9', '$',
        now());
INSERT INTO sport_car(id, max_speed)
VALUES ('46b6f09c-dcfd-496e-9d47-c98ddb02f6dd', 222);

INSERT INTO vehicle(id, manufacturer, model, price, invoice_id)
VALUES ('67ab448a-34c6-4f3d-9f39-1af4af825b12', 'ZAZ', 'Model_221', 222.33, null);
INSERT INTO engine(id, brand, volume)
VALUES ('e9c2f9a5-5dc5-40e3-9033-8670e8f3235b', 'Brand_221', 2233);
INSERT INTO auto(id, body_type, count, engine, currency, created)
VALUES ('67ab448a-34c6-4f3d-9f39-1af4af825b12', 'Body_type_111', 22, 'e9c2f9a5-5dc5-40e3-9033-8670e8f3235b', '$',
        now());
INSERT INTO sport_car(id, max_speed)
VALUES ('67ab448a-34c6-4f3d-9f39-1af4af825b12', 223);