ALTER TABLE tb_subscription
    MODIFY COLUMN payment_status ENUM('WAITING_PAYMENT', 'PAID') NOT NULL;