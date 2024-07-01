package com.aston.second_task.service.interfaces;

import com.aston.second_task.entity.Dish;

import java.util.List;

public interface DishService {
    void saveDish(Dish dish);
    Dish findDishById(Integer id);
    List<Dish> findAllDishes();
    void updateDish(Dish dish, Integer id);
    void deleteDish(Integer id);
}
