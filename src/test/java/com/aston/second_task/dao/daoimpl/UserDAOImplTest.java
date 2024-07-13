package com.aston.second_task.dao.daoimpl;
import com.aston.second_task.entity.AppUser;
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
public class UserDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("testuser")
            .withPassword("testpass");

    private UserDAOImpl userDAO;
    private Connection connection;

    @BeforeAll
    public static void init() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        userDAO = new UserDAOImpl();
        userDAO.setConnection(connection);
        createTables();
    }

    private void createTables() throws SQLException {
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
                        "price NUMERIC(10,2) NOT NULL, " +
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

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Test
    public void testSaveAndFindById() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("flysky@example.com");
        appUser.setPhone("1234567890");
        appUser.setPassword("password");
        appUser.setAddress("123 Main St");
        appUser.setRole("user");

        userDAO.save(appUser);
        assertNotNull(appUser.getId());

        AppUser foundUser = userDAO.findById(appUser.getId());
        assertNotNull(foundUser);
        assertEquals(appUser.getId(), foundUser.getId());
        assertEquals(appUser.getFirstName(), foundUser.getFirstName());
        assertEquals(appUser.getLastName(), foundUser.getLastName());
        assertEquals(appUser.getEmail(), foundUser.getEmail());
        assertEquals(appUser.getPhone(), foundUser.getPhone());
        assertEquals(appUser.getPassword(), foundUser.getPassword());
        assertEquals(appUser.getAddress(), foundUser.getAddress());
        assertEquals(appUser.getRole(), foundUser.getRole());
    }

    @Test
    public void testFindAll() {
        AppUser appUser1 = new AppUser();
        appUser1.setFirstName("John");
        appUser1.setLastName("Doe");
        appUser1.setEmail("john.doe@example.com");
        appUser1.setPhone("1234567890");
        appUser1.setPassword("password");
        appUser1.setAddress("123 Main St");
        appUser1.setRole("user");

        AppUser appUser2 = new AppUser();
        appUser2.setFirstName("Jane");
        appUser2.setLastName("Doe");
        appUser2.setEmail("jane.doe@example.com");
        appUser2.setPhone("0987654321");
        appUser2.setPassword("password");
        appUser2.setAddress("456 Main St");
        appUser2.setRole("user");

        userDAO.save(appUser1);
        userDAO.save(appUser2);

        List<AppUser> users = userDAO.findAll();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testUpdate() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("abobus@example.com");
        appUser.setPhone("1234567890");
        appUser.setPassword("password");
        appUser.setAddress("123 Main St");
        appUser.setRole("user");

        userDAO.save(appUser);
        assertNotNull(appUser.getId());

        appUser.setFirstName("UpdatedJohn");
        appUser.setLastName("UpdatedDoe");
        appUser.setEmail("updated.john.doe@example.com");
        appUser.setPhone("9876543210");
        appUser.setPassword("updatedPassword");
        appUser.setAddress("789 Main St");
        appUser.setRole("admin");

        userDAO.update(appUser);

        AppUser updatedUser = userDAO.findById(appUser.getId());
        assertNotNull(updatedUser);
        assertEquals(appUser.getId(), updatedUser.getId());
        assertEquals(appUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(appUser.getLastName(), updatedUser.getLastName());
        assertEquals(appUser.getEmail(), updatedUser.getEmail());
        assertEquals(appUser.getPhone(), updatedUser.getPhone());
        assertEquals(appUser.getPassword(), updatedUser.getPassword());
        assertEquals(appUser.getAddress(), updatedUser.getAddress());
        assertEquals(appUser.getRole(), updatedUser.getRole());
    }

    @Test
    public void testDelete() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("fibonacci@example.com");
        appUser.setPhone("1234567890");
        appUser.setPassword("password");
        appUser.setAddress("123 Main St");
        appUser.setRole("user");

        userDAO.save(appUser);
        assertNotNull(appUser.getId());

        userDAO.delete(appUser.getId());

        AppUser deletedUser = userDAO.findById(appUser.getId());
        assertNull(deletedUser);
    }

    @Test
    public void testGetId() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("shaurma.doe@example.com");
        appUser.setPhone("1234567890");
        appUser.setPassword("password");
        appUser.setAddress("123 Main St");
        appUser.setRole("user");

        userDAO.save(appUser);
        assertNotNull(appUser.getId());

        Integer foundId = userDAO.getId(appUser);
        assertNotNull(foundId);
        assertEquals(appUser.getId(), foundId);
    }
}