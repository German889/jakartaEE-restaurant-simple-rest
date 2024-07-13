package com.aston.second_task.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant.RestaurantBuilder()
                .id(2)
                .name("McDonald's")
                .address("101 Pine St")
                .rating(new BigDecimal("6.87"))
                .email("contact@mcdonalds.com")
                .phone("0987654321")
                .workingHours("8:00-22:00")
                .reviewOwners(new HashSet<>())
                .build();
        dish = new Dish();
        dish.setId(1);
        dish.setName("Whopper");
        dish.setDescription("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.");
        dish.setPrice("5.99");
        dish.setRestaurant(restaurant);
        dish.setImageURL("https://example.com/whopper.jpg");
    }

    @Test
    void getId() {
        assertEquals(1, dish.getId());
    }

    @Test
    void setId() {
        dish.setId(2);
        assertEquals(2, dish.getId());
    }

    @Test
    void getName() {
        assertEquals("Whopper", dish.getName());
    }

    @Test
    void setName() {
        dish.setName("Big Mac");
        assertEquals("Big Mac", dish.getName());
    }

    @Test
    void getDescription() {
        assertEquals("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.", dish.getDescription());
    }

    @Test
    void setDescription() {
        dish.setDescription("Two all-beef patties, special sauce, lettuce, cheese, pickles, onions on a sesame seed bun.");
        assertEquals("Two all-beef patties, special sauce, lettuce, cheese, pickles, onions on a sesame seed bun.", dish.getDescription());
    }

    @Test
    void getPrice() {
        assertEquals("5.99", dish.getPrice());
    }

    @Test
    void setPrice() {
        dish.setPrice("3.99");
        assertEquals("3.99", dish.getPrice());
    }

    @Test
    void getRestaurant() {
        assertEquals(restaurant, dish.getRestaurant());
    }

    @Test
    void setRestaurant() {
        Restaurant newRestaurant = new Restaurant.RestaurantBuilder()
                .id(2)
                .name("McDonald's")
                .address("101 Pine St")
                .rating(new BigDecimal("6.87"))
                .email("contact@mcdonalds.com")
                .phone("0987654321")
                .workingHours("8:00-22:00")
                .reviewOwners(new HashSet<>())
                .build();
        dish.setRestaurant(newRestaurant);
        assertEquals(newRestaurant, dish.getRestaurant());
    }

    @Test
    void getImageURL() {
        assertEquals("https://example.com/whopper.jpg", dish.getImageURL());
    }

    @Test
    void setImageURL() {
        dish.setImageURL("https://example.com/bigmac.jpg");
        assertEquals("https://example.com/bigmac.jpg", dish.getImageURL());
    }

    @Test
    void testEquals() {
        Dish dish1 = new Dish();
        dish1.setId(1);
        dish1.setName("Whopper");
        dish1.setDescription("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.");
        dish1.setPrice("5.99");
        dish1.setRestaurant(restaurant);
        dish1.setImageURL("https://example.com/whopper.jpg");

        Dish dish2 = new Dish();
        dish2.setId(1);
        dish2.setName("Whopper");
        dish2.setDescription("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.");
        dish2.setPrice("5.99");
        dish2.setRestaurant(restaurant);
        dish2.setImageURL("https://example.com/whopper.jpg");

        assertEquals(dish1, dish2);
    }

    @Test
    void testHashCode() {
        Dish dish1 = new Dish();
        dish1.setId(1);
        dish1.setName("Whopper");
        dish1.setDescription("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.");
        dish1.setPrice("5.99");
        dish1.setRestaurant(restaurant);
        dish1.setImageURL("https://example.com/whopper.jpg");

        Dish dish2 = new Dish();
        dish2.setId(1);
        dish2.setName("Whopper");
        dish2.setDescription("A flame-grilled burger with juicy tomatoes, crisp lettuce, creamy mayonnaise, ketchup, and sliced pickles.");
        dish2.setPrice("5.99");
        dish2.setRestaurant(restaurant);
        dish2.setImageURL("https://example.com/whopper.jpg");

        assertEquals(dish1.hashCode(), dish2.hashCode());
    }
}