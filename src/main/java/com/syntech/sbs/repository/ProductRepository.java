package com.syntech.sbs.repository;

import com.syntech.sbs.model.Product;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;

@Stateless
public class ProductRepository extends GenericRepository<Product>{
    
    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public ProductRepository() {
        super(Product.class);
    }

    @Override
    public void save(Product entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Product entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT u FROM Product u", Product.class);
        
        return query.getResultList();
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

    public List<Product> getProducts(int first, int pageSize) {
        String query = "SELECT p FROM Product p";
        return entityManager.createQuery(query, Product.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
                
    }

    public int countProducts(Map<String, FilterMeta> filterBy) {
        String query = "SELECT COUNT(p) FROM Product p";
        return ((Long) entityManager.createQuery(query).getSingleResult()).intValue();
    }

}
