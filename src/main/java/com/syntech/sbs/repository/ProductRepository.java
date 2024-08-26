package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.model.Product_;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import org.primefaces.model.FilterMeta;

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

    public ProductRepository filterByCode(String code){
        Predicate codePredicate = criteriaBuilder.equal(root.get(Product_.code), code);
        this.addPredicates(codePredicate);
        return this;
    }
    
    public Product findByCode(String code){
        try {
            return ((ProductRepository) this.startQuery())
                                     .filterByCode(code)
                                     .getSingleResult();
            
        } catch (NoResultException e) {
            return null;
        }
        
    }

}
