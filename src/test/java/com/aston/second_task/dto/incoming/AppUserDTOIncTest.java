package com.aston.second_task.dto.incoming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserDTOIncTest {

    private UserDTOInc userDTOInc;

    @BeforeEach
    void setUp() {
        userDTOInc = new UserDTOInc("John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER");
    }

    @Test
    void getFirstName() {
        assertEquals("John", userDTOInc.getFirstName());
    }

    @Test
    void setFirstName() {
        userDTOInc.setFirstName("Jane");
        assertEquals("Jane", userDTOInc.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", userDTOInc.getLastName());
    }

    @Test
    void setLastName() {
        userDTOInc.setLastName("Smith");
        assertEquals("Smith", userDTOInc.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", userDTOInc.getEmail());
    }

    @Test
    void setEmail() {
        userDTOInc.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", userDTOInc.getEmail());
    }

    @Test
    void getRole() {
        assertEquals("USER", userDTOInc.getRole());
    }

    @Test
    void setRole() {
        userDTOInc.setRole("ADMIN");
        assertEquals("ADMIN", userDTOInc.getRole());
    }

    @Test
    void getPhone() {
        assertEquals("1234567890", userDTOInc.getPhone());
    }

    @Test
    void setPhone() {
        userDTOInc.setPhone("0987654321");
        assertEquals("0987654321", userDTOInc.getPhone());
    }

    @Test
    void getPassword() {
        assertEquals("password", userDTOInc.getPassword());
    }

    @Test
    void setPassword() {
        userDTOInc.setPassword("newpassword");
        assertEquals("newpassword", userDTOInc.getPassword());
    }

    @Test
    void getAddress() {
        assertEquals("123 Main St", userDTOInc.getAddress());
    }

    @Test
    void setAddress() {
        userDTOInc.setAddress("456 Elm St");
        assertEquals("456 Elm St", userDTOInc.getAddress());
    }

    @Test
    void testEquals() {
        UserDTOInc userDTOInc1 = new UserDTOInc("John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER");
        UserDTOInc userDTOInc2 = new UserDTOInc("John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER");
        assertEquals(userDTOInc1, userDTOInc2);
    }

    @Test
    void testHashCode() {
        UserDTOInc userDTOInc1 = new UserDTOInc("John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER");
        UserDTOInc userDTOInc2 = new UserDTOInc("John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "USER");
        assertEquals(userDTOInc1.hashCode(), userDTOInc2.hashCode());
    }
}