package com.aston.second_task.dto.outgoing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserDTOOutTest {

    private UserDTOOut userDTOOut;

    @BeforeEach
    void setUp() {
        userDTOOut = new UserDTOOut("John", "Doe", "john.doe@example.com");
    }

    @Test
    void getFirstName() {
        assertEquals("John", userDTOOut.getFirstName());
    }

    @Test
    void setFirstName() {
        userDTOOut.setFirstName("Jane");
        assertEquals("Jane", userDTOOut.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", userDTOOut.getLastName());
    }

    @Test
    void setLastName() {
        userDTOOut.setLastName("Smith");
        assertEquals("Smith", userDTOOut.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", userDTOOut.getEmail());
    }

    @Test
    void setEmail() {
        userDTOOut.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", userDTOOut.getEmail());
    }

    @Test
    void testEquals() {
        UserDTOOut userDTOOut1 = new UserDTOOut("John", "Doe", "john.doe@example.com");
        UserDTOOut userDTOOut2 = new UserDTOOut("John", "Doe", "john.doe@example.com");
        assertEquals(userDTOOut1, userDTOOut2);
    }

    @Test
    void testHashCode() {
        UserDTOOut userDTOOut1 = new UserDTOOut("John", "Doe", "john.doe@example.com");
        UserDTOOut userDTOOut2 = new UserDTOOut("John", "Doe", "john.doe@example.com");
        assertEquals(userDTOOut1.hashCode(), userDTOOut2.hashCode());
    }
}