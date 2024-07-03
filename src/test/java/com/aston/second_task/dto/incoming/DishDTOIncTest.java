package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DishDTOIncTest {

    private DishDTOInc dishDTOInc;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1, "Burger King", "456 Elm St", new BigDecimal("7.87"), "contact@burgerking.com", "1234567890", "9:00-21:00", new HashSet<>());
        dishDTOInc = new DishDTOInc("Whopper", "A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", "5.99", restaurant, "https://example.com/whopper.jpg");
    }

    @Test
    void getName() {
        assertEquals("Whopper", dishDTOInc.getName());
    }

    @Test
    void setName() {
        dishDTOInc.setName("Big Mac");
        assertEquals("Big Mac", dishDTOInc.getName());
    }

    @Test
    void getDescription() {
        assertEquals("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", dishDTOInc.getDescription());
    }

    @Test
    void setDescription() {
        dishDTOInc.setDescription("Two all-beef patties, special sauce, lettuce, cheese, pickles, onions on a sesame seed bun.");
        assertEquals("Two all-beef patties, special sauce, lettuce, cheese, pickles, onions on a sesame seed bun.", dishDTOInc.getDescription());
    }

    @Test
    void getPrice() {
        assertEquals("5.99", dishDTOInc.getPrice());
    }

    @Test
    void setPrice() {
        dishDTOInc.setPrice("3.99");
        assertEquals("3.99", dishDTOInc.getPrice());
    }

    @Test
    void getRestaurant() {
        assertEquals(restaurant, dishDTOInc.getRestaurant());
    }

    @Test
    void setRestaurant() {
        Restaurant newRestaurant = new Restaurant(2, "McDonald's", "101 Pine St", new BigDecimal("6.87"), "contact@mcdonalds.com", "0987654321", "8:00-22:00", new HashSet<>());
        dishDTOInc.setRestaurant(newRestaurant);
        assertEquals(newRestaurant, dishDTOInc.getRestaurant());
    }

    @Test
    void getImageURL() {
        assertEquals("https://example.com/whopper.jpg", dishDTOInc.getImageURL());
    }

    @Test
    void setImageURL() {
        dishDTOInc.setImageURL("https://example.com/bigmac.jpg");
        assertEquals("https://example.com/bigmac.jpg", dishDTOInc.getImageURL());
    }

    @Test
    void testEquals() {
        DishDTOInc dishDTOInc1 = new DishDTOInc("Whopper", "A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", "5.99", restaurant, "https://example.com/whopper.jpg");
        DishDTOInc dishDTOInc2 = new DishDTOInc("Whopper", "A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", "5.99", restaurant, "https://example.com/whopper.jpg");
        assertEquals(dishDTOInc1, dishDTOInc2);
    }

    @Test
    void testHashCode() {
        DishDTOInc dishDTOInc1 = new DishDTOInc("Whopper", "A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", "5.99", restaurant, "https://example.com/whopper.jpg");
        DishDTOInc dishDTOInc2 = new DishDTOInc("Whopper", "A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", "5.99", restaurant, "https://example.com/whopper.jpg");
        assertEquals(dishDTOInc1.hashCode(), dishDTOInc2.hashCode());
    }
}