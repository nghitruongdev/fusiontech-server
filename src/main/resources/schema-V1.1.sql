ALTER TABLE PRODUCT_VARIANT
    MODIFY COLUMN ACTIVE BOOLEAN DEFAULT FALSE;

ALTER TABLE PRODUCT
    MODIFY COLUMN ACTIVE BOOLEAN DEFAULT FALSE;

ALTER TABLE VARIANT_INVENTORY_DETAIL
    DROP CONSTRAINT FK_inventory_detail_inventory;

alter table VARIANT_INVENTORY_DETAIL
    ADD constraint FK_inventory_detail_inventory
        foreign key (INVENTORY_ID) references PRODUCT_VARIANT_INVENTORY (ID)
            on update cascade on DELETE NO ACTION;

DELETE
FROM PRODUCT_VARIANT_INVENTORY
WHERE ID = 28;

DROP FUNCTION IF EXISTS GetProductSpecifications;

DELIMITER //
CREATE FUNCTION GetProductSpecifications(product_id INT)
    RETURNS JSON
    DETERMINISTIC
    NO SQL
BEGIN
    DECLARE result JSON;
    SET result = (SELECT JSON_ARRAYAGG(
                                 JSON_OBJECT(
                                         'VariantID', pv.ID,
                                         'Name', ps.NAME,
                                         'Value', ps.SPECIFICATION_VALUE
                                     )
                             )
                  FROM PRODUCT_VARIANT pv
                           JOIN VARIANT_SPECIFICATION vs ON pv.ID = vs.VARIANT_ID
                           JOIN PRODUCT_SPECIFICATION ps ON vs.SPECIFICATION_ID = ps.ID
                  WHERE pv.PRODUCT_ID = product_id);
    RETURN result;
END;
//
DELIMITER ;

SELECT GetProductSpecifications(1);

DROP FUNCTION IF EXISTS get_product_specification;
DELIMITER //
CREATE FUNCTION GetProductSpecifications(product_id INT)
    RETURNS JSON
    DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE result JSON;

    SELECT DISTINCT PS.NAME, GROUP_CONCAT()
    FROM PRODUCT_SPECIFICATION PS
             JOIN VARIANT_SPECIFICATION VS on PS.ID = VS.SPECIFICATION_ID
             JOIN PRODUCT_VARIANT PV on VS.VARIANT_ID = PV.ID;

    SET result = (SELECT JSON_ARRAYAGG(
                                 JSON_OBJECT(
                                         'VariantID', pv.ID,
                                         'Name', ps.NAME,
                                         'Value', ps.SPECIFICATION_VALUE
                                     )
                             )
                  FROM PRODUCT_VARIANT pv
                           JOIN VARIANT_SPECIFICATION vs ON pv.ID = vs.VARIANT_ID
                           JOIN PRODUCT_SPECIFICATION ps ON vs.SPECIFICATION_ID = ps.ID
                  WHERE pv.PRODUCT_ID = product_id);
    RETURN result;
END;
DELIMITER ;
