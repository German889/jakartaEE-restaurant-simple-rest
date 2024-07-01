package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.repository.DAO.DishDAO;
import com.aston.second_task.service.interfaces.DishService;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.List;

@ApplicationScoped
public class DishServiceImpl implements DishService {

    @Inject
    private DishDAO dishDAO;

    @Resource
    private UserTransaction userTransaction;

    @Override
    @Transactional
    public void saveDish(Dish dish) {
        dishDAO.save(dish);
    }

    @Override
    public Dish findDishById(Integer id) {
        return dishDAO.findById(id);
    }

    @Override
    public List<Dish> findAllDishes() {
        return dishDAO.findAll();
    }

    @Override
    @Transactional
    public void updateDish(Dish dish, Integer id) {
        dish.setId(id);
        dishDAO.update(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Integer id) {
        dishDAO.delete(id);
    }
}