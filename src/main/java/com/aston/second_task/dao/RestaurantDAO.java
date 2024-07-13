package com.aston.second_task.dao;

import com.aston.second_task.entity.Restaurant;

import java.sql.Connection;
import java.util.List;

public interface RestaurantDAO {
    void save(Restaurant restaurant);
    Restaurant findById(Integer id);
    List<Restaurant> findAll();
    void update(Restaurant restaurant);
    void delete(Integer id);
    Integer getId(Restaurant restaurant);
    void setConnection(Connection connection);
}
