package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.AppUser;
import com.aston.second_task.repository.DAO.UserDAO;
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
public class AppUserDAOImplTest {

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
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(appUser);
        entityManager.getTransaction().commit();

        AppUser foundAppUser = userDAO.findById(appUser.getId());
        assertNotNull(foundAppUser);
        assertEquals(appUser.getEmail(), foundAppUser.getEmail());
    }

    @Test
    public void testFindAll() {
        AppUser appUser1 = new AppUser();
        appUser1.setEmail("test1@example.com");
        appUser1.setPassword("password1");

        AppUser appUser2 = new AppUser();
        appUser2.setEmail("test2@example.com");
        appUser2.setPassword("password2");

        entityManager.getTransaction().begin();
        userDAO.save(appUser1);
        userDAO.save(appUser2);
        entityManager.getTransaction().commit();

        List<AppUser> appUsers = userDAO.findAll();
        assertEquals(2, appUsers.size());
    }

    @Test
    public void testUpdate() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(appUser);
        entityManager.getTransaction().commit();

        appUser.setEmail("updated@example.com");

        entityManager.getTransaction().begin();
        userDAO.update(appUser);
        entityManager.getTransaction().commit();

        AppUser updatedAppUser = userDAO.findById(appUser.getId());
        assertNotNull(updatedAppUser);
        assertEquals("updated@example.com", updatedAppUser.getEmail());
    }

    @Test
    public void testDelete() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(appUser);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        userDAO.delete(appUser.getId());
        entityManager.getTransaction().commit();

        AppUser deletedAppUser = userDAO.findById(appUser.getId());
        assertNull(deletedAppUser);
    }

    @Test
    public void testGetId() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        entityManager.getTransaction().begin();
        userDAO.save(appUser);
        entityManager.getTransaction().commit();

        Integer id = userDAO.getId(appUser);
        assertNotNull(id);
        assertEquals(appUser.getId(), id);
    }
}