package com.syntech.sbs.repository;

import com.syntech.sbs.model.SalesDetails;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesDetailsRepository extends GenericRepository<SalesDetails> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public SalesDetailsRepository() {
        super(SalesDetails.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }
}
