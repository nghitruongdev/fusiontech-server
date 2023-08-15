USE FUSIONTECH;

ALTER TABLE APP_USER ADD COLUMN VERIFIED BOOLEAN DEFAULT FALSE;

ALTER TABLE APP_USER CHANGE VERIFIED IS_VERIFIED BOOLEAN DEFAULT FALSE;

ALTER TABLE PRODUCT_VARIANT MODIFY COLUMN ACTIVE BOOLEAN DEFAULT TRUE;

ALTER TABLE PRODUCT MODIFY COLUMN ACTIVE BOOLEAN DEFAULT TRUE;

UPDATE PRODUCT_VARIANT  SET ACTIVE = TRUE WHERE ACTIVE = FALSE;

UPDATE PRODUCT SET ACTIVE = TRUE WHERE ACTIVE = FALSE;

# ALTER TABLE REVIEW ADD CONSTRAINT UNIQUE (PRODUCT_ID, USER_ID);

DROP TRIGGER IF EXISTS set_default_address;
DELIMITER  //
CREATE TRIGGER set_default_address
    AFTER INSERT ON SHIPPING_ADDRESS
    FOR EACH ROW
BEGIN
    DECLARE user_address_count INT;

    -- Count the number of addresses for the user
    SELECT COUNT(*) INTO user_address_count
    FROM SHIPPING_ADDRESS
    WHERE user_id = NEW.user_id;

    -- If user has no addresses, set the new address as default
    IF user_address_count = 1 THEN
        UPDATE APP_USER
        SET default_address_id = NEW.id
        WHERE id = NEW.user_id;
    END IF;
END//
DELIMITER ;

ALTER TABLE VOUCHER
    ADD COLUMN DISCOUNT TINYINT UNSIGNED NOT NULL;

DROP VIEW IF EXISTS order_item_info;
CREATE VIEW order_item_info AS
SELECT OI.ID AS id, p.NAME AS name, PV.SKU AS sku, COALESCE(PV.IMAGES, P.IMAGES) AS images, OI.PRICE AS price, OI.QUANTITY AS quantity, P.DISCOUNT AS discount
FROM ORDER_ITEM OI
         JOIN APP_ORDER AO on AO.ID = oi.ORDER_ID
         JOIN PRODUCT_VARIANT PV on PV.ID = oi.VARIANT_ID
         JOIN PRODUCT P on P.ID = PV.PRODUCT_ID
;

SELECT * FROM order_item_info;
