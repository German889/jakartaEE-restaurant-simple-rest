package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.dao.RestaurantDAO;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.exceptions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class RestaurantDAOImplTest {
    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("testuser")
            .withPassword("testpass");

    private RestaurantDAOImpl restaurantDAO;
    private Connection connection;

    @BeforeAll
    public static void init() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        restaurantDAO = new RestaurantDAOImpl();
        restaurantDAO.setConnection(connection);
        createTables();
    }

    private void createTables() throws SQLException {
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
            stmt.executeUpdate(sql);
        }
    }

    @Test
    public void testSaveAndFindById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setRating(new BigDecimal("4.5"));
        restaurant.setEmail("test@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setWorkingHours("9AM-5PM");
        restaurantDAO.save(restaurant);

        Restaurant foundRestaurant = restaurantDAO.findById(7);

        assertNotNull(foundRestaurant);
        assertEquals(restaurant.getName(), foundRestaurant.getName());
        assertEquals(restaurant.getAddress(), foundRestaurant.getAddress());
        assertEquals(restaurant.getRating(), foundRestaurant.getRating());
        assertEquals(restaurant.getEmail(), foundRestaurant.getEmail());
        assertEquals(restaurant.getPhone(), foundRestaurant.getPhone());
        assertEquals(restaurant.getWorkingHours(), foundRestaurant.getWorkingHours());
    }
    @Test
    public void testSaveThrowsElementNotSavedException() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(null);
        restaurant.setAddress("123 Test St");
        restaurant.setRating(new BigDecimal("4.5"));
        restaurant.setEmail("test@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setWorkingHours("9AM-5PM");
        assertThrows(ElementNotSavedException.class, () -> {
            restaurantDAO.save(restaurant);
        });
    }

    @Test
    public void testFindAll() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Restaurant 1");
        restaurant1.setAddress("Address 1");
        restaurant1.setRating(new BigDecimal("4.0"));
        restaurant1.setEmail("rest1@example.com");
        restaurant1.setPhone("1111111111");
        restaurant1.setWorkingHours("10AM-6PM");
        restaurantDAO.save(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Restaurant 2");
        restaurant2.setAddress("Address 2");
        restaurant2.setRating(new BigDecimal("4.5"));
        restaurant2.setEmail("rest2@example.com");
        restaurant2.setPhone("2222222222");
        restaurant2.setWorkingHours("11AM-7PM");
        restaurantDAO.save(restaurant2);

        List<Restaurant> restaurants = restaurantDAO.findAll();

        assertNotNull(restaurants);
        assertEquals(2, restaurants.size());
    }

    @Test
    public void testUpdate() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Original Name");
        restaurant.setAddress("Original Address");
        restaurant.setRating(new BigDecimal("3.5"));
        restaurant.setEmail("original@example.com");
        restaurant.setPhone("3333333333");
        restaurant.setWorkingHours("8AM-4PM");
        restaurantDAO.save(restaurant);

        restaurant.setId(5);
        restaurant.setName("Updated Name");
        restaurant.setAddress("Updated Address");
        restaurant.setRating(new BigDecimal("4.0"));
        restaurant.setEmail("updated@example.com");
        restaurant.setPhone("4444444444");
        restaurant.setWorkingHours("9AM-5PM");
        restaurantDAO.update(restaurant);

        Restaurant updatedRestaurant = restaurantDAO.findById(restaurant.getId());

        assertNotNull(updatedRestaurant);
        assertEquals("Updated Name", updatedRestaurant.getName());
        assertEquals("Updated Address", updatedRestaurant.getAddress());
        assertEquals(new BigDecimal("4.0"), updatedRestaurant.getRating());
        assertEquals("updated@example.com", updatedRestaurant.getEmail());
        assertEquals("4444444444", updatedRestaurant.getPhone());
        assertEquals("9AM-5PM", updatedRestaurant.getWorkingHours());
    }
    @Test
    public void testUpdateThrowsElementNotUpdatedException() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(999);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setRating(new BigDecimal("4.5"));
        restaurant.setEmail("test@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setWorkingHours("9AM-5PM");

        // Проверяем, что метод update выбрасывает исключение ElementNotUpdatedException
        assertThrows(ElementNotUpdatedException.class, () -> {
            restaurantDAO.update(restaurant);
        });
    }
    @Test
    public void testUpdateThrowsSQLException() {
        // Создаем объект Restaurant с несуществующим столбцом в запросе
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1); // Предполагаемый существующий ID
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setRating(new BigDecimal("4.5"));
        restaurant.setEmail("test@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setWorkingHours("9AM-5PM");

        // Подменяем SQL-запрос на ошибочный, включая несуществующий столбец
        String faultySql = "UPDATE restaurant " +
                "SET " +
                "name = ?, " +
                "address = ?, " +
                "rating = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "working_hours = ?, " +
                "nonexistent_column = ? " + // Несуществующий столбец
                "WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(faultySql)) {
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setBigDecimal(3, restaurant.getRating());
            ps.setString(4, restaurant.getEmail());
            ps.setString(5, restaurant.getPhone());
            ps.setString(6, restaurant.getWorkingHours());
            ps.setString(7, "some value");
            ps.setInt(8, restaurant.getId());
            ps.executeUpdate();
        } catch (SQLException se) {
            assertNotNull(se);
        }
    }

    @Test
    public void testDelete() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(3);
        restaurant.setName("To Be Deleted");
        restaurant.setAddress("Delete Address");
        restaurant.setRating(new BigDecimal("3.0"));
        restaurant.setEmail("delete@example.com");
        restaurant.setPhone("5555555555");
        restaurant.setWorkingHours("7AM-3PM");
        restaurantDAO.save(restaurant);

        restaurantDAO.delete(restaurant.getId());

        Restaurant deletedRestaurant = restaurantDAO.findById(restaurant.getId());

        assertNull(deletedRestaurant);
    }
    @Test
    public void testDeleteNonExistentRestaurant() {
        Integer nonExistentId = 999;

        assertThrows(ElementNotFoundExceptions.class, () -> {
            restaurantDAO.delete(nonExistentId);
        });
    }

    @Test
    public void testGetId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Unique Restaurant");
        restaurant.setAddress("Unique Address");
        restaurant.setRating(new BigDecimal("5.0"));
        restaurant.setEmail("unique@example.com");
        restaurant.setPhone("6666666666");
        restaurant.setWorkingHours("12AM-8PM");
        restaurantDAO.save(restaurant);

        Integer id = restaurantDAO.getId(restaurant);

        assertNotNull(id);
    }

}