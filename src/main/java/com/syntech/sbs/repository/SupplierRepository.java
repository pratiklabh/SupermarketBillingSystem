package com.syntech.sbs.repository;

import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.model.Supplier_;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import org.primefaces.model.FilterMeta;

@Stateless
public class SupplierRepository extends GenericRepository<Supplier>{

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;
    
    public SupplierRepository(){
        super(Supplier.class);
    }
    
    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }
    
    public SupplierRepository filterByPhone(String phone){
        Predicate phonePredicate = criteriaBuilder.equal(root.get(Supplier_.phone), phone);
        this.addPredicates(phonePredicate);
        return this;
    }
    
    public Supplier findByPhone(String phone) {
        
        return ((SupplierRepository) this.startQuery())
                                        .filterByPhone(phone)
                                        .getSingleResult();

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
