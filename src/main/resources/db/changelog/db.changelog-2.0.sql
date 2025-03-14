--liquibase formatted sql

--changeset azmeev:1
INSERT INTO SHOP_ITEM (title, description, img_path, price, created_date, last_modified_date)
VALUES
        ('ITEM1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item1.jpg', 10, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item2.jpg', 20, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item3.jpg', 30, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item4.jpg', 500, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item5.jpg', 300, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item6.jpg', 200, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM7', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item7.jpg', 100, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM8', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item8.jpg', 400, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM9', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item9.jpg', 40, '13.03.2025 10:00:00', '13.03.2025 10:00:00'),
        ('ITEM10', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent euismod leo leo, non lacinia felis consequat vel. Duis egestas sem non ex euismod, quis faucibus justo sagittis. In porttitor ante in ligula iaculis finibus. Suspendisse potenti. Donec eu faucibus velit, sit amet auctor ex. Sed orci ex, tincidunt at ligula ut, posuere semper diam.', 'item10.jpg', 70, '13.03.2025 10:00:00', '13.03.2025 10:00:00');