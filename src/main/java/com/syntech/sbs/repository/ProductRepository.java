package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class ProductRepository {
    
    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    @Transactional
    public void save(Product product){
        entityManager.persist(product);
    }
    
    @Transactional
    public void update(Product product){
        entityManager.merge(product);
    }
    
    private Product getById(Long id){
        return entityManager.find(Product.class, id);
    }
    
    @Transactional
    public void delete(Long id){
        Product product = getById(id);
        if(product != null){
            entityManager.remove(product);
        }
    }
    
    public List<Product> getAll(){
        return entityManager.createQuery(
                "SELECT p FROM Product p", Product.class).getResultList();
    }
    
    public Product getByCode(Long code){
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
