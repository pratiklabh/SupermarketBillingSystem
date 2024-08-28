package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pratik
 */
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserRepository userRepo;

    protected EntityManager entityManager() {
        return entityManager;
    }

    public void init() {

        User user1 = new User();
        user1.setUsername("pratik");
        user1.setPassword("pratik@123");
        user1.setEmail("pratik@example.com");
        user1.setPhone("1234567890");

        userRepo.save(user1);

    }

    @Test
    public void testFindByUsernameAndPassword() {
        User user = userRepo.findByUsernameAndPassword("pratik", "pratik@123");
        assertNotNull(user);
        assertEquals("pratik@example.com", user.getEmail());
    }
}
