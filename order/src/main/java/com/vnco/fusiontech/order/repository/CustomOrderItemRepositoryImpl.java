package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.common.web.request.mail.OrderItemDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

public class CustomOrderItemRepositoryImpl implements CustomOrderItemRepository {
    @PersistenceContext
    EntityManager manager;
    
    @Override
    public List findOrderItemInfoIn(List<Long> ids) {
    var list =     manager.createNativeQuery("""
                                  SELECT * FROM order_item_info WHERE ID IN :ids
                                  """).setParameter("ids", ids)
                                                           .unwrap(NativeQuery.class)
                      .setTupleTransformer(new AliasToBeanResultTransformer<>(OrderItemDTO.class))
                                                           .getResultList();
    return list;
    
    }
}
