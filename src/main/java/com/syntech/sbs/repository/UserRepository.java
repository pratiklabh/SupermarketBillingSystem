package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import com.syntech.sbs.model.User_;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class UserRepository extends GenericRepository<User> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public UserRepository() {
        super(User.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

    public UserRepository filterByUsername(String username) {
        Predicate userNamePredicates = criteriaBuilder.equal(root.get(User_.username), username);
        this.addPredicates(userNamePredicates);
        return this;
    }

    public UserRepository filterByPassword(String password) {
        Predicate passwordPredicates = criteriaBuilder.equal(root.get(User_.password), password);
        this.addPredicates(passwordPredicates);
        return this;
    }

    public UserRepository filterByEmail(String email) {
        Predicate emailPredicate = criteriaBuilder.equal(root.get(User_.email), email);
        this.addPredicates(emailPredicate);
        return this;
    }

    public UserRepository filterByPhone(String phone) {
        Predicate phonePredicate = criteriaBuilder.equal(root.get(User_.phone), phone);
        this.addPredicates(phonePredicate);
        return this;
    }

    //authentication for login
    public User findByUsernameAndPassword(String username, String password) {
        return ((UserRepository) this.startQuery())
                                     .filterByUsername(username)
                                     .filterByPassword(password)
                                     .getSingleResult();
    }
    
    public User findByUsername(String username){
        return ((UserRepository) this.startQuery())
                                     .filterByUsername(username)
                                     .getSingleResult();
    }
    
    public User findByEmail(String email){
        return ((UserRepository) this.startQuery())
                                     .filterByEmail(email)
                                     .getSingleResult();
    }
    
    public User findByPhone(String phone){
        return ((UserRepository) this.startQuery())
                                     .filterByPhone(phone)
                                     .getSingleResult();
    }

    public List<User> getUsers(int first, int pageSize) {
        String query = "SELECT u FROM User u";
        return entityManager.createQuery(query, User.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public int countUsers(Map<String, FilterMeta> filters) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot));

        applyFilters(filters, countRoot, countQuery);

        return entityManager.createQuery(countQuery).getSingleResult().intValue();
    }

    public List<User> getUsers(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        applyFilters(filters, root, criteriaQuery);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private void applyFilters(Map<String, FilterMeta> filters, Root<User> root, CriteriaQuery<?> query) {
        Predicate[] predicates = filters.values().stream()
                .map(filter -> criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getFilterValue() + "%"))
                //similar to sql, where username like '%john%' ==> filter.getField=username , filter.getFilterValue=john
                .toArray(Predicate[]::new);
        query.where(predicates);
    }

}
