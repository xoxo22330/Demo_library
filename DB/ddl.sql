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

INSERT IGNORE INTO book(isbn,name,author,introduction) VALUES
('9789864765560','Effective Java','Joshua Bloch','Best practices for Java'),
('9789863479116','Clean Architecture','Robert C. Martin','Clean architecture book');

INSERT IGNORE INTO inventory(isbn,status) VALUES
('9789864765560','AVAILABLE'),
('9789863479116','AVAILABLE');

-- 書本資料
INSERT IGNORE INTO book(isbn,name,author,introduction) VALUES
('9780134685991','Effective Java, 3rd Edition','Joshua Bloch','Updated guide to Java programming best practices'),
('9780132350884','Clean Code','Robert C. Martin','A handbook of agile software craftsmanship'),
('9780201633610','Design Patterns: Elements of Reusable Object-Oriented Software','Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides','The classic book introducing GoF design patterns'),
('9780134494166','Core Java Volume I – Fundamentals','Cay S. Horstmann','Comprehensive introduction to Java programming'),
('9781617294945','Spring in Action, 5th Edition','Craig Walls','Guide to building modern applications with Spring framework'),
('9780596009205','Head First Design Patterns','Eric Freeman, Elisabeth Robson, Bert Bates, Kathy Sierra','Visual guide to design patterns in Java'),
('9781491950357','Building Microservices','Sam Newman','Designing fine-grained systems using microservice architecture'),
('9780137081073','Agile Software Development, Principles, Patterns, and Practices','Robert C. Martin','Agile development with principles and practices'),
('9781449373320','Designing Data-Intensive Applications','Martin Kleppmann','The big ideas behind reliable, scalable, and maintainable systems'),
('9780596007126','Head First Java, 2nd Edition','Kathy Sierra, Bert Bates','Beginner-friendly guide to learning Java programming');

-- 庫存資料 (每本至少一筆庫存)
INSERT IGNORE INTO inventory(isbn,status) VALUES
('9780134685991','AVAILABLE'),
('9780132350884','AVAILABLE'),
('9780201633610','AVAILABLE'),
('9780134494166','AVAILABLE'),
('9781617294945','AVAILABLE'),
('9780596009205','AVAILABLE'),
('9781491950357','AVAILABLE'),
('9780137081073','AVAILABLE'),
('9781449373320','AVAILABLE'),
('9780596007126','AVAILABLE');
