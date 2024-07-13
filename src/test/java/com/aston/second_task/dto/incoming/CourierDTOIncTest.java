package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CourierDTOIncTest {

    private CourierDTOInc courierDTOInc;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser.Builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .password("password")
                .address("123 Main St")
                .role("courier")
                .reviewedRestaurants(new HashSet<>())
                .build();
        courierDTOInc = new CourierDTOInc(appUser, "ABC123", "Honda Civic", "Available");
    }

    @Test
    void getUser() {
        assertEquals(appUser, courierDTOInc.getUser());
    }

    @Test
    void setUser() {
        AppUser newAppUser = new AppUser.Builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .password("password")
                .address("123 Main St")
                .role("courier")
                .reviewedRestaurants(new HashSet<>())
                .build();
        courierDTOInc.setUser(newAppUser);
        assertEquals(newAppUser, courierDTOInc.getUser());
    }

    @Test
    void getVehicleRegistrationNumber() {
        assertEquals("ABC123", courierDTOInc.getVehicleRegistrationNumber());
    }

    @Test
    void setVehicleRegistrationNumber() {
        courierDTOInc.setVehicleRegistrationNumber("XYZ789");
        assertEquals("XYZ789", courierDTOInc.getVehicleRegistrationNumber());
    }

    @Test
    void getVehicleModel() {
        assertEquals("Honda Civic", courierDTOInc.getVehicleModel());
    }

    @Test
    void setVehicleModel() {
        courierDTOInc.setVehicleModel("Toyota Corolla");
        assertEquals("Toyota Corolla", courierDTOInc.getVehicleModel());
    }

    @Test
    void getStatus() {
        assertEquals("Available", courierDTOInc.getStatus());
    }

    @Test
    void setStatus() {
        courierDTOInc.setStatus("Busy");
        assertEquals("Busy", courierDTOInc.getStatus());
    }

    @Test
    void testEquals() {
        CourierDTOInc courierDTOInc1 = new CourierDTOInc(appUser, "ABC123", "Honda Civic", "Available");
        CourierDTOInc courierDTOInc2 = new CourierDTOInc(appUser, "ABC123", "Honda Civic", "Available");
        assertEquals(courierDTOInc1, courierDTOInc2);
    }

    @Test
    void testHashCode() {
        CourierDTOInc courierDTOInc1 = new CourierDTOInc(appUser, "ABC123", "Honda Civic", "Available");
        CourierDTOInc courierDTOInc2 = new CourierDTOInc(appUser, "ABC123", "Honda Civic", "Available");
        assertEquals(courierDTOInc1.hashCode(), courierDTOInc2.hashCode());
    }
}