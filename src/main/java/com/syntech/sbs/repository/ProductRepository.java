package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class ProductRepository extends GenericRepository<Product>{
    
    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public ProductRepository() {
        super(Product.class);
    }
  
    public Product findByCode(Long code){
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Product p WHERE p.code = :code", Product.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
