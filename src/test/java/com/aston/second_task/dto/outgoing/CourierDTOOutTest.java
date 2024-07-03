package com.aston.second_task.dto.outgoing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourierDTOOutTest {

    private CourierDTOOut courierDTOOut;
    private UserDTOOut userDTOOut;

    @BeforeEach
    void setUp() {
        userDTOOut = new UserDTOOut("John", "Doe", "john.doe@example.com");
        courierDTOOut = new CourierDTOOut(userDTOOut, "Honda Civic", "Available");
    }

    @Test
    void getUser() {
        assertEquals(userDTOOut, courierDTOOut.getUser());
    }

    @Test
    void setUser() {
        UserDTOOut newUserDTOOut = new UserDTOOut("Jane", "Doe", "jane.doe@example.com");
        courierDTOOut.setUser(newUserDTOOut);
        assertEquals(newUserDTOOut, courierDTOOut.getUser());
    }

    @Test
    void getVehicleModel() {
        assertEquals("Honda Civic", courierDTOOut.getVehicleModel());
    }

    @Test
    void setVehicleModel() {
        courierDTOOut.setVehicleModel("Toyota Corolla");
        assertEquals("Toyota Corolla", courierDTOOut.getVehicleModel());
    }

    @Test
    void getStatus() {
        assertEquals("Available", courierDTOOut.getStatus());
    }

    @Test
    void setStatus() {
        courierDTOOut.setStatus("Busy");
        assertEquals("Busy", courierDTOOut.getStatus());
    }

    @Test
    void testEquals() {
        CourierDTOOut courierDTOOut1 = new CourierDTOOut(userDTOOut, "Honda Civic", "Available");
        CourierDTOOut courierDTOOut2 = new CourierDTOOut(userDTOOut, "Honda Civic", "Available");
        assertEquals(courierDTOOut1, courierDTOOut2);
    }

    @Test
    void testHashCode() {
        CourierDTOOut courierDTOOut1 = new CourierDTOOut(userDTOOut, "Honda Civic", "Available");
        CourierDTOOut courierDTOOut2 = new CourierDTOOut(userDTOOut, "Honda Civic", "Available");
        assertEquals(courierDTOOut1.hashCode(), courierDTOOut2.hashCode());
    }
}