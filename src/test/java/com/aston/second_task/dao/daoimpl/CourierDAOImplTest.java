package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.dao.CourierDAO;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.exceptions.ElementNotFoundExceptions;
import com.aston.second_task.exceptions.ElementNotSavedException;
import com.aston.second_task.exceptions.ElementsNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class CourierDAOImplTest {
    @Mock
    private ResultSet resultSet;

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
    void testSaveThrowsElementNotSavedException() {
        AppUser appUser = new AppUser();
        appUser.setId(999);
        appUser.setFirstName("testuser2");

        Courier courier = new Courier();
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber("ABC123");
        courier.setVehicleModel("Model X");
        courier.setStatus("Active");

        assertThrows(ElementNotSavedException.class, () -> {
            courierDAO.save(courier);
        });
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
    void testFindAllReturnsEmptyList() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM courier");
        } catch (SQLException e) {
            fail("Failed to delete records from courier table");
        }
        List<Courier> couriers = courierDAO.findAll();
        assertTrue(couriers.isEmpty());
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
    void testUpdateThrowsElementNotFoundExceptions() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("testuser");

        Courier courier = new Courier();
        courier.setId(999); // Несуществующий id
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber("ABC123");
        courier.setVehicleModel("Model X");
        courier.setStatus("Active");

        assertThrows(ElementNotFoundExceptions.class, () -> {
            courierDAO.update(courier);
        });
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
    @Test
    void testRemoveThrowsElementNotFoundExceptions() {
        Integer nonExistentId = 999; // Несуществующий id
        assertThrows(ElementNotFoundExceptions.class, () -> {
            courierDAO.remove(nonExistentId);
        });
    }

    @Test
    void testMapResultSetToCourier() throws SQLException {
        // Подготовка мок-объекта ResultSet
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("userid")).thenReturn(101);
        when(resultSet.getString("vehicle_registration_number")).thenReturn("ABC123");
        when(resultSet.getString("vehicle_model")).thenReturn("Model X");
        when(resultSet.getString("status")).thenReturn("Active");

        CourierDAOImpl courierDAO = new CourierDAOImpl();
        Courier courier = courierDAO.mapResultSetToCourier(resultSet);

        assertNotNull(courier);
        assertEquals(1, courier.getId());
        assertEquals(101, courier.getUser().getId());
        assertEquals("ABC123", courier.getVehicleRegistrationNumber());
        assertEquals("Model X", courier.getVehicleModel());
        assertEquals("Active", courier.getStatus());
    }
    @Test
    void testMapResultSetToCourierThrowsSQLException() throws SQLException {
        when(resultSet.getInt("id")).thenThrow(new SQLException("Column not found"));
        CourierDAOImpl courierDAO = new CourierDAOImpl();
        assertThrows(SQLException.class, () -> {
            courierDAO.mapResultSetToCourier(resultSet);
        });
    }
}