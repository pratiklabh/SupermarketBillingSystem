package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import com.syntech.sbs.model.User_;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

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

    public User findByUsername(String username) {
        try {
            return ((UserRepository) this.startQuery())
                    .filterByUsername(username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public User findByEmail(String email) {
        try {
            return ((UserRepository) this.startQuery())
                    .filterByEmail(email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByPhone(String phone) {
        try {
            return ((UserRepository) this.startQuery())
                    .filterByPhone(phone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String hashPassword(String password, String salt) {
        try {
            // Initialize SHA-256 digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Update digest with salt
            digest.update(salt.getBytes());

            // Hash the password
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Return hashed password encoded in Base64
            return Base64.getEncoder().encodeToString(hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // RuntimeException if algorithm not found
        }
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt); // Generate random bytes
        return Base64.getEncoder().encodeToString(salt); // Return salt encoded in Base64
    }

    @Transactional
    @Override
    public void save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(user.getPassword(), salt);
            user.setPassword(hashedPassword + ":" + salt);
        }

        entityManager.persist(user);

    }

}
