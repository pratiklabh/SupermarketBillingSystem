package com.syntech.sbs.repository;

import com.syntech.sbs.model.Stock;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StockRepository extends GenericRepository<Stock> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public StockRepository() {
        super(Stock.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

}
