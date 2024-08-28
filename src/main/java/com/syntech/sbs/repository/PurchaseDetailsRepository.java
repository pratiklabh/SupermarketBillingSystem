package com.syntech.sbs.repository;

import com.syntech.sbs.model.PurchaseDetails;
import com.syntech.sbs.model.PurchaseDetails_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

@Stateless
public class PurchaseDetailsRepository extends GenericRepository<PurchaseDetails> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public PurchaseDetailsRepository() {
        super(PurchaseDetails.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

    public PurchaseDetailsRepository filterByPurchaseId(Long id) {
        Predicate purchaseIdPredicate = criteriaBuilder.equal(root.get(PurchaseDetails_.purchase), id);
        this.addPredicates(purchaseIdPredicate);
        return this;
    }

    public List<PurchaseDetails> findByPurchaseId(Long id) {
        try {
            return ((PurchaseDetailsRepository) this.startQuery())
                    .filterByPurchaseId(id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
