package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.DishDTOInc;
import com.aston.second_task.dto.outgoing.DishDTOOut;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Dish;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.service.interfaces.DishService;
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

class DishControllerTest {

    @Mock
    private DishService dishService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private DishController dishController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDish() {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName("Pizza");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("Italian Restaurant");
        dish.setRestaurant(restaurant);

        DishDTOOut dishDTOOut = new DishDTOOut();
        dishDTOOut.setName("Pizza");

        when(dishService.findDishById(id)).thenReturn(dish);

        Response response = dishController.getDish(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(dishDTOOut, response.getEntity());
    }

    @Test
    void getAllDishes() {
        Dish dish1 = new Dish();
        dish1.setId(1);
        dish1.setName("Pizza");
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1);
        restaurant1.setName("Italian Restaurant");
        dish1.setRestaurant(restaurant1);

        Dish dish2 = new Dish();
        dish2.setId(2);
        dish2.setName("Burger");
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2);
        restaurant2.setName("American Restaurant");
        dish2.setRestaurant(restaurant2);

        List<Dish> dishes = Arrays.asList(dish1, dish2);

        DishDTOOut dishDTOOut1 = new DishDTOOut();
        dishDTOOut1.setName("Pizza");

        DishDTOOut dishDTOOut2 = new DishDTOOut();
        dishDTOOut2.setName("Burger");

        List<DishDTOOut> dishDTOOuts = Arrays.asList(dishDTOOut1, dishDTOOut2);

        when(dishService.findAllDishes()).thenReturn(dishes);

        Response response = dishController.getAllDishes();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(dishDTOOuts, response.getEntity());
    }

    @Test
    void saveDish() {
        DishDTOInc dishDTOInc = new DishDTOInc();
        dishDTOInc.setName("Pizza");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Italian Restaurant");
        dishDTOInc.setRestaurant(restaurant);

        Dish dish = new Dish();
        dish.setName("Pizza");
        dish.setRestaurant(restaurant);

        when(restaurantService.getRestaurantID(restaurant)).thenReturn(1);

        Response response = dishController.saveDish(dishDTOInc);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(dishService, times(1)).saveDish(dish);
    }

    @Test
    void updateDish() {
        Integer id = 1;
        DishDTOInc dishDTOInc = new DishDTOInc();
        dishDTOInc.setName("Pizza");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Italian Restaurant");
        dishDTOInc.setRestaurant(restaurant);

        Dish dish = new Dish();
        dish.setName("Pizza");
        dish.setRestaurant(restaurant);

        when(restaurantService.getRestaurantID(restaurant)).thenReturn(1);

        Response response = dishController.updateDish(dishDTOInc, id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(dishService, times(1)).updateDish(dish, id);
    }

    @Test
    void deleteDish() {
        Integer id = 1;

        Response response = dishController.deleteDish(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(dishService, times(1)).deleteDish(id);
    }
}