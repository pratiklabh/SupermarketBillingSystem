package com.syntech.sbs.repository;

import com.syntech.sbs.model.PurchaseDetails;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PurchaseDetailsRepository extends GenericRepository<PurchaseDetails>{

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public PurchaseDetailsRepository() {
        super(PurchaseDetails.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

}
