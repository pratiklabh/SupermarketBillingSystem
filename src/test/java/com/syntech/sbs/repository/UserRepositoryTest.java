package com.syntech.sbs.repository;

import com.syntech.sbs.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepo;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<User> criteriaQuery;
    private Root<User> root;
    private TypedQuery<User> typedQuery;

    @BeforeEach
    public void init() {
        entityManager = mock(EntityManager.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        criteriaQuery = mock(CriteriaQuery.class);
        root = mock(Root.class);
        typedQuery = mock(TypedQuery.class);

        userRepo = new UserRepository() {
            @Override
            protected EntityManager entityManager() {
                return entityManager;
            }
        };

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(User.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(User.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        userRepo.startQuery();

        // Mock user entity for testing
        User user1 = new User("Pratik", "pratik", "pratik@123", "pratik@example.com", 
                "1234567890", "Admin", "ACTIVE");
        user1.setId(1L);

        // mocking save
        doNothing().when(entityManager).persist(any(User.class));

        // mocking findById
        when(entityManager.find(User.class, 1L)).thenReturn(user1);

        // mocking getSingleResult
        when(typedQuery.getSingleResult()).thenReturn(user1);
    }

//    @Test
//    public void testSave() {
//        User u = new User("Test", "test", "test@123", "test@gmail.com", "0987654321", "Admin", "Active");
//        u.setId(2L);
//        userRepo.save(u);
//        verify(entityManager, times(1)).persist(u);
//    }

    @Test
    public void testUpdate() {
        User u = new User("Test", "test", "test@123", "test@gmail.com", "0987654321", "Admin", "Active");
        u.setId(2L);
        userRepo.update(u);
        verify(entityManager, times(1)).merge(u);
    }

    @Test
    public void testDelete() {
        userRepo.delete(1L);
        verify(entityManager, times(1)).remove(any(User.class));
    }

    @Test
    public void testFindById() {
        User foundUser = userRepo.findById(1L);
        assertNotNull(foundUser);
        assertEquals("Pratik", foundUser.getName());
    }

    @Test
    public void testFindAll() {
        // Setup mock for findAll
        when(typedQuery.getResultList()).thenReturn(List.of(new User()));

        assertEquals(1, userRepo.findAll().size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
    }

//    @Test
//    public void testFindByUsernameAndPassword() {
//        User user = userRepo.findByUsernameAndPassword("pratik", "pratik@123");
//        assertNotNull(user);
//        assertEquals("pratik@example.com", user.getEmail());
//    }

    @Test
    public void testFindByUsername() {
        User user = userRepo.findByUsername("pratik");
        assertNotNull(user);
        assertEquals("1234567890", user.getPhone());
    }

    @Test
    public void testFindByPhone() {
        User user = userRepo.findByPhone("1234567890");
        assertNotNull(user);
        assertEquals("pratik", user.getUsername());
    }

    @Test
    public void testFindByEmail() {
        User user = userRepo.findByEmail("pratik@example.com");
        assertNotNull(user);
        assertEquals("1234567890", user.getPhone());
    }
}
