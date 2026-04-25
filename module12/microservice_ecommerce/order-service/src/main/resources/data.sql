INSERT INTO orders (order_status, price) VALUES
                                                 ('PENDING', 1500.00),
                                                 ('CONFIRMED', 2200.50),
                                                 ('SHIPPED', 999.99),
                                                 ('DELIVERED', 4500.75),
                                                 ('CANCELLED', 1200.00),
                                                 ('PENDING', 1800.25),
                                                 ( 'CONFIRMED', 3000.00),
                                                 ('SHIPPED', 2750.40),
                                                 ( 'DELIVERED', 5100.90),
                                                 ( 'PENDING', 800.60);


INSERT INTO order_item (product_id, quantity, order_id) VALUES
                                                                (101, 2, 1),
                                                                (102, 1, 1),

                                                                (103, 3, 2),
                                                                (104, 2, 2),

                                                                (105, 1, 3),
                                                                ( 106, 4, 3),

                                                                ( 107, 2, 4),
                                                                ( 108, 5, 4),

                                                                ( 109, 1, 5),
                                                                ( 110, 2, 5),

                                                                ( 111, 3, 6),
                                                                ( 112, 1, 6),

                                                                ( 113, 2, 7),
                                                                ( 114, 4, 7),

                                                                (115, 1, 8),
                                                                (116, 2, 8),

                                                                ( 117, 3, 9),
                                                                ( 118, 1, 9),

                                                                ( 119, 2, 10),
                                                                (120, 5, 10);