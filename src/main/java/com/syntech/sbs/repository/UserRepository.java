package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Stateless
public class UserRepository {

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;

    @Transactional
    public void save(User user){
        entityManager.persist(user);
    }

     public User getById(Long id){
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void update(User user){
        entityManager.merge(user);
    }

    @Transactional
    public void delete(Long id){
        User user = getById(id);
        if(user != null){
            entityManager.remove(user);
        }
    }

    //to get all users 
    public List<User> getAll(){
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
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