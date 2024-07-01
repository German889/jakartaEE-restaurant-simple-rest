package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.Restaurant;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface RestaurantDAO {
    void save(Restaurant restaurant);
    Restaurant findById(Integer id);
    List<Restaurant> findAll();
    void update(Restaurant restaurant);
    void delete(Integer id);
    Integer getId(Restaurant restaurant);
    void setEntityManager(EntityManager em);
}
