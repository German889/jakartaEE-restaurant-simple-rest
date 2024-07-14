package com.aston.second_task.service.implementations;

import com.aston.second_task.dao.daoimpl.CourierDAOImpl;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.dao.CourierDAO;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.service.CourierService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@ApplicationScoped
public class CourierServiceImpl implements CourierService {
    private static final Logger logger = LoggerFactory.getLogger(CourierServiceImpl.class);
    private CourierDAO courierDAO;
    @Inject
    public CourierServiceImpl(CourierDAO courierDAO) {
        this.courierDAO = courierDAO;
    }

    @Transactional
    public void saveCourier(Courier courier) {
        try {
            logger.info("Saving courier: {}", courier);
            courierDAO.save(courier);
        } catch (ElementNotSavedException e) {
            logger.error("Failed to save courier: {}", courier, e);
            throw new ElementNotSavedException("Courier not saved");
        }
    }

    public Courier findCourierById(Integer id) {
        try {
            logger.info("Finding courier by id: {}", id);
            return courierDAO.findById(id);
        } catch (ElementNotFoundExceptions e) {
            logger.error("Failed to find courier by id: {}", id, e);
            throw new ElementNotFoundExceptions("Courier with id " + id + " not found");
        }
    }

    public List<Courier> findAllCouriers() {
        try {
            logger.info("Finding all couriers");
            return courierDAO.findAll();
        } catch (ElementsNotFoundException e) {
            logger.error("Failed to find all couriers", e);
            throw new ElementsNotFoundException("No couriers found");
        }
    }

    @Transactional
    public void updateCourier(Courier courier, Integer id) {
        try {
            logger.info("Updating courier: {}", courier);
            courier.setId(id);
            courierDAO.update(courier);
        } catch (ElementNotUpdatedException e) {
            logger.error("Failed to update courier: {}", courier, e);
            throw new ElementNotUpdatedException("Courier not updated");
        }
    }

    @Transactional
    public void deleteCourier(Integer id) {
        try {
            logger.info("Deleting courier: {}", id);
            courierDAO.remove(id);
        } catch (ElementNotDeletedException e) {
            logger.error("Failed to delete courier: {}", id, e);
            throw new ElementNotDeletedException("Courier with id " + id + " not deleted");
        }
    }

}