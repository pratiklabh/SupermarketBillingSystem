package com.syntech.sbs.repository;

import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.model.SalesDetails_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

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
    
    public SalesDetailsRepository filterBySalesId(Long id) {
        Predicate salesIdPredicate = criteriaBuilder.equal(root.get(SalesDetails_.sales), id);
        this.addPredicates(salesIdPredicate);
        return this;
    }

    public List<SalesDetails> findBySalesId(Long id) {
        try {
            return ((SalesDetailsRepository) this.startQuery())
                    .filterBySalesId(id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
