package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.repository.DAO.RestaurantDAO;
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
public class RestaurantDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private RestaurantDAO restaurantDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test", persistenceProperties());
        entityManager = entityManagerFactory.createEntityManager();
        restaurantDAO = new RestaurantDAOImpl();
        ((RestaurantDAOImpl) restaurantDAO).setEntityManager(entityManager);
    }

    private Map<String, String> persistenceProperties() {
        return Map.of(
                "jakarta.persistence.jdbc.url", postgreSQLContainer.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgreSQLContainer.getUsername(),
                "jakarta.persistence.jdbc.password", postgreSQLContainer.getPassword()
        );
    }

    @Test
    public void testSaveAndFindById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("Test Address");
        restaurant.setEmail("test@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.save(restaurant);
        entityManager.getTransaction().commit();

        Restaurant foundRestaurant = restaurantDAO.findById(restaurant.getId());
        assertNotNull(foundRestaurant);
        assertEquals(restaurant.getName(), foundRestaurant.getName());
    }

    @Test
    public void testFindAll() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Test Restaurant 1");
        restaurant1.setAddress("Test Address 1");
        restaurant1.setEmail("test1@example.com");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Test Restaurant 2");
        restaurant2.setAddress("Test Address 2");
        restaurant2.setEmail("test2@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.save(restaurant1);
        restaurantDAO.save(restaurant2);
        entityManager.getTransaction().commit();

        List<Restaurant> restaurants = restaurantDAO.findAll();
        assertEquals(2, restaurants.size());
    }

    @Test
    public void testUpdate() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Initial Name");
        restaurant.setAddress("Initial Address");
        restaurant.setEmail("initial@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.save(restaurant);
        entityManager.getTransaction().commit();

        restaurant.setName("Updated Name");
        restaurant.setAddress("Updated Address");
        restaurant.setEmail("updated@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.update(restaurant);
        entityManager.getTransaction().commit();

        Restaurant updatedRestaurant = restaurantDAO.findById(restaurant.getId());
        assertNotNull(updatedRestaurant);
        assertEquals("Updated Name", updatedRestaurant.getName());
        assertEquals("Updated Address", updatedRestaurant.getAddress());
        assertEquals("updated@example.com", updatedRestaurant.getEmail());
    }

    @Test
    public void testDelete() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("To Be Deleted");
        restaurant.setAddress("Delete Address");
        restaurant.setEmail("delete@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.save(restaurant);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        restaurantDAO.delete(restaurant.getId());
        entityManager.getTransaction().commit();

        Restaurant deletedRestaurant = restaurantDAO.findById(restaurant.getId());
        assertNull(deletedRestaurant);
    }

    @Test
    public void testGetId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Unique Restaurant");
        restaurant.setAddress("Unique Address");
        restaurant.setEmail("unique@example.com");

        entityManager.getTransaction().begin();
        restaurantDAO.save(restaurant);
        entityManager.getTransaction().commit();

        Integer id = restaurantDAO.getId(restaurant);
        assertNotNull(id);
        assertEquals(restaurant.getId(), id);
    }
}