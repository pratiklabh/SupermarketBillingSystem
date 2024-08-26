package com.syntech.sbs.repository;

import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.model.Supplier_;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import org.primefaces.model.FilterMeta;

@Stateless
public class SupplierRepository extends GenericRepository<Supplier> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public SupplierRepository() {
        super(Supplier.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

    public SupplierRepository filterByPhone(String phone) {
        Predicate phonePredicate = criteriaBuilder.equal(root.get(Supplier_.phone), phone);
        this.addPredicates(phonePredicate);
        return this;
    }

    public Supplier findByPhone(String phone) {
        try {
            return ((SupplierRepository) this.startQuery())
                    .filterByPhone(phone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no result is found
        }
    }


}
