package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.dao.RestaurantDAO;
import com.aston.second_task.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    @Mock
    private RestaurantDAO restaurantDAO;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRestaurant_ElementNotSavedException() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        doThrow(new ElementNotSavedException("Error saving restaurant")).when(restaurantDAO).save(restaurant);

        Exception exception = assertThrows(ElementNotSavedException.class, () -> {
            restaurantService.saveRestaurant(restaurant);
        });

        assertEquals("Restaurant not saved", exception.getMessage());
        verify(restaurantDAO, times(1)).save(restaurant);
    }

    @Test
    void findRestaurantById_ElementNotFoundExceptions() {
        Integer id = 1;

        when(restaurantDAO.findById(id)).thenThrow(new ElementNotFoundExceptions("Restaurant not found"));

        Exception exception = assertThrows(ElementNotFoundExceptions.class, () -> {
            restaurantService.findRestaurantById(id);
        });

        assertEquals("Restaurant with id " + id + " not found", exception.getMessage());
        verify(restaurantDAO, times(1)).findById(id);
    }

    @Test
    void findAllRestaurants_ElementsNotFoundException() {
        when(restaurantDAO.findAll()).thenThrow(new ElementsNotFoundException("No restaurants found"));

        Exception exception = assertThrows(ElementsNotFoundException.class, () -> {
            restaurantService.findAllRestaurants();
        });

        assertEquals("No restaurants found", exception.getMessage());
        verify(restaurantDAO, times(1)).findAll();
    }

    @Test
    void updateRestaurant_ElementNotUpdatedException() {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        doThrow(new ElementNotUpdatedException("Error updating restaurant")).when(restaurantDAO).update(restaurant);

        Exception exception = assertThrows(ElementNotUpdatedException.class, () -> {
            restaurantService.updateRestaurant(restaurant, id);
        });

        assertEquals("Restaurant not updated", exception.getMessage());
        verify(restaurantDAO, times(1)).update(restaurant);
    }

    @Test
    void deleteRestaurant_ElementNotDeletedException() {
        Integer id = 1;

        doThrow(new ElementNotDeletedException("Error deleting restaurant")).when(restaurantDAO).delete(id);

        Exception exception = assertThrows(ElementNotDeletedException.class, () -> {
            restaurantService.deleteRestaurant(id);
        });

        assertEquals("Restaurant with id " + id + " not deleted", exception.getMessage());
        verify(restaurantDAO, times(1)).delete(id);
    }

    @Test
    void getRestaurantID_IdNotReceivedException() {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress("123 Test St");
        restaurant.setEmail("test@example.com");

        when(restaurantDAO.getId(restaurant)).thenThrow(new IdNotReceivedException("ID not received"));

        Exception exception = assertThrows(IdNotReceivedException.class, () -> {
            restaurantService.getRestaurantID(restaurant);
        });

        assertEquals("Restaurant ID not received", exception.getMessage());
        verify(restaurantDAO, times(1)).getId(restaurant);
    }
}