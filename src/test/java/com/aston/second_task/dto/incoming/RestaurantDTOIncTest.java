package com.aston.second_task.dto.incoming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDTOIncTest {

    private RestaurantDTOInc restaurantDTOInc;

    @BeforeEach
    void setUp() {
        restaurantDTOInc = new RestaurantDTOInc("Burger King", "456 Elm St", new BigDecimal("4.5"), "contact@burgerking.com", "1234567890", "9:00-21:00");
    }

    @Test
    void getName() {
        assertEquals("Burger King", restaurantDTOInc.getName());
    }

    @Test
    void setName() {
        restaurantDTOInc.setName("McDonald's");
        assertEquals("McDonald's", restaurantDTOInc.getName());
    }

    @Test
    void getAddress() {
        assertEquals("456 Elm St", restaurantDTOInc.getAddress());
    }

    @Test
    void setAddress() {
        restaurantDTOInc.setAddress("101 Pine St");
        assertEquals("101 Pine St", restaurantDTOInc.getAddress());
    }

    @Test
    void getRating() {
        assertEquals(new BigDecimal("4.5"), restaurantDTOInc.getRating());
    }

    @Test
    void setRating() {
        restaurantDTOInc.setRating(new BigDecimal("4.2"));
        assertEquals(new BigDecimal("4.2"), restaurantDTOInc.getRating());
    }

    @Test
    void getEmail() {
        assertEquals("contact@burgerking.com", restaurantDTOInc.getEmail());
    }

    @Test
    void setEmail() {
        restaurantDTOInc.setEmail("contact@mcdonalds.com");
        assertEquals("contact@mcdonalds.com", restaurantDTOInc.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("1234567890", restaurantDTOInc.getPhone());
    }

    @Test
    void setPhone() {
        restaurantDTOInc.setPhone("0987654321");
        assertEquals("0987654321", restaurantDTOInc.getPhone());
    }

    @Test
    void getWorkingHours() {
        assertEquals("9:00-21:00", restaurantDTOInc.getWorkingHours());
    }

    @Test
    void setWorkingHours() {
        restaurantDTOInc.setWorkingHours("8:00-22:00");
        assertEquals("8:00-22:00", restaurantDTOInc.getWorkingHours());
    }

    @Test
    void testEquals() {
        RestaurantDTOInc restaurantDTOInc1 = new RestaurantDTOInc("Burger King", "456 Elm St", new BigDecimal("4.5"), "contact@burgerking.com", "1234567890", "9:00-21:00");
        RestaurantDTOInc restaurantDTOInc2 = new RestaurantDTOInc("Burger King", "456 Elm St", new BigDecimal("4.5"), "contact@burgerking.com", "1234567890", "9:00-21:00");
        assertEquals(restaurantDTOInc1, restaurantDTOInc2);
    }

    @Test
    void testHashCode() {
        RestaurantDTOInc restaurantDTOInc1 = new RestaurantDTOInc("Burger King", "456 Elm St", new BigDecimal("4.5"), "contact@burgerking.com", "1234567890", "9:00-21:00");
        RestaurantDTOInc restaurantDTOInc2 = new RestaurantDTOInc("Burger King", "456 Elm St", new BigDecimal("4.5"), "contact@burgerking.com", "1234567890", "9:00-21:00");
        assertEquals(restaurantDTOInc1.hashCode(), restaurantDTOInc2.hashCode());
    }
}