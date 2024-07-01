package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.UserDAO;
import com.aston.second_task.repository.DAOImpl.UserDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test", Map.of(
                "jakarta.persistence.jdbc.url", postgreSQLContainer.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgreSQLContainer.getUsername(),
                "jakarta.persistence.jdbc.password", postgreSQLContainer.getPassword()
        ));
        entityManager = entityManagerFactory.createEntityManager();
        userDAO = new UserDAOImpl();
        ((UserDAOImpl) userDAO).setEntityManager(entityManager);
    }

    @Test
    public void testSaveAndFindById() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(user);
        entityManager.getTransaction().commit();

        User foundUser = userDAO.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setPassword("password2");

        entityManager.getTransaction().begin();
        userDAO.save(user1);
        userDAO.save(user2);
        entityManager.getTransaction().commit();

        List<User> users = userDAO.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(user);
        entityManager.getTransaction().commit();

        user.setEmail("updated@example.com");

        entityManager.getTransaction().begin();
        userDAO.update(user);
        entityManager.getTransaction().commit();

        User updatedUser = userDAO.findById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(user);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        userDAO.delete(user.getId());
        entityManager.getTransaction().commit();

        User deletedUser = userDAO.findById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    public void testGetId() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(user);
        entityManager.getTransaction().commit();

        Integer id = userDAO.getId(user);
        assertNotNull(id);
        assertEquals(user.getId(), id);
    }
}