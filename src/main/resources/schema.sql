CREATE SCHEMA IF NOT EXISTS FUSIONTECH COLLATE UTF8MB4_0900_AI_CI;

SET FOREIGN_KEY_CHECKS = 0;

DROP USER IF EXISTS 'fusiontech_user'@'%';

CREATE USER IF NOT EXISTS 'fusiontech_user'@'%' IDENTIFIED WITH mysql_native_password BY 'password';

GRANT ALL PRIVILEGES ON FUSIONTECH.* TO 'fusiontech_user'@'%';

FLUSH PRIVILEGES;
-- GRANT ALTER, ALTER ROUTINE, CREATE, CREATE ROUTINE, CREATE TEMPORARY TABLES, CREATE VIEW, DELETE, DROP, EVENT, EXECUTE, INDEX, INSERT, LOCK TABLES, REFERENCES, SELECT, SHOW VIEW, TRIGGER, UPDATE ON FUSIONTECH.* TO fusiontech_admin@'%';

USE FUSIONTECH;

# GRANT ALL PRIVILEGES ON FUSIONTECH.* TO 'fusiontech_admin'@'%';

# ALTER TABLE APP_USER DROP FOREIGN KEY FKpa9omm1k1vi0npantpfepafwt;

DROP TABLE IF EXISTS FAVORITE CASCADE;

DROP TABLE IF EXISTS ORDER_ITEM CASCADE;

DROP TABLE IF EXISTS APP_ORDER CASCADE;

DROP TABLE IF EXISTS PAYMENT CASCADE;

DROP TABLE IF EXISTS REVIEW CASCADE;

DROP TABLE IF EXISTS VOUCHER CASCADE;

DROP TABLE IF EXISTS SHIPPING_ADDRESS CASCADE;

DROP TABLE IF EXISTS VARIANT_INVENTORY_DETAIL CASCADE;

DROP TABLE IF EXISTS PRODUCT_VARIANT_INVENTORY CASCADE;

DROP TABLE IF EXISTS VARIANT_SPECIFICATION CASCADE;

DROP TABLE IF EXISTS PRODUCT_SPECIFICATION CASCADE;

DROP TABLE IF EXISTS PRODUCT_VARIANT CASCADE;

DROP TABLE IF EXISTS PRODUCT CASCADE;

DROP TABLE IF EXISTS BRAND CASCADE;

DROP TABLE IF EXISTS CATEGORY CASCADE;

DROP TABLE IF EXISTS APP_USER CASCADE;

DROP PROCEDURE IF EXISTS TOP_SPENT_CUSTOMER;

DROP FUNCTION IF EXISTS GET_AVAILABLE_QUANTITY;

DROP FUNCTION IF EXISTS GET_SOLD_COUNT;

DROP FUNCTION IF EXISTS GET_TOTAL_QUANTITY;

CREATE TABLE APP_USER
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    DATE_OF_BIRTH      DATE,
    EMAIL              VARCHAR(255),
    FIREBASE_UID       VARCHAR(28),
    FIRST_NAME         NVARCHAR(50) DEFAULT '',
    GENDER             TINYINT,
    IMAGE              VARCHAR(255),
    IS_DISABLED        BOOLEAN,
    IS_STAFF           BOOLEAN,
    LAST_NAME          NVARCHAR(50) DEFAULT '',
    PHONE_NUMBER       VARCHAR(15),
    DEFAULT_ADDRESS_ID BIGINT,
    CONSTRAINT UK_user_email UNIQUE (EMAIL),
    CONSTRAINT UK_user_firebase UNIQUE (FIREBASE_UID),
    CONSTRAINT UK_user_phone UNIQUE (PHONE_NUMBER)
);

CREATE TABLE BRAND
(
    ID    INT AUTO_INCREMENT PRIMARY KEY,
    IMAGE VARCHAR(255),
    NAME  NVARCHAR(255) NOT NULL,
    SLUG  VARCHAR(255)  NOT NULL,
    CONSTRAINT UK_brand_slug UNIQUE (SLUG)
);

