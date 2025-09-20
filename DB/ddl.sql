CREATE DATABASE IF NOT EXISTS esunlib CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE esunlib;

CREATE TABLE IF NOT EXISTS user (
                                    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    registration_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_time DATETIME NULL
    );

CREATE TABLE IF NOT EXISTS book (
                                    isbn VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    introduction TEXT
    );

CREATE TABLE IF NOT EXISTS inventory (
                                         inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         isbn VARCHAR(20) NOT NULL,
    store_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('AVAILABLE','BORROWED','PROCESSING','LOST','DAMAGED','DISCARDED') NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT fk_inventory_book FOREIGN KEY (isbn) REFERENCES book(isbn)
    );

CREATE TABLE IF NOT EXISTS borrowing_record (
                                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                user_id BIGINT NOT NULL,
                                                inventory_id BIGINT NOT NULL,
                                                borrowing_time DATETIME NOT NULL,
                                                return_time DATETIME NULL,
                                                CONSTRAINT fk_br_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_br_inv  FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id),
    INDEX idx_br_user (user_id),
    INDEX idx_br_inv (inventory_id),
    INDEX idx_br_open (inventory_id, return_time)
    );

-- 範例資料
INSERT IGNORE INTO book(isbn,name,author,introduction) VALUES
('9789864765560','Effective Java','Joshua Bloch','Best practices for Java'),
('9789863479116','Clean Architecture','Robert C. Martin','Clean architecture book');

INSERT IGNORE INTO inventory(isbn,status) VALUES
('9789864765560','AVAILABLE'),
('9789863479116','AVAILABLE');
