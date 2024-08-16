package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.FilterMeta;

@Stateless
public class UserRepository extends GenericRepository<User> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

     public UserRepository() {
        super(User.class);
    }

    @PostConstruct
    public void init() {
        setEntityManager(entityManager); // Set the EntityManager after construction
    }
    
    public CriteriaQuery<User> criteriaQueryMethod(String fieldName, String value){
        //get criteria builder instance
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        //create CriteriaQuery object
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        
        //define the root
        Root<User> root = criteriaQuery.from(User.class);

        //select all attribute of root 
        Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }

    public User findByUsername(String username) {

        CriteriaQuery<User> criteriaQuery = criteriaQueryMethod("username", username);
        return entityManager.createQuery(criteriaQuery)
                            .getResultStream()
                            .findFirst()
                            .orElse(null);

    }

    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u from User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }

    public User findByPhone(String phone) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u WHERE u.phone = :phone", User.class);
        query.setParameter("phone", phone);
        return query.getResultStream().findFirst().orElse(null);

    }

    //authentication for login
    public User findByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = entityManager.
                createQuery("SELECT U from User u WHERE u.username = :username "
                        + "AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<User> getUsers(int first, int pageSize) {
        String query = "SELECT u FROM User u"; 
        return entityManager.createQuery(query, User.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public int countUsers(Map<String, FilterMeta> filters) {
        String query = "SELECT COUNT(u) FROM User u";
        return ((Long) entityManager.createQuery(query).getSingleResult()).intValue();
    }

}
