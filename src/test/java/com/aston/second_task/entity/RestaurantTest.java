package com.aston.second_task.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant.RestaurantBuilder()
                .id(1)
                .name("Burger King")
                .address("456 Elm St")
                .rating(new BigDecimal("4.5"))
                .email("contact@burgerking.com")
                .phone("1234567890")
                .workingHours("9:00-21:00")
                .reviewOwners(new HashSet<>())
                .build();
    }

    @Test
    void getId() {
        assertEquals(1, restaurant.getId());
    }

    @Test
    void setId() {
        restaurant.setId(2);
        assertEquals(2, restaurant.getId());
    }

    @Test
    void getName() {
        assertEquals("Burger King", restaurant.getName());
    }

    @Test
    void setName() {
        restaurant.setName("McDonald's");
        assertEquals("McDonald's", restaurant.getName());
    }

    @Test
    void getAddress() {
        assertEquals("456 Elm St", restaurant.getAddress());
    }

    @Test
    void setAddress() {
        restaurant.setAddress("101 Pine St");
        assertEquals("101 Pine St", restaurant.getAddress());
    }

    @Test
    void getRating() {
        assertEquals(new BigDecimal("4.5"), restaurant.getRating());
    }

    @Test
    void setRating() {
        restaurant.setRating(new BigDecimal("4.2"));
        assertEquals(new BigDecimal("4.2"), restaurant.getRating());
    }

    @Test
    void getEmail() {
        assertEquals("contact@burgerking.com", restaurant.getEmail());
    }

    @Test
    void setEmail() {
        restaurant.setEmail("contact@mcdonalds.com");
        assertEquals("contact@mcdonalds.com", restaurant.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("1234567890", restaurant.getPhone());
    }

    @Test
    void setPhone() {
        restaurant.setPhone("0987654321");
        assertEquals("0987654321", restaurant.getPhone());
    }

    @Test
    void getWorkingHours() {
        assertEquals("9:00-21:00", restaurant.getWorkingHours());
    }

    @Test
    void setWorkingHours() {
        restaurant.setWorkingHours("8:00-22:00");
        assertEquals("8:00-22:00", restaurant.getWorkingHours());
    }

    @Test
    void testEquals() {
        Restaurant restaurant1 = new Restaurant.RestaurantBuilder()
                .id(1)
                .name("Burger King")
                .address("456 Elm St")
                .rating(new BigDecimal("4.5"))
                .email("contact@burgerking.com")
                .phone("1234567890")
                .workingHours("9:00-21:00")
                .reviewOwners(new HashSet<>())
                .build();
        Restaurant restaurant2 = new Restaurant.RestaurantBuilder()
                .id(1)
                .name("Burger King")
                .address("456 Elm St")
                .rating(new BigDecimal("4.5"))
                .email("contact@burgerking.com")
                .phone("1234567890")
                .workingHours("9:00-21:00")
                .reviewOwners(new HashSet<>())
                .build();
        assertEquals(restaurant1, restaurant2);
    }

    @Test
    void testHashCode() {
        Restaurant restaurant1 = new Restaurant.RestaurantBuilder()
                .id(1)
                .name("Burger King")
                .address("456 Elm St")
                .rating(new BigDecimal("4.5"))
                .email("contact@burgerking.com")
                .phone("1234567890")
                .workingHours("9:00-21:00")
                .reviewOwners(new HashSet<>())
                .build();
        Restaurant restaurant2 = new Restaurant.RestaurantBuilder()
                .id(1)
                .name("Burger King")
                .address("456 Elm St")
                .rating(new BigDecimal("4.5"))
                .email("contact@burgerking.com")
                .phone("1234567890")
                .workingHours("9:00-21:00")
                .reviewOwners(new HashSet<>())
                .build();
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());
    }
}