CREATE TABLE CATEGORY
(
    ID             INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION    NVARCHAR(255),
    IMAGE          VARCHAR(255),
    NAME           NVARCHAR(255) NOT NULL,
    SLUG           VARCHAR(255)  NOT NULL,
    SPECIFICATIONS JSON,
    CONSTRAINT UK_category_slug UNIQUE (SLUG)
);

CREATE TABLE PAYMENT
(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    AMOUNT  DOUBLE PRECISION NOT NULL CHECK ( AMOUNT > 0 ),
    METHOD  VARCHAR(50)      NOT NULL,
    PAID_AT TIMESTAMP,
    STATUS  VARCHAR(50)
);

create table APP_ORDER
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    ADDRESS_ID   BIGINT,
    EMAIL        VARCHAR(255),
    NOTE         NVARCHAR(255),
    PAYMENT_ID   BIGINT      NOT NULL,
    PURCHASED_AT TIMESTAMP DEFAULT NOW(),
    STATUS       VARCHAR(50) NOT NULL,
    USER_ID      BIGINT,
    VOUCHER_ID   BIGINT,
    CONSTRAINT UK_order_payment UNIQUE (PAYMENT_ID),
    CONSTRAINT FK_order_payment
        FOREIGN KEY (PAYMENT_ID) REFERENCES PAYMENT (ID),
    CONSTRAINT FK_order_user
        FOREIGN KEY (USER_ID) REFERENCES APP_USER (ID)
);

create table PRODUCT
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION TEXT,
    FEATURES    JSON,
    IMAGES      JSON,
    NAME        NVARCHAR(255) NOT NULL,
    SLUG        VARCHAR(255),
    SUMMARY     NVARCHAR(255),
    STATUS      VARCHAR(50),
    ACTIVE      BOOLEAN,
    DISCOUNT    TINYINT UNSIGNED CHECK (DISCOUNT IS NULL OR DISCOUNT BETWEEN 0 AND 100),
    BRAND_ID    INTEGER,
    CATEGORY_ID INTEGER,
    CONSTRAINT UK_product_slug UNIQUE (SLUG),
    CONSTRAINT FK_product_category
        FOREIGN KEY (CATEGORY_ID) REFERENCES Category (ID)
            ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT FK_product_brand
        FOREIGN KEY (BRAND_ID) REFERENCES BRAND (ID)
            ON UPDATE CASCADE ON DELETE SET NULL
);

create table FAVORITE
(
    PRODUCT_ID BIGINT NOT NULL,
    USER_ID    BIGINT NOT NULL,
    PRIMARY KEY (PRODUCT_ID, USER_ID),
    CONSTRAINT FK_favorite_product
        FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_favorite_user
        FOREIGN KEY (USER_ID) REFERENCES APP_USER (ID)
            ON UPDATE CASCADE ON DELETE CASCADE
);

create table PRODUCT_SPECIFICATION
(
    ID                  INT AUTO_INCREMENT PRIMARY KEY,
    NAME                NVARCHAR(50) NOT NULL,
    SPECIFICATION_VALUE NVARCHAR(50) NOT NULL,
    CONSTRAINT UK_specification_name_value UNIQUE (NAME, SPECIFICATION_VALUE)
);

create table PRODUCT_VARIANT
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    IMAGES     JSON,
    PRICE      DOUBLE PRECISION NOT NULL CHECK (PRICE >= 0),
    SKU        VARCHAR(100)     NOT NULL,
    ACTIVE     BOOLEAN,
    PRODUCT_ID BIGINT           NOT NULL,
    CONSTRAINT UK_variant_sku UNIQUE (SKU),
    constraint FK_variant_product
        FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
            ON UPDATE CASCADE ON DELETE CASCADE
);

create table ORDER_ITEM
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    PRICE      DOUBLE PRECISION NOT NULL CHECK ( PRICE >= 0 ),
    QUANTITY   TINYINT          NOT NULL CHECK ( QUANTITY > 0 ),
    ORDER_ID   BIGINT           NOT NULL,
    VARIANT_ID BIGINT           NOT NULL,
    CONSTRAINT UK_order_variant UNIQUE (ORDER_ID, VARIANT_ID),
    CONSTRAINT FK_order_item_variant
        FOREIGN KEY (VARIANT_ID) REFERENCES PRODUCT_VARIANT (ID)
            ON UPDATE CASCADE ON DELETE NO ACTION,
    CONSTRAINT FK_order_item_order
        FOREIGN KEY (ORDER_ID) REFERENCES APP_ORDER (ID)
            ON UPDATE CASCADE ON DELETE NO ACTION

);

