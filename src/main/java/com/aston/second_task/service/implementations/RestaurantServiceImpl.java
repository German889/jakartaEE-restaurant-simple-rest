package com.aston.second_task.service.implementations;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.dao.RestaurantDAO;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.service.RestaurantService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@ApplicationScoped
public class RestaurantServiceImpl implements RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private RestaurantDAO restaurantDAO;

    @Inject
    public RestaurantServiceImpl(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @Transactional
    public void saveRestaurant(Restaurant restaurant) {
        try {
            logger.info("Saving restaurant: {}", restaurant);
            restaurantDAO.save(restaurant);
        } catch (ElementNotSavedException e) {
            logger.error("Failed to save restaurant: {}", restaurant, e);
            throw new ElementNotSavedException("Restaurant not saved");
        }
    }

    public Restaurant findRestaurantById(Integer id) {
        try {
            logger.info("Finding restaurant by id: {}", id);
            return restaurantDAO.findById(id);
        } catch (ElementNotFoundExceptions e) {
            logger.error("Failed to find restaurant by id: {}", id, e);
            throw new ElementNotFoundExceptions("Restaurant with id " + id + " not found");
        }
    }

    public List<Restaurant> findAllRestaurants() {
        try {
            logger.info("Finding all restaurants");
            return restaurantDAO.findAll();
        } catch (ElementsNotFoundException e) {
            logger.error("Failed to find all restaurants", e);
            throw new ElementsNotFoundException("No restaurants found");
        }
    }

    @Transactional
    public void updateRestaurant(Restaurant restaurant, Integer id) {
        try {
            logger.info("Updating restaurant: {}", restaurant);
            restaurant.setId(id);
            restaurantDAO.update(restaurant);
        } catch (ElementNotUpdatedException e) {
            logger.error("Failed to update restaurant: {}", restaurant, e);
            throw new ElementNotUpdatedException("Restaurant not updated");
        }
    }

    @Transactional
    public void deleteRestaurant(Integer id) {
        try {
            logger.info("Deleting restaurant: {}", id);
            restaurantDAO.delete(id);
        } catch (ElementNotDeletedException e) {
            logger.error("Failed to delete restaurant: {}", id, e);
            throw new ElementNotDeletedException("Restaurant with id " + id + " not deleted");
        }
    }

    public Integer getRestaurantID(Restaurant restaurant) {
        try {
            logger.info("Getting restaurant ID for restaurant: {}", restaurant);
            return restaurantDAO.getId(restaurant);
        } catch (IdNotReceivedException e) {
            logger.error("Failed to get restaurant ID for restaurant: {}", restaurant, e);
            throw new IdNotReceivedException("Restaurant ID not received");
        }
    }
}