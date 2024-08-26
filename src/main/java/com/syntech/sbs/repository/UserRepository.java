package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import com.syntech.sbs.model.User_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

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
        try {
            return ((UserRepository) this.startQuery())
                                     .filterByUsername(username)
                                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    
    public User findByEmail(String email){
        try{
            return ((UserRepository) this.startQuery())
                                     .filterByEmail(email)
                                     .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
    
    public User findByPhone(String phone){
        try {
            return ((UserRepository) this.startQuery())
                                     .filterByPhone(phone)
                                     .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

}