CREATE TABLE VOUCHER
(
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE                VARCHAR(50) NOT NULL COLLATE utf8mb4_unicode_ci,
    DESCRIPTION         NVARCHAR(50),
    MIN_ORDER_AMOUNT    DOUBLE PRECISION CHECK ( MIN_ORDER_AMOUNT >= 0 ),
    MAX_DISCOUNT_AMOUNT DOUBLE PRECISION CHECK ( MAX_DISCOUNT_AMOUNT >= 0 ),
    START_DATE          TIMESTAMP,
    EXPIRATION_DATE     TIMESTAMP,
    LIMIT_USAGE         INT,
    USER_LIMIT_USAGE    TINYINT UNSIGNED,
    CONSTRAINT UK_voucher_code UNIQUE (CODE)
);

ALTER TABLE APP_ORDER
    ADD CONSTRAINT FK_order_voucher
        FOREIGN KEY (VOUCHER_ID) REFERENCES VOUCHER (ID)
            ON UPDATE CASCADE ON DELETE NO ACTION;

create table PRODUCT_VARIANT_INVENTORY
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_BY         BIGINT NOT NULL,
    CREATED_DATE       TIMESTAMP DEFAULT NOW(),
    LAST_MODIFIED_BY   BIGINT,
    LAST_MODIFIED_DATE TIMESTAMP,
    CONSTRAINT FK_variant_inventory_created
        FOREIGN KEY (CREATED_BY) REFERENCES APP_USER (ID),
    FOREIGN KEY (LAST_MODIFIED_BY) REFERENCES APP_USER (ID)
);

CREATE TABLE REVIEW
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    COMMENT    NVARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT NOW(),
    RATING     TINYINT,
    PRODUCT_ID BIGINT NOT NULL,
    USER_ID    BIGINT NOT NULL,
    CONSTRAINT FK_review_app_user
        FOREIGN KEY (USER_ID) REFERENCES APP_USER (id)
            ON DELETE CASCADE,
    CONSTRAINT FK_review_product
        FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (id)
            ON DELETE CASCADE
);

create table SHIPPING_ADDRESS
(
    ID       BIGINT AUTO_INCREMENT PRIMARY KEY,
    ADDRESS  NVARCHAR(255) NOT NULL,
    DISTRICT NVARCHAR(50),
    NAME     NVARCHAR(100) NOT NULL,
    PHONE    VARCHAR(15),
    PROVINCE NVARCHAR(50),
    WARD     NVARCHAR(50),
    USER_ID  BIGINT        NOT NULL,
    CONSTRAINT FK_shipping_address_user
        FOREIGN KEY (USER_ID) REFERENCES APP_USER (ID)
);

ALTER TABLE APP_USER
    ADD CONSTRAINT FK_DEFAULT_ADDRESS_USER
        FOREIGN KEY (DEFAULT_ADDRESS_ID) REFERENCES SHIPPING_ADDRESS (ID)
            ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE APP_ORDER
    ADD CONSTRAINT FK_ORDER_ADDRESS
        FOREIGN KEY (ADDRESS_ID) REFERENCES SHIPPING_ADDRESS (ID)
            ON UPDATE CASCADE ON DELETE SET NULL;

CREATE TABLE VARIANT_INVENTORY_DETAIL
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    QUANTITY     INT    NOT NULL CHECK ( QUANTITY > 0 ),
    VARIANT_ID   BIGINT NOT NULL,
    INVENTORY_ID BIGINT NOT NULL,
    CONSTRAINT FK_inventory_detail_inventory
        FOREIGN KEY (INVENTORY_ID) REFERENCES PRODUCT_VARIANT_INVENTORY (ID)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_inventory_detail_variant
        FOREIGN KEY (VARIANT_ID) REFERENCES PRODUCT_VARIANT (ID)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE VARIANT_SPECIFICATION
