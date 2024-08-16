package com.syntech.sbs.repository;

import com.syntech.sbs.model.Supplier;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;

@Stateless
public class SupplierRepository extends GenericRepository<Supplier>{

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;
    
    public SupplierRepository(){
        super(Supplier.class);
    }
    @PostConstruct
    public void init() {
        setEntityManager(entityManager); // Set the EntityManager after construction
    }
    
    public Supplier findByPhone(String phone) {
        TypedQuery<Supplier> query = entityManager
                .createQuery("SELECT u FROM Supplier u WHERE u.phone = :phone", Supplier.class);
        query.setParameter("phone", phone);
        return query.getResultStream().findFirst().orElse(null);

    }
    
    public List<Supplier> getSuppliers(int first, int pageSize) {
        String query = "SELECT u FROM Supplier u"; 
        return entityManager.createQuery(query, Supplier.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public int countSuppliers(Map<String, FilterMeta> filters) {
        String query = "SELECT COUNT(u) FROM Supplier u";
        return ((Long) entityManager.createQuery(query).getSingleResult()).intValue();
    }

}
