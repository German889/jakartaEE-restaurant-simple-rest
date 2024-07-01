package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.Dish;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface DishDAO {
    void save(Dish dish);
    Dish findById(Integer id);
    List<Dish> findAll();
    void update(Dish dish);
    void delete(Integer id);
    void setEntityManager(EntityManager em);
}
