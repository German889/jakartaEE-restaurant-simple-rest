package com.aston.second_task.dto.outgoing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishDTOOutTest {

    private DishDTOOut dishDTOOut;

    @BeforeEach
    void setUp() {
        dishDTOOut = new DishDTOOut("Whopper", "5.99", "https://example.com/whopper.jpg");
    }

    @Test
    void getName() {
        assertEquals("Whopper", dishDTOOut.getName());
    }

    @Test
    void setName() {
        dishDTOOut.setName("Big Mac");
        assertEquals("Big Mac", dishDTOOut.getName());
    }

    @Test
    void getPrice() {
        assertEquals("5.99", dishDTOOut.getPrice());
    }

    @Test
    void setPrice() {
        dishDTOOut.setPrice("3.99");
        assertEquals("3.99", dishDTOOut.getPrice());
    }

    @Test
    void getImageURL() {
        assertEquals("https://example.com/whopper.jpg", dishDTOOut.getImageURL());
    }

    @Test
    void setImageURL() {
        dishDTOOut.setImageURL("https://example.com/bigmac.jpg");
        assertEquals("https://example.com/bigmac.jpg", dishDTOOut.getImageURL());
    }

    @Test
    void testEquals() {
        DishDTOOut dishDTOOut1 = new DishDTOOut("Whopper", "5.99", "https://example.com/whopper.jpg");
        DishDTOOut dishDTOOut2 = new DishDTOOut("Whopper", "5.99", "https://example.com/whopper.jpg");
        assertEquals(dishDTOOut1, dishDTOOut2);
    }

    @Test
    void testHashCode() {
        DishDTOOut dishDTOOut1 = new DishDTOOut("Whopper", "5.99", "https://example.com/whopper.jpg");
        DishDTOOut dishDTOOut2 = new DishDTOOut("Whopper", "5.99", "https://example.com/whopper.jpg");
        assertEquals(dishDTOOut1.hashCode(), dishDTOOut2.hashCode());
    }
}