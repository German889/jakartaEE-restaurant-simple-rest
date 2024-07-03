package com.aston.second_task.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    private Courier courier;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser(1, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER", new HashSet<>());
        courier = new Courier(1, appUser, "ABC123", "Honda Civic", "Available");
    }

    @Test
    void getId() {
        assertEquals(1, courier.getId());
    }

    @Test
    void setId() {
        courier.setId(2);
        assertEquals(2, courier.getId());
    }

    @Test
    void getUser() {
        assertEquals(appUser, courier.getUser());
    }

    @Test
    void setUser() {
        AppUser newAppUser = new AppUser(2, "Jane", "Doe", "jane.doe@example.com", "0987654321", "password", "789 Oak St", "USER", new HashSet<>());
        courier.setUser(newAppUser);
        assertEquals(newAppUser, courier.getUser());
    }

    @Test
    void getVehicleRegistrationNumber() {
        assertEquals("ABC123", courier.getVehicleRegistrationNumber());
    }

    @Test
    void setVehicleRegistrationNumber() {
        courier.setVehicleRegistrationNumber("XYZ789");
        assertEquals("XYZ789", courier.getVehicleRegistrationNumber());
    }

    @Test
    void getVehicleModel() {
        assertEquals("Honda Civic", courier.getVehicleModel());
    }

    @Test
    void setVehicleModel() {
        courier.setVehicleModel("Toyota Corolla");
        assertEquals("Toyota Corolla", courier.getVehicleModel());
    }

    @Test
    void getStatus() {
        assertEquals("Available", courier.getStatus());
    }

    @Test
    void setStatus() {
        courier.setStatus("Busy");
        assertEquals("Busy", courier.getStatus());
    }

    @Test
    void testEquals() {
        Courier courier1 = new Courier(1, appUser, "ABC123", "Honda Civic", "Available");
        Courier courier2 = new Courier(1, appUser, "ABC123", "Honda Civic", "Available");
        assertEquals(courier1, courier2);
    }

    @Test
    void testHashCode() {
        Courier courier1 = new Courier(1, appUser, "ABC123", "Honda Civic", "Available");
        Courier courier2 = new Courier(1, appUser, "ABC123", "Honda Civic", "Available");
        assertEquals(courier1.hashCode(), courier2.hashCode());
    }
}