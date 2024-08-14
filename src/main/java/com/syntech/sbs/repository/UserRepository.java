package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserRepository extends GenericRepository<User> {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    public UserRepository() {
        super(User.class);
    }
    
    
    public User getByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public User findByUsername(String username){

        TypedQuery<User> query = entityManager.
                createQuery("SELECT u FROM User u WHERE u.username = :username",User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    public User findByEmail(String email){
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u from User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    public User findByPhone(String phone){
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u WHERE u.phone = :phone", User.class);
        query.setParameter("phone", phone);
        return query.getResultStream().findFirst().orElse(null);
    
    }
    
    //authentication for login
    public User findByUsernameAndPassword(String username, String password){
        TypedQuery<User> query = entityManager.
                createQuery("SELECT U from User u WHERE u.username = :username "
                        + "AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultStream().findFirst().orElse(null);
    }
    
//    public List<User> getUsers(int number){
//        String query = "SELECT u FROM User u";
//        List<User> users = entityManager.createQuery(query, User.class)
//                                        .setMaxResults(number)
//                                        .getResultList();
//        return users;
//    }

}