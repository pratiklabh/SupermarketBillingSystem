package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepo;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<User> criteriaQuery;
    private Root<User> root;
    private TypedQuery<User> typedQuery;  // Added TypedQuery mock

    @BeforeEach
    public void init() {
        entityManager = mock(EntityManager.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        criteriaQuery = mock(CriteriaQuery.class);
        root = mock(Root.class);
        typedQuery = mock(TypedQuery.class);  // Initialize TypedQuery mock

        userRepo = new UserRepository() {
            @Override
            protected EntityManager entityManager() {
                return entityManager;
            }
        };

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(User.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(User.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);  // Mock createQuery to return the TypedQuery

        // Manually invoke the startQuery method to initialize the query components
        userRepo.startQuery();

        // Mocking user entity and repository behavior
        User user1 = new User();
        user1.setUsername("pratik");
        user1.setPassword("pratik@123");
        user1.setEmail("pratik@example.com");
        user1.setPhone("1234567890");

        userRepo.save(user1);

        // Mock the behavior for getSingleResult to return the mock user
        when(typedQuery.getSingleResult()).thenReturn(user1);  // Return user1 when getSingleResult is called
    }

    @Test
    public void testFindByUsernameAndPassword() {
        User user = userRepo.findByUsernameAndPassword("pratik", "pratik@123");
        assertNotNull(user);
        assertEquals("pratik@example.com", user.getEmail());
    }
}
