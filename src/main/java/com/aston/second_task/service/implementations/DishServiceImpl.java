package com.aston.second_task.service.implementations;

import com.aston.second_task.dao.daoimpl.DishDAOImpl;
import com.aston.second_task.entity.Dish;
import com.aston.second_task.dao.DishDAO;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.service.DishService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@ApplicationScoped
public class DishServiceImpl implements DishService {
    private static final Logger logger = LoggerFactory.getLogger(DishServiceImpl.class);

    private DishDAO dishDAO;
    @Inject
    public DishServiceImpl(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }


    @Transactional
    public void saveDish(Dish dish) {
        try {
            logger.info("Saving dish: {}", dish);
            dishDAO.save(dish);
        } catch (ElementNotSavedException e) {
            logger.error("Failed to save dish: {}", dish, e);
            throw new ElementNotSavedException("Dish not saved");
        }
    }

    public Dish findDishById(Integer id) {
        try {
            logger.info("Finding dish by id: {}", id);
            return dishDAO.findById(id);
        } catch (ElementNotFoundExceptions e) {
            logger.error("Failed to find dish by id: {}", id, e);
            throw new ElementNotFoundExceptions("Dish with id " + id + " not found");
        }
    }

    public List<Dish> findAllDishes() {
        try {
            logger.info("Finding all dishes");
            return dishDAO.findAll();
        } catch (ElementsNotFoundException e) {
            logger.error("Failed to find all dishes", e);
            throw new ElementsNotFoundException("No dishes found");
        }
    }

    @Transactional
    public void updateDish(Dish dish, Integer id) {
        try {
            logger.info("Updating dish: {}", dish);
            dish.setId(id);
            dishDAO.update(dish);
        } catch (ElementNotUpdatedException e) {
            logger.error("Failed to update dish: {}", dish, e);
            throw new ElementNotUpdatedException("Dish not updated");
        }
    }

    @Transactional
    public void deleteDish(Integer id) {
        try {
            logger.info("Deleting dish: {}", id);
            dishDAO.delete(id);
        } catch (ElementNotDeletedException e) {
            logger.error("Failed to delete dish: {}", id, e);
            throw new ElementNotDeletedException("Dish with id " + id + " not deleted");
        }
    }
}