package com.aston.second_task.service;
import com.aston.second_task.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    void saveRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Integer id);
    List<Restaurant> findAllRestaurants();
    void updateRestaurant(Restaurant restaurant, Integer id);
    void deleteRestaurant(Integer id);
    Integer getRestaurantID(Restaurant restaurant);
}
