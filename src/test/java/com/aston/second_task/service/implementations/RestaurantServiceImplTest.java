package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.repository.DAO.RestaurantDAO;
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
    void saveRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        restaurantService.saveRestaurant(restaurant);

        verify(restaurantDAO, times(1)).save(restaurant);
    }

    @Test
    void findRestaurantById() {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Test Restaurant");

        when(restaurantDAO.findById(id)).thenReturn(restaurant);

        Restaurant foundRestaurant = restaurantService.findRestaurantById(id);

        assertNotNull(foundRestaurant);
        assertEquals(id, foundRestaurant.getId());
        verify(restaurantDAO, times(1)).findById(id);
    }

    @Test
    void findAllRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1);
        restaurant1.setName("Test Restaurant 1");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2);
        restaurant2.setName("Test Restaurant 2");

        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(restaurantDAO.findAll()).thenReturn(restaurants);

        List<Restaurant> foundRestaurants = restaurantService.findAllRestaurants();

        assertNotNull(foundRestaurants);
        assertEquals(2, foundRestaurants.size());
        verify(restaurantDAO, times(1)).findAll();
    }

    @Test
    void updateRestaurant() {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        restaurantService.updateRestaurant(restaurant, id);

        assertEquals(id, restaurant.getId());
        verify(restaurantDAO, times(1)).update(restaurant);
    }

    @Test
    void deleteRestaurant() {
        Integer id = 1;

        restaurantService.deleteRestaurant(id);

        verify(restaurantDAO, times(1)).delete(id);
    }

    @Test
    void getRestaurantID() {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress("123 Test St");
        restaurant.setEmail("test@example.com");
        Integer id = 1;

        when(restaurantDAO.getId(restaurant)).thenReturn(id);

        Integer foundId = restaurantService.getRestaurantID(restaurant);

        assertNotNull(foundId);
        assertEquals(id, foundId);
        verify(restaurantDAO, times(1)).getId(restaurant);
    }
}