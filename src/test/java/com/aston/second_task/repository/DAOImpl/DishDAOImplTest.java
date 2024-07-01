package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.repository.DAO.DishDAO;
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
public class DishDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private DishDAO dishDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test", persistenceProperties());
        entityManager = entityManagerFactory.createEntityManager();
        dishDAO = new DishDAOImpl();
        ((DishDAOImpl) dishDAO).setEntityManager(entityManager);
    }

    private Map<String, String> persistenceProperties() {
        return Map.of(
                "jakarta.persistence.jdbc.url", postgreSQLContainer.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgreSQLContainer.getUsername(),
                "jakarta.persistence.jdbc.password", postgreSQLContainer.getPassword()
        );
    }

    @Test
    public void testSave() {
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setPrice("10.0");

        entityManager.getTransaction().begin();
        dishDAO.save(dish);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindById() {
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setPrice("10.0");

        entityManager.getTransaction().begin();
        dishDAO.save(dish);
        entityManager.getTransaction().commit();

        Dish foundDish = dishDAO.findById(dish.getId());
        assertNotNull(foundDish);
        assertEquals(dish.getName(), foundDish.getName());
    }

    @Test
    public void testFindAll() {
        Dish dish1 = new Dish();
        dish1.setName("Test Dish 1");
        dish1.setPrice("10.0");

        Dish dish2 = new Dish();
        dish2.setName("Test Dish 2");
        dish2.setPrice("15.0");

        entityManager.getTransaction().begin();
        dishDAO.save(dish1);
        dishDAO.save(dish2);
        entityManager.getTransaction().commit();

        List<Dish> dishes = dishDAO.findAll();
        assertEquals(2, dishes.size());
    }

    @Test
    public void testUpdate() {
        Dish dish = new Dish();
        dish.setName("Initial Dish");
        dish.setPrice("10.0");

        entityManager.getTransaction().begin();
        dishDAO.save(dish);
        entityManager.getTransaction().commit();

        dish.setName("Updated Dish");
        dish.setPrice("20.0");

        entityManager.getTransaction().begin();
        dishDAO.update(dish);
        entityManager.getTransaction().commit();

        Dish updatedDish = dishDAO.findById(dish.getId());
        assertNotNull(updatedDish);
        assertEquals("Updated Dish", updatedDish.getName());
        assertEquals("20.0", updatedDish.getPrice());
    }

    @Test
    public void testDelete() {
        Dish dish = new Dish();
        dish.setName("To Be Deleted");
        dish.setPrice("10.0");

        entityManager.getTransaction().begin();
        dishDAO.save(dish);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        dishDAO.delete(dish.getId());
        entityManager.getTransaction().commit();

        Dish deletedDish = dishDAO.findById(dish.getId());
        assertNull(deletedDish);
    }
}