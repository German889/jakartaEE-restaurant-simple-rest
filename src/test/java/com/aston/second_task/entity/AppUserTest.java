package com.aston.second_task.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

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
    }

    @Test
    void getId() {
        assertEquals(1, appUser.getId());
    }

    @Test
    void setId() {
        appUser.setId(2);
        assertEquals(2, appUser.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("John", appUser.getFirstName());
    }

    @Test
    void setFirstName() {
        appUser.setFirstName("Jane");
        assertEquals("Jane", appUser.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", appUser.getLastName());
    }

    @Test
    void setLastName() {
        appUser.setLastName("Smith");
        assertEquals("Smith", appUser.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", appUser.getEmail());
    }

    @Test
    void setEmail() {
        appUser.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", appUser.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("1234567890", appUser.getPhone());
    }

    @Test
    void setPhone() {
        appUser.setPhone("0987654321");
        assertEquals("0987654321", appUser.getPhone());
    }

    @Test
    void getPassword() {
        assertEquals("password", appUser.getPassword());
    }

    @Test
    void setPassword() {
        appUser.setPassword("newpassword");
        assertEquals("newpassword", appUser.getPassword());
    }

    @Test
    void getAddress() {
        assertEquals("123 Main St", appUser.getAddress());
    }

    @Test
    void setAddress() {
        appUser.setAddress("456 Elm St");
        assertEquals("456 Elm St", appUser.getAddress());
    }

    @Test
    void getRole() {
        assertEquals("courier", appUser.getRole());
    }

    @Test
    void setRole() {
        appUser.setRole("admin");
        assertEquals("admin", appUser.getRole());
    }

    @Test
    void testEquals() {
        AppUser appUser1 = new AppUser.Builder()
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
        AppUser appUser2 = new AppUser.Builder()
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
        assertEquals(appUser1, appUser2);
    }

    @Test
    void testHashCode() {
        AppUser appUser1 = new AppUser.Builder()
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
        AppUser appUser2 = new AppUser.Builder()
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
        assertEquals(appUser1.hashCode(), appUser2.hashCode());
    }
}