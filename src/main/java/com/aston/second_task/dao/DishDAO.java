package com.aston.second_task.dao;

import com.aston.second_task.entity.Dish;

import java.sql.Connection;
import java.util.List;

public interface DishDAO {
    void save(Dish dish);
    Dish findById(Integer id);
    List<Dish> findAll();
    void update(Dish dish);
    void delete(Integer id);
    void setConnection(Connection connection);
}
