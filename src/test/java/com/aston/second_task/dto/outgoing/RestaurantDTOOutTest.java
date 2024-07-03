package com.aston.second_task.dto.outgoing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDTOOutTest {

    private RestaurantDTOOut restaurantDTOOut;

    @BeforeEach
    void setUp() {
        restaurantDTOOut = new RestaurantDTOOut("Burger King", "456 Elm St", new BigDecimal("4.5"));
    }

    @Test
    void getName() {
        assertEquals("Burger King", restaurantDTOOut.getName());
    }

    @Test
    void setName() {
        restaurantDTOOut.setName("McDonald's");
        assertEquals("McDonald's", restaurantDTOOut.getName());
    }

    @Test
    void getAddress() {
        assertEquals("456 Elm St", restaurantDTOOut.getAddress());
    }

    @Test
    void setAddress() {
        restaurantDTOOut.setAddress("101 Pine St");
        assertEquals("101 Pine St", restaurantDTOOut.getAddress());
    }

    @Test
    void getRating() {
        assertEquals(new BigDecimal("4.5"), restaurantDTOOut.getRating());
    }

    @Test
    void setRating() {
        restaurantDTOOut.setRating(new BigDecimal("4.2"));
        assertEquals(new BigDecimal("4.2"), restaurantDTOOut.getRating());
    }

    @Test
    void testEquals() {
        RestaurantDTOOut restaurantDTOOut1 = new RestaurantDTOOut("Burger King", "456 Elm St", new BigDecimal("4.5"));
        RestaurantDTOOut restaurantDTOOut2 = new RestaurantDTOOut("Burger King", "456 Elm St", new BigDecimal("4.5"));
        assertEquals(restaurantDTOOut1, restaurantDTOOut2);
    }

    @Test
    void testHashCode() {
        RestaurantDTOOut restaurantDTOOut1 = new RestaurantDTOOut("Burger King", "456 Elm St", new BigDecimal("4.5"));
        RestaurantDTOOut restaurantDTOOut2 = new RestaurantDTOOut("Burger King", "456 Elm St", new BigDecimal("4.5"));
        assertEquals(restaurantDTOOut1.hashCode(), restaurantDTOOut2.hashCode());
    }
}