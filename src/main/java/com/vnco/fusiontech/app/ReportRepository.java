package com.vnco.fusiontech.app;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;

//@Repository
public interface ReportRepository extends PagingAndSortingRepository<Object, Object> {

    @Procedure(procedureName = "TOP_SPENT_CUSTOMER")
    Object topSpentCustomer();
}
