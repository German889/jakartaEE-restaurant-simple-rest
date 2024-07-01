package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.repository.DAO.RestaurantDAO;
import com.aston.second_task.service.interfaces.RestaurantService;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.List;

@ApplicationScoped
public class RestaurantServiceImpl implements RestaurantService {

    @Inject
    private RestaurantDAO restaurantDAO;

    @Resource
    private UserTransaction userTransaction;

    @Override
    @Transactional
    public void saveRestaurant(Restaurant restaurant) {
        restaurantDAO.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Integer id) {
        return restaurantDAO.findById(id);
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantDAO.findAll();
    }

    @Override
    @Transactional
    public void updateRestaurant(Restaurant restaurant, Integer id) {
        restaurant.setId(id);
        restaurantDAO.update(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Integer id) {
        restaurantDAO.delete(id);
    }

    @Override
    public Integer getRestaurantID(Restaurant restaurant){
        return restaurantDAO.getId(restaurant);
    }
}