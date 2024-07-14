package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.dao.DishDAO;
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
    void saveDish_ElementNotSavedException() {
        Dish dish = new Dish();
        dish.setName("Test Dish");

        doThrow(new ElementNotSavedException("Error saving dish")).when(dishDAO).save(dish);

        Exception exception = assertThrows(ElementNotSavedException.class, () -> {
            dishService.saveDish(dish);
        });

        assertEquals("Dish not saved", exception.getMessage());
        verify(dishDAO, times(1)).save(dish);
    }

    @Test
    void findDishById_ElementNotFoundExceptions() {
        Integer id = 1;

        when(dishDAO.findById(id)).thenThrow(new ElementNotFoundExceptions("Dish not found"));

        Exception exception = assertThrows(ElementNotFoundExceptions.class, () -> {
            dishService.findDishById(id);
        });

        assertEquals("Dish with id " + id + " not found", exception.getMessage());
        verify(dishDAO, times(1)).findById(id);
    }

    @Test
    void findAllDishes_ElementsNotFoundException() {
        when(dishDAO.findAll()).thenThrow(new ElementsNotFoundException("No dishes found"));

        Exception exception = assertThrows(ElementsNotFoundException.class, () -> {
            dishService.findAllDishes();
        });

        assertEquals("No dishes found", exception.getMessage());
        verify(dishDAO, times(1)).findAll();
    }

    @Test
    void updateDish_ElementNotUpdatedException() {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setName("Test Dish");

        doThrow(new ElementNotUpdatedException("Error updating dish")).when(dishDAO).update(dish);

        Exception exception = assertThrows(ElementNotUpdatedException.class, () -> {
            dishService.updateDish(dish, id);
        });

        assertEquals("Dish not updated", exception.getMessage());
        verify(dishDAO, times(1)).update(dish);
    }

    @Test
    void deleteDish_ElementNotDeletedException() {
        Integer id = 1;

        doThrow(new ElementNotDeletedException("Error deleting dish")).when(dishDAO).delete(id);

        Exception exception = assertThrows(ElementNotDeletedException.class, () -> {
            dishService.deleteDish(id);
        });

        assertEquals("Dish with id " + id + " not deleted", exception.getMessage());
        verify(dishDAO, times(1)).delete(id);
    }
}