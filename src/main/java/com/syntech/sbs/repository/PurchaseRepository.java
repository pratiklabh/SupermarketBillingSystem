package com.syntech.sbs.repository;

import com.syntech.sbs.model.Purchase;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PurchaseRepository extends GenericRepository<Purchase>{
    
    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;
    
    public PurchaseRepository() {
        super(Purchase.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }
    
    
}
