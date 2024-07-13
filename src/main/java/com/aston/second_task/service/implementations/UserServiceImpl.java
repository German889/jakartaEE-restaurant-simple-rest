package com.aston.second_task.service.implementations;

import com.aston.second_task.dao.daoimpl.UserDAOImpl;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.dao.UserDAO;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }



    @Transactional
    public AppUser saveUser(AppUser appUser) {
        try {
            logger.info("Saving appUser: {}", appUser);
            userDAO.save(appUser);
            return appUser;
        } catch (ElementNotSavedException e) {
            logger.error("Failed to save appUser in service: {}", appUser, e);
            throw new ElementNotSavedException("User not saved");
        }
    }

    public AppUser findUserById(Integer id) {
        try {
            logger.info("Finding user by id: {}", id);
            return userDAO.findById(id);
        } catch (ElementNotFoundExceptions e) {
            logger.error("Failed to find user by id: {}", id, e);
            throw new ElementNotFoundExceptions("User with id "+id+" not found");
        }
    }

    public List<AppUser> findAllUsers() {
        try {
            logger.info("Finding all users");
            return userDAO.findAll();
        } catch (ElementsNotFoundException e) {
            logger.error("Failed to find all users", e);
            throw new ElementsNotFoundException("All users not found");
        }
    }

    @Transactional
    public AppUser updateUser(AppUser appUser, Integer id) {
        try {
            logger.info("Updating appUser: {}", appUser);
            appUser.setId(id);
            userDAO.update(appUser);
            return appUser;
        } catch (ElementNotUpdatedException e) {
            logger.error("Failed to update appUser: {}", appUser, e);
            throw new ElementNotUpdatedException("User not updated");
        }
    }

    @Transactional
    public Integer deleteUser(Integer id) {
        try {
            logger.info("Deleting user: {}", id);
            userDAO.delete(id);
            return id;
        } catch (ElementNotDeletedException e) {
            logger.error("Failed to delete user: {}", id, e);
            throw new ElementNotDeletedException("User with id "+id+" not deleted");
        }
    }

    public Integer getUserID(AppUser appUser) {
        try {
            logger.info("Getting user ID for user: {}", appUser);
            return userDAO.getId(appUser);
        } catch (IdNotReceivedException e) {
            logger.error("Failed to get user ID for user: {}", appUser, e);
            throw new IdNotReceivedException("Id of user not found");
        }
    }
}