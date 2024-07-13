package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.dao.CourierDAO;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Courier;
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
public class CourierDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private CourierDAO courierDAO;
    private Connection connection;

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        courierDAO = new CourierDAOImpl();
        ((CourierDAOImpl) courierDAO).setConnection(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS courier");
            stmt.execute("DROP TABLE IF EXISTS app_user");

            stmt.execute("CREATE TABLE IF NOT EXISTS app_user (" +
                    "id SERIAL PRIMARY KEY, " +
                    "first_name VARCHAR(255) NOT NULL, " +
                    "last_name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL UNIQUE, " +
                    "phone VARCHAR(20), " +
                    "password VARCHAR(255) NOT NULL, " +
                    "address VARCHAR(255), " +
                    "role VARCHAR(50))");

            stmt.execute("CREATE TABLE IF NOT EXISTS courier (" +
                    "id SERIAL PRIMARY KEY, " +
                    "userid INTEGER REFERENCES app_user(id), " +
                    "vehicle_registration_number VARCHAR(255), " +
                    "vehicle_model VARCHAR(255), " +
                    "status VARCHAR(255));");


            stmt.execute("INSERT INTO app_user (first_name, last_name, email, phone, password, address, role) " +
                    "VALUES ('Giver', 'Doe', '92hg87@example.com', '123456djk', 'password1', '123 Main St', 'user');");
            stmt.execute("INSERT INTO app_user (first_name, last_name, email, phone, password, address, role) " +
                    "VALUES ('Jane', 'Smith', '56748347@example.com', '0987654321', 'password2', '456 Elm St', 'user');");
        }
    }

    @Test
    void testSaveAndFindById() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("testuser2");

        Courier courier = new Courier();
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber("ABC123");
        courier.setVehicleModel("Model X");
        courier.setStatus("Active");

        courierDAO.save(courier);

        Courier foundCourier = courierDAO.findById(1);
        assertNotNull(foundCourier);
        assertEquals("ABC123", foundCourier.getVehicleRegistrationNumber());
        assertEquals("Model X", foundCourier.getVehicleModel());
        assertEquals("Active", foundCourier.getStatus());
    }

    @Test
    void testFindAll() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("testuser");

        Courier courier1 = new Courier();
        courier1.setUser(appUser);
        courier1.setVehicleRegistrationNumber("ABC123");
        courier1.setVehicleModel("Model X");
        courier1.setStatus("Active");

        Courier courier2 = new Courier();
        courier2.setUser(appUser);
        courier2.setVehicleRegistrationNumber("DEF456");
        courier2.setVehicleModel("Model Y");
        courier2.setStatus("Inactive");

        courierDAO.save(courier1);
        courierDAO.save(courier2);

        List<Courier> couriers = courierDAO.findAll();
        assertEquals(2, couriers.size());
    }

    @Test
    void testUpdate() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("testuser");

        Courier courier = new Courier();
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber("ABC123");
        courier.setVehicleModel("Model X");
        courier.setStatus("Active");

        courierDAO.save(courier);
        courier.setId(1);
        courier.setStatus("Inactive");
        courierDAO.update(courier);

        Courier updatedCourier = courierDAO.findById(1);
        assertNotNull(updatedCourier);
        assertEquals("Inactive", updatedCourier.getStatus());
    }

    @Test
    void testRemove() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("testuser");

        Courier courier = new Courier();
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber("ABC123");
        courier.setVehicleModel("Model X");
        courier.setStatus("Active");

        courierDAO.save(courier);

        courierDAO.remove(1);

        Courier removedCourier = courierDAO.findById(1);
        assertNull(removedCourier);
    }
}