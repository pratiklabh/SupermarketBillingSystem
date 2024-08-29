package com.syntech.sbs.repository;

import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.Sales_;
import com.syntech.sbs.model.User_;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

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

    public SalesRepository filterByCustomerId(Long customerId) {
        Predicate customerPredicate = criteriaBuilder.equal(root.get(Sales_.customer).get(User_.id), customerId);
        this.addPredicates(customerPredicate);
        return this;
    }

    public List<Sales> findByCustomerId(Long customerId) {
        try {
            return ((SalesRepository) this.startQuery())
                                     .filterByCustomerId(customerId)
                                     .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

}
