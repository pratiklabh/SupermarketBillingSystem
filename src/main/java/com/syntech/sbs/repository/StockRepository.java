package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product_;
import com.syntech.sbs.model.Stock;
import com.syntech.sbs.model.Stock_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

@Stateless
public class StockRepository extends GenericRepository<Stock> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public StockRepository() {
        super(Stock.class);
    }

    @Override
    public void save(Stock stock) {
        if (stock.getId() == null) {
            entityManager.persist(stock);
        } else {
            entityManager.merge(stock);
        }
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

    public StockRepository filterByProductName(String productName) {
        Predicate namePredicate = criteriaBuilder.equal(root.get(Stock_.product).get(Product_.name), productName);
        this.addPredicates(namePredicate);
        return this;
    }

    public Stock findByProductName(String productName) {
        try {
            return ((StockRepository) this.startQuery())
                    .filterByProductName(productName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
