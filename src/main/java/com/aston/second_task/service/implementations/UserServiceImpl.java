package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.UserDAO;
import com.aston.second_task.service.interfaces.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Override
    @Transactional
    public User saveUser(User user) {
        try {
            logger.info("Saving user: {}", user);
            userDAO.save(user);
            return user;
        } catch (Exception e) {
            logger.error("Failed to save user: {}", user, e);
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public User findUserById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user, Integer id) {
        try {
            logger.info("Updating user: {}", user);
            user.setId(id);
            userDAO.update(user);
            return user;
        } catch (Exception e) {
            logger.error("Failed to update user: {}", user, e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer id) {
        try {
            logger.info("Deleting user: {}");
            userDAO.delete(id);
            return id;
        } catch (Exception e) {
            logger.error("Failed to delete user: {}",  e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }
    @Override
    public Integer getUserID(User user){
        return userDAO.getId(user);
    }
}