USE fusiontech;

-- get revenue from that every years
DROP PROCEDURE IF EXISTS get_revenue;
CREATE
    DEFINER = root@localhost PROCEDURE get_revenue()
BEGIN
    SELECT COUNT(o.ID)                 AS totalSales,
           SUM(oi.PRICE * oi.QUANTITY) AS totalRevenue,
           AVG(oi.PRICE)               AS averagePrice,
           YEAR(o.PURCHASED_AT)        as saleYear
    FROM APP_ORDER o
             JOIN
         ORDER_ITEM oi ON o.ID = oi.ORDER_ID
    GROUP BY saleYear;
END;

-- GET MOST SELLING PRODUCTS BY DATE RANGE
DROP PROCEDURE IF EXISTS get_best_selling_products;
CREATE
    DEFINER = root@localhost PROCEDURE get_best_selling_products(IN startDate date, IN endDate date, IN size int)
BEGIN
    SELECT
        p.ID AS id
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
    GROUP BY p.ID
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
    SELECT
        p.Id AS id,
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
    SELECT
        p.ID
    FROM PRODUCT p
    GROUP BY p.ID
    ORDER BY p.ID DESC
    LIMIT targetSize;
END;
CALL get_latest_product(5);