(
    VARIANT_ID       BIGINT NOT NULL,
    SPECIFICATION_ID INT    NOT NULL,
    PRIMARY KEY (VARIANT_ID, SPECIFICATION_ID),
    CONSTRAINT FK_variant_specification_specification
        FOREIGN KEY (SPECIFICATION_ID) REFERENCES PRODUCT_SPECIFICATION (ID)
            ON DELETE NO ACTION,
    CONSTRAINT FK_variant_specification_variant
        FOREIGN KEY (VARIANT_ID) REFERENCES PRODUCT_VARIANT (ID)
            ON DELETE CASCADE
);

DELIMITER //
DROP FUNCTION IF EXISTS GET_SOLD_COUNT //
CREATE FUNCTION GET_SOLD_COUNT(p_variantId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE v_soldCount BIGINT;

    SELECT COALESCE(SUM(oi.quantity), 0)
    INTO v_soldCount
    FROM ORDER_ITEM oi
             JOIN APP_ORDER ao ON oi.order_id = ao.id
    WHERE oi.variant_id = p_variantId
      AND ao.statuS NOT IN ('DELIVERED_FAILED', 'CANCELLED', 'DENIED');

    RETURN v_soldCount;
END //
DELIMITER ;

DELIMITER //
DROP FUNCTION IF EXISTS get_total_quantity//
CREATE FUNCTION get_total_quantity(p_variantId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE v_totalQty BIGINT;
    SELECT COALESCE(SUM(quantity), 0)
    FROM variant_inventory_detail vi
    WHERE vi.variant_id = p_variantId
    INTO v_totalQty;
    RETURN v_totalQty;
END//
DELIMITER ;

DELIMITER //
DROP FUNCTION IF EXISTS get_available_quantity//
CREATE FUNCTION get_available_quantity(p_variantId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE v_availQty BIGINT;

    SELECT get_total_quantity(p_variantId) - get_sold_count(p_variantId)
    INTO v_availQty;
    RETURN v_availQty;
END//
DELIMITER ;

DELIMITER //
DROP FUNCTION IF EXISTS get_product_min_price//
CREATE FUNCTION get_product_min_price(productId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE minPrice BIGINT;
    SELECT COALESCE(MIN(PRICE), 0) FROM PRODUCT_VARIANT WHERE PRODUCT_ID = productId AND ACTIVE = TRUE
    INTO minPrice;
    RETURN minPrice;
END//
DELIMITER ;

DELIMITER //
DROP FUNCTION IF EXISTS get_product_max_price//
CREATE FUNCTION get_product_max_price(productId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE maxPrice BIGINT;
    SELECT COALESCE(MAX(PRICE), 0) FROM PRODUCT_VARIANT WHERE PRODUCT_ID = productId AND ACTIVE = TRUE
    INTO maxPrice;
    RETURN maxPrice;
END//
DELIMITER ;

DELIMITER //
DROP FUNCTION IF EXISTS get_product_available_quantity//
CREATE FUNCTION get_product_available_quantity(productId BIGINT) RETURNS BIGINT
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE availableQty BIGINT;
    SELECT COALESCE(SUM(get_available_quantity(ID)), 0) FROM PRODUCT_VARIANT WHERE PRODUCT_ID = productId AND ACTIVE = TRUE
    INTO availableQty;
    RETURN availableQty;
END//
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS top_spent_customer//
CREATE PROCEDURE top_spent_customer()
BEGIN
    SELECT u.id                                   AS id,
           CONCAT(u.last_name, ' ', u.first_name) AS fullName,
           SUM(p.amount)                          AS totalSpent
    FROM app_user u
             JOIN APP_ORDER o ON o.user_id = u.id
             JOIN payment p ON o.payment_id = p.id
    GROUP BY u.id, CONCAT(u.last_name, ' ', u.first_name)
    ORDER BY totalspent DESC;
END //
DELIMITER ;

SET FOREIGN_KEY_CHECKS = 1;
