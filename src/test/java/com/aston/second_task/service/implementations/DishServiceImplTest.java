package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.dao.DishDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceImplTest {

    @Mock
    private DishDAO dishDAO;

    @InjectMocks
    private DishServiceImpl dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDish() {
        Dish dish = new Dish();
        dish.setName("Test Dish");

        dishService.saveDish(dish);

        verify(dishDAO, times(1)).save(dish);
    }

    @Test
    void findDishById() {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName("Test Dish");

        when(dishDAO.findById(id)).thenReturn(dish);

        Dish foundDish = dishService.findDishById(id);

        assertNotNull(foundDish);
        assertEquals(id, foundDish.getId());
        verify(dishDAO, times(1)).findById(id);
    }

    @Test
    void findAllDishes() {
        Dish dish1 = new Dish();
        dish1.setId(1);
        dish1.setName("Test Dish 1");

        Dish dish2 = new Dish();
        dish2.setId(2);
        dish2.setName("Test Dish 2");

        List<Dish> dishes = Arrays.asList(dish1, dish2);

        when(dishDAO.findAll()).thenReturn(dishes);

        List<Dish> foundDishes = dishService.findAllDishes();

        assertNotNull(foundDishes);
        assertEquals(2, foundDishes.size());
        verify(dishDAO, times(1)).findAll();
    }

    @Test
    void updateDish() {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setName("Test Dish");

        dishService.updateDish(dish, id);

        assertEquals(id, dish.getId());
        verify(dishDAO, times(1)).update(dish);
    }

    @Test
    void deleteDish() {
        Integer id = 1;

        dishService.deleteDish(id);

        verify(dishDAO, times(1)).delete(id);
    }
}