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

    public User findByUsername(String username) {

        Predicate namePredicate = criteriaBuilder.equal(root.get(User_.username), username);
        criteriaQuery.where(namePredicate);

//        this.addPredicates(namePredicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();

    }

    public User findByEmail(String email) {
        Predicate emailPredicate = criteriaBuilder.equal(root.get(User_.email), email);
        criteriaQuery.where(emailPredicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public User findByPhone(String phone) {
        Predicate phonePredicate = criteriaBuilder.equal(root.get(User_.phone), phone);
        criteriaQuery.where(phonePredicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst()
                .orElse(null);

    }

    public List<User> findByRole(String role) {
        Predicate rolePredicate = criteriaBuilder.equal(root.get(User_.role), role);
        criteriaQuery.where(rolePredicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    public List<User> findByStatus(String status) {
        Predicate statusPredicate = criteriaBuilder.equal(root.get(User_.status), status);
        criteriaQuery.where(statusPredicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    //authentication for login
    public User findByUsernameAndPassword(String username, String password) {
        Predicate usernamePredicate = criteriaBuilder.equal(root.get(User_.username), username);
        Predicate passwordPredicate = criteriaBuilder.equal(root.get(User_.password), password);

        criteriaQuery.where(criteriaBuilder.and(usernamePredicate, passwordPredicate));

        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<User> getUsers(int first, int pageSize) {
        String query = "SELECT u FROM User u";
        return entityManager.createQuery(query, User.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

//    public int countUsers(Map<String, FilterMeta> filters) {
//        String query = "SELECT COUNT(u) FROM User u";
//        return ((Long) entityManager.createQuery(query).getSingleResult()).intValue();
//    }

    public int countUsers(Map<String, FilterMeta> filters) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot));

        applyFilters(filters, countRoot, countQuery);

        return entityManager.createQuery(countQuery).getSingleResult().intValue();
    }

    public List<User> getUsers(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        applyFilters(filters, root, criteriaQuery);

        // Apply sorting logic (if needed)
        // if (sortBy != null) { ... }
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private void applyFilters(Map<String, FilterMeta> filters, Root<User> root, CriteriaQuery<?> query) {
        Predicate[] predicates = filters.values().stream()
                .map(filter -> criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getFilterValue() + "%"))
                .toArray(Predicate[]::new);
        query.where(predicates);
    }

}
