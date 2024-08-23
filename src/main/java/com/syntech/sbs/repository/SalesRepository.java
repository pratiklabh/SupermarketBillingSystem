package com.syntech.sbs.repository;

import com.syntech.sbs.model.Sales;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesRepository extends GenericRepository<Sales> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public SalesRepository() {
        super(Sales.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

}
