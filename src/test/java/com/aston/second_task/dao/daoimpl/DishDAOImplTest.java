package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.dao.DishDAO;
import com.aston.second_task.entity.Dish;
import com.aston.second_task.entity.Restaurant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DishDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private DishDAO dishDAO;
    private Connection connection;

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        dishDAO = new DishDAOImpl();
        ((DishDAOImpl) dishDAO).setConnection(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS restaurant " +
                            "(id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(255) NOT NULL, " +
                            "address VARCHAR(255) NOT NULL, " +
                            "rating NUMERIC(2,1), " +
                            "email VARCHAR(255), " +
                            "phone VARCHAR(255), " +
                            "working_hours VARCHAR(255));" +

                            "CREATE TABLE IF NOT EXISTS app_user " +
                            "(id SERIAL PRIMARY KEY, " +
                            "first_name VARCHAR(255) NOT NULL, " +
                            "last_name VARCHAR(255) NOT NULL, " +
                            "email VARCHAR(255) NOT NULL UNIQUE, " +
                            "phone VARCHAR(20), " +
                            "password VARCHAR(255) NOT NULL, " +
                            "address VARCHAR(255), " +
                            "role VARCHAR(50));" +

                            "CREATE TABLE IF NOT EXISTS dish " +
                            "(id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(255) NOT NULL, " +
                            "description TEXT, " +
                            "price VARCHAR(255) NOT NULL, " +
                            "restaurant_id INTEGER REFERENCES restaurant(id), " +
                            "imageURL TEXT);" +

                            "CREATE TABLE IF NOT EXISTS courier " +
                            "(id SERIAL PRIMARY KEY, " +
                            "user_id INTEGER UNIQUE REFERENCES app_user(id), " +
                            "vehicle_registration_number VARCHAR(255), " +
                            "vehicle_model VARCHAR(255), " +
                            "status VARCHAR(255));" +

                            "CREATE TABLE IF NOT EXISTS review " +
                            "(id SERIAL PRIMARY KEY, " +
                            "restaurant_id INTEGER REFERENCES restaurant(id), " +
                            "user_id INTEGER REFERENCES app_user(id));";
            stmt.execute(sql);
            String sql2 = "INSERT INTO restaurant (" +
                    "name, " +
                    "address, " +
                    "rating, " +
                    "email, " +
                    "phone, " +
                    "working_hours) " +
                    "VALUES('La Rotonda','Wide Street, 5','5.7','hello@h.h','74684643','9:00-18:00');";
            String sql3 = "INSERT INTO restaurant (" +
                    "name, " +
                    "address, " +
                    "rating, " +
                    "email, " +
                    "phone, " +
                    "working_hours) " +
                    "VALUES('Milano','Wide Street, 6','5.8','hello56@h.h','74784643','8:00-18:00');";
            stmt.execute(sql2+sql3);
        }
    }

    @Test
    void testSaveAndFindById() {
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setDescription("Test Description");
        dish.setPrice("10.00");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        dish.setRestaurant(restaurant);
        dish.setImageURL("http://example.com/image.jpg");

        dishDAO.save(dish);

        Dish foundDish = dishDAO.findById(3);
        assertNotNull(foundDish);
        assertEquals("Test Dish", foundDish.getName());
        assertEquals("10.00", foundDish.getPrice());
    }

    @Test
    void testFindAll() {
        Dish dish1 = new Dish();
        dish1.setName("Dish 1");
        dish1.setDescription("Description 1");
        dish1.setPrice("10.00");
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1);
        dish1.setRestaurant(restaurant1);
        dish1.setImageURL("http://example.com/image1.jpg");

        Dish dish2 = new Dish();
        dish2.setName("Dish 2");
        dish2.setDescription("Description 2");
        dish2.setPrice("20.00");
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2);
        dish2.setRestaurant(restaurant2);
        dish2.setImageURL("http://example.com/image2.jpg");

        dishDAO.save(dish1);
        dishDAO.save(dish2);

        List<Dish> dishes = dishDAO.findAll();
        assertEquals(2, dishes.size());
    }

    @Test
    void testUpdate() {
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setDescription("Test Description");
        dish.setPrice("10.00");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        dish.setRestaurant(restaurant);
        dish.setImageURL("http://example.com/image.jpg");

        dishDAO.save(dish);
        dish.setId(2);
        dish.setName("Updated Dish");
        dish.setPrice("15.00");
        dishDAO.update(dish);

        Dish updatedDish = dishDAO.findById(2);
        assertNotNull(updatedDish);
        assertEquals("Updated Dish", updatedDish.getName());
        assertEquals("15.00", updatedDish.getPrice());
    }

    @Test
    void testDelete() {
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setDescription("Test Description");
        dish.setPrice("10.00");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        dish.setRestaurant(restaurant);
        dish.setImageURL("http://example.com/image.jpg");

        dishDAO.save(dish);

        dishDAO.delete(1);

        Dish deletedDish = dishDAO.findById(1);
        assertNull(deletedDish);
    }
}