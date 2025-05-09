TRUNCATE shop_order CASCADE;
TRUNCATE shop_cart_item CASCADE;
TRUNCATE shop_item CASCADE;

INSERT INTO SHOP_ITEM (id, title, description, img_path, price)
VALUES
    (1, 'ITEM1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item1.jpg', 10),
    (2, 'ITEM2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item2.jpg', 20),
    (3, 'ITEM3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item3.jpg', 30),
    (4, 'ITEM4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item4.jpg', 500),
    (5, 'ITEM5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item5.jpg', 300),
    (6, 'ITEM6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item6.jpg', 200),
    (7, 'ITEM7', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item7.jpg', 100),
    (8, 'ITEM8', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item8.jpg', 400),
    (9, 'ITEM9', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item9.jpg', 40),
    (10, 'ITEM10', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item10.jpg', 70);

SELECT SETVAL('shop_item_id_seq', (SELECT MAX(id) FROM shop_item));

TRUNCATE SHOP_USER CASCADE;
INSERT INTO SHOP_USER (id, username)
VALUES (1, 'user01'),
       (2, 'user02');

TRUNCATE SHOP_ORDER CASCADE;


INSERT INTO SHOP_ORDER (id, user_id)
VALUES (1, 1),
       (2, 1);

SELECT SETVAL('shop_order_id_seq', (SELECT MAX(id) FROM shop_order));