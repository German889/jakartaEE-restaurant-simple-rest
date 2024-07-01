package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.RestaurantDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.service.interfaces.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRestaurant() {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("mcDonalds");

        RestaurantDTOOut restaurantDTOOut = new RestaurantDTOOut();
        restaurantDTOOut.setName("mcDonalds");

        when(restaurantService.findRestaurantById(id)).thenReturn(restaurant);

        Response response = restaurantController.getRestaurant(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(restaurantDTOOut, response.getEntity());
    }

    @Test
    void getAllRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1);
        restaurant1.setName("mcDonalds");
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2);
        restaurant2.setName("la rotonda");
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        RestaurantDTOOut restaurantDTOOut1 = new RestaurantDTOOut();
        restaurantDTOOut1.setName("mcDonalds");
        RestaurantDTOOut restaurantDTOOut2 = new RestaurantDTOOut();
        restaurantDTOOut2.setName("la rotonda");
        List<RestaurantDTOOut> restaurantDTOOuts = Arrays.asList(restaurantDTOOut1, restaurantDTOOut2);

        when(restaurantService.findAllRestaurants()).thenReturn(restaurants);

        Response response = restaurantController.getAllRestaurants();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(restaurantDTOOuts, response.getEntity());
    }

    @Test
    void saveRestaurant() {
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        restaurantDTOInc.setName("mcDonalds");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("mcDonalds");

        Response response = restaurantController.saveRestaurant(restaurantDTOInc);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(restaurantService, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void updateRestaurant() {
        Integer id = 1;
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        restaurantDTOInc.setName("mcDonalds");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("mcDonalds");

        Response response = restaurantController.updateRestaurant(restaurantDTOInc, id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(restaurantService, times(1)).updateRestaurant(restaurant, id);
    }

    @Test
    void deleteRestaurant() {
        Integer id = 1;

        Response response = restaurantController.deleteRestaurant(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(restaurantService, times(1)).deleteRestaurant(id);
    }
}