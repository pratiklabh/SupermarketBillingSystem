package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.model.Product_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

@Stateless
public class ProductRepository extends GenericRepository<Product> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public ProductRepository() {
        super(Product.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

    public ProductRepository filterByCode(String code) {
        Predicate codePredicate = criteriaBuilder.equal(root.get(Product_.code), code);
        this.addPredicates(codePredicate);
        return this;
    }

    public Product findByCode(String code) {
        try {
            return ((ProductRepository) this.startQuery())
                    .filterByCode(code)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

    }

    public ProductRepository filterByName(String name) {
        Predicate namePredicates = criteriaBuilder.equal(root.get(Product_.name), name);
        this.addPredicates(namePredicates);
        return this;
    }

    public Product findByName(String name) {
        try {
            return ((ProductRepository) this.startQuery())
                    .filterByName(name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

}
