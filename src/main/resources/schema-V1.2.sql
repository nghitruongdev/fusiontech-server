USE fusiontech;

-- get revenue from all years
DROP PROCEDURE IF EXISTS get_revenue_all_year;
CREATE
    DEFINER = root@localhost PROCEDURE get_revenue_all_year()
BEGIN
    SELECT COUNT(o.ID)   AS totalSales,
           SUM(AMOUNT)   AS totalRevenue,
           AVG(oi.PRICE) AS averagePrice
    FROM APP_ORDER o
             JOIN
         ORDER_ITEM oi ON o.ID = oi.ORDER_ID
             JOIN
         PAYMENT p on o.PAYMENT_ID = p.ID
    WHERE o.STATUS = 'COMPLETED'
      AND p.STATUS = 'COMPLETED';
END;
call get_revenue_all_year();


-- get revenue from that every years
DROP PROCEDURE IF EXISTS get_revenue;
CREATE
    DEFINER = root@localhost procedure get_revenue()
BEGIN

    SELECT COUNT(o.ID)          AS totalSales,
           SUM(AMOUNT)        AS totalRevenue,
           YEAR(o.PURCHASED_AT) as saleYear
    FROM APP_ORDER o
             JOIN
         ORDER_ITEM oi ON o.ID = oi.ORDER_ID
             JOIN
         PAYMENT p on o.PAYMENT_ID = p.ID
    WHERE o.STATUS = 'COMPLETED'
      AND p.STATUS = 'COMPLETED'
    GROUP BY saleYear;
END;
CALL get_revenue();

DROP PROCEDURE IF EXISTS get_revenue_by_day;
CREATE
    DEFINER = root@localhost PROCEDURE get_revenue_by_day(in targetDay date)
BEGIN
    SELECT COUNT(o.ID)         AS totalSales,
           SUM(AMOUNT)         AS totalRevenue,
           DAY(o.PURCHASED_AT) as currentDay
    FROM APP_ORDER o
             JOIN
         ORDER_ITEM oi ON o.ID = oi.ORDER_ID
             JOIN
         PAYMENT p on o.PAYMENT_ID = p.ID
    WHERE o.STATUS = 'COMPLETED'
      AND p.STATUS = 'COMPLETED'
      AND DATE(o.PURCHASED_AT) = targetDay
    GROUP BY currentDay;
END;
call get_revenue_by_day('2023-08-18');


-- GET MOST SELLING PRODUCTS BY DATE RANGE
DROP PROCEDURE IF EXISTS get_best_selling_products;
CREATE
    DEFINER = root@localhost PROCEDURE get_best_selling_products(IN startDate date, IN endDate date, IN size int)
BEGIN
    SELECT p.ID                  AS id,
           GET_SOLD_COUNT(pv.ID) as sold
    FROM PRODUCT p
             JOIN
         PRODUCT_VARIANT pv ON p.ID = pv.PRODUCT_ID
             JOIN
         ORDER_ITEM oi ON pv.ID = oi.VARIANT_ID
             JOIN
         APP_ORDER o ON oi.ORDER_ID = o.ID
    WHERE o.PURCHASED_AT >= startDate
      AND o.PURCHASED_AT <= endDate
      AND o.STATUS = 'COMPLETED'
    GROUP BY p.ID, pv.ID
#     HAVING SUM(oi.QUANTITY) > 0
    LIMIT size;
END;

CALL get_best_selling_products('2023-08-08', '2023-08-30', 10);

DROP PROCEDURE IF EXISTS get_slow_selling_products;
CREATE
    DEFINER = root@localhost PROCEDURE get_slow_selling_products(IN startDate date, IN endDate date, IN size int)
BEGIN
    SELECT p.ID AS productId
    FROM PRODUCT p
             JOIN
         PRODUCT_VARIANT pv ON p.ID = pv.PRODUCT_ID
             JOIN
         ORDER_ITEM oi ON pv.ID = oi.VARIANT_ID
             JOIN
         APP_ORDER o ON oi.ORDER_ID = o.ID
    WHERE o.PURCHASED_AT >= startDate
      AND o.PURCHASED_AT <= endDate
      AND o.STATUS = 'COMPLETED'
    GROUP BY p.ID, p.NAME
    HAVING SUM(oi.QUANTITY) > 0
    ORDER BY SUM(oi.PRICE * oi.QUANTITY) DESC
    LIMIT size;
END;


DROP PROCEDURE IF EXISTS get_discount_products;
CREATE
    DEFINER = root@localhost PROCEDURE get_discount_products()
BEGIN
    SELECT p.Id       AS id,
           p.DISCOUNT as discount
    FROM PRODUCT p
    WHERE p.ACTIVE = 1
      AND p.DISCOUNT > 0
    GROUP BY p.Id, p.DISCOUNT
    ORDER BY p.DISCOUNT DESC;
END;
CALL get_discount_products();


DROP PROCEDURE IF EXISTS get_latest_product;
CREATE
    DEFINER = root@localhost PROCEDURE get_latest_product(IN targetSize int)
BEGIN
    SELECT p.ID
    FROM PRODUCT p
    WHERE p.ACTIVE = 1
    GROUP BY p.ID
    ORDER BY p.ID DESC
    LIMIT targetSize;
END;
CALL get_latest_product(5);

DROP PROCEDURE IF EXISTS get_best_customer;
CREATE
    DEFINER = root@localhost PROCEDURE get_best_customer(IN targetSize int)
BEGIN
    SELECT u.id                 as id,
           COUNT(DISTINCT o.ID) as totalOrder
    FROM APP_USER u
             JOIN
         APP_ORDER o on u.ID = o.USER_ID
    WHERE o.STATUS = 'COMPLETED'
    GROUP BY u.id
    ORDER BY totalOrder DESC
    LIMIT targetSize;
END;
CALL get_best_customer(10);


DROP PROCEDURE IF EXISTS get_available_inventory;
CREATE
    DEFINER = root@localhost PROCEDURE get_available_inventory()
BEGIN
    SELECT
        pv.ID AS variant_id,
        get_available_quantity(pv.ID) AS quantity
    FROM
        PRODUCT_VARIANT pv
    GROUP BY pv.ID
    ORDER BY quantity DESC;
END;
CALL get_available_inventory();

# SELECT *
# FROM product_variant
# WHERE SKU = '28-TIV-ASU-ALI';

# ALTER table product_variant DROP CONSTRAINT UK_variant_sku;

DROP USER IF EXISTS 'fusiontech_user'@'%';

CREATE USER IF NOT EXISTS 'fusiontech_user'@'%' IDENTIFIED WITH mysql_native_password BY 'password';

GRANT ALL PRIVILEGES ON FUSIONTECH.* TO 'fusiontech_user'@'%';

FLUSH PRIVILEGES;
-- GRANT ALTER, ALTER ROUTINE, CREATE, CREATE ROUTINE, CREATE TEMPORARY TABLES, CREATE VIEW, DELETE, DROP, EVENT, EXECUTE, INDEX, INSERT, LOCK TABLES, REFERENCES, SELECT, SHOW VIEW, TRIGGER, UPDATE ON FUSIONTECH.* TO fusiontech_admin@'%';

USE FUSIONTECH;