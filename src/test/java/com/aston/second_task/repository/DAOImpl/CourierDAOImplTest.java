package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Courier;
import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.CourierDAO;
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
public class CourierDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CourierDAO courierDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test", persistenceProperties());
        entityManager = entityManagerFactory.createEntityManager();
        courierDAO = new CourierDAOImpl();
        ((CourierDAOImpl) courierDAO).setEntityManager(entityManager);
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
        User user = new User(null, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "courier");
        Courier courier = new Courier(null, user, "ABC123", "Model X", "Available");

        entityManager.getTransaction().begin();
        courierDAO.save(courier);
        entityManager.getTransaction().commit();

        Courier foundCourier = courierDAO.findById(courier.getId());
        assertNotNull(foundCourier);
        assertEquals(courier.getVehicleRegistrationNumber(), foundCourier.getVehicleRegistrationNumber());
    }

    @Test
    public void testFindAll() {
        User user1 = new User(null, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "courier");
        Courier courier1 = new Courier(null, user1, "ABC123", "Model X", "Available");

        User user2 = new User(null, "Jane", "Doe", "jane.doe@example.com", "0987654321", "password", "456 Main St", "courier");
        Courier courier2 = new Courier(null, user2, "DEF456", "Model Y", "Busy");

        entityManager.getTransaction().begin();
        courierDAO.save(courier1);
        courierDAO.save(courier2);
        entityManager.getTransaction().commit();

        List<Courier> couriers = courierDAO.findAll();
        assertEquals(2, couriers.size());
    }

    @Test
    public void testUpdate() {
        User user = new User(null, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "courier");
        Courier courier = new Courier(null, user, "ABC123", "Model X", "Available");

        entityManager.getTransaction().begin();
        courierDAO.save(courier);
        entityManager.getTransaction().commit();

        courier.setVehicleRegistrationNumber("XYZ789");
        courier.setVehicleModel("Model Z");
        courier.setStatus("Busy");

        entityManager.getTransaction().begin();
        courierDAO.update(courier);
        entityManager.getTransaction().commit();

        Courier updatedCourier = courierDAO.findById(courier.getId());
        assertNotNull(updatedCourier);
        assertEquals("XYZ789", updatedCourier.getVehicleRegistrationNumber());
        assertEquals("Model Z", updatedCourier.getVehicleModel());
        assertEquals("Busy", updatedCourier.getStatus());
    }

    @Test
    public void testRemove() {
        User user = new User(null, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "courier");
        Courier courier = new Courier(null, user, "ABC123", "Model X", "Available");

        entityManager.getTransaction().begin();
        courierDAO.save(courier);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        courierDAO.remove(courier.getId());
        entityManager.getTransaction().commit();

        Courier removedCourier = courierDAO.findById(courier.getId());
        assertNull(removedCourier);
    }
}