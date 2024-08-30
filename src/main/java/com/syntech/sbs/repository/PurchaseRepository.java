package com.syntech.sbs.repository;

import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.model.Purchase_;
import com.syntech.sbs.model.User_;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

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
    public PurchaseRepository filterBySupplierId(Long supplierId) {
        Predicate supplierPredicate = criteriaBuilder.equal(root.get(Purchase_.supplier).get(User_.id), supplierId);
        this.addPredicates(supplierPredicate);
        return this;
    }

    public List<Purchase> findBySupplierId(Long supplierId) {
        try {
            return ((PurchaseRepository) this.startQuery())
                                     .filterBySupplierId(supplierId)
                                     .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
    
}
