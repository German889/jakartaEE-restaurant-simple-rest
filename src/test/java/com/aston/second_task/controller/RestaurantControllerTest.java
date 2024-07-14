package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.RestaurantDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.mapper.RestaurantMapper;
import com.aston.second_task.service.RestaurantService;
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
    void getRestaurant_NotFound() {
        Integer restaurantId = 1;
        when(restaurantService.findRestaurantById(restaurantId)).thenThrow(new ElementNotFoundExceptions("Restaurant not found"));
        Response response = restaurantController.getRestaurant(restaurantId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Restaurant not found", response.getEntity());
    }

    @Test
    void getRestaurant_InternalServerError() {
        Integer restaurantId = 1;
        when(restaurantService.findRestaurantById(restaurantId)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = restaurantController.getRestaurant(restaurantId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
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
    void getAllRestaurants_NotFound() {
        when(restaurantService.findAllRestaurants()).thenThrow(new ElementsNotFoundException("No restaurants found"));
        Response response = restaurantController.getAllRestaurants();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("No restaurants found", response.getEntity());
    }

    @Test
    void getAllRestaurants_InternalServerError() {
        when(restaurantService.findAllRestaurants()).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = restaurantController.getAllRestaurants();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
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
    void saveRestaurant_ElementNotSaved() {
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
        doThrow(new ElementNotSavedException("Error saving restaurant")).when(restaurantService).saveRestaurant(restaurant);
        Response response = restaurantController.saveRestaurant(restaurantDTOInc);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error saving restaurant", response.getEntity());
    }

    @Test
    void saveRestaurant_InternalServerError() {
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
        doThrow(new RuntimeException("Some unexpected error")).when(restaurantService).saveRestaurant(restaurant);
        Response response = restaurantController.saveRestaurant(restaurantDTOInc);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
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
    void updateRestaurant_ElementNotUpdated() {
        Integer restaurantId = 1;
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
        doThrow(new ElementNotUpdatedException("Error updating restaurant")).when(restaurantService).updateRestaurant(restaurant, restaurantId);
        Response response = restaurantController.updateRestaurant(restaurantDTOInc, restaurantId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error updating restaurant", response.getEntity());
    }

    @Test
    void updateRestaurant_InternalServerError() {
        Integer restaurantId = 1;
        RestaurantDTOInc restaurantDTOInc = new RestaurantDTOInc();
        Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
        doThrow(new RuntimeException("Some unexpected error")).when(restaurantService).updateRestaurant(restaurant, restaurantId);
        Response response = restaurantController.updateRestaurant(restaurantDTOInc, restaurantId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }

    @Test
    void deleteRestaurant() {
        Integer id = 1;

        Response response = restaurantController.deleteRestaurant(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(restaurantService, times(1)).deleteRestaurant(id);
    }
    @Test
    void deleteRestaurant_ElementNotDeleted() {
        Integer restaurantId = 1;
        doThrow(new ElementNotDeletedException("Error deleting restaurant")).when(restaurantService).deleteRestaurant(restaurantId);
        Response response = restaurantController.deleteRestaurant(restaurantId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error deleting restaurant", response.getEntity());
    }

    @Test
    void deleteRestaurant_InternalServerError() {
        Integer restaurantId = 1;
        doThrow(new RuntimeException("Some unexpected error")).when(restaurantService).deleteRestaurant(restaurantId);
        Response response = restaurantController.deleteRestaurant(restaurantId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }
}