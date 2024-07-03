package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.AppUser;
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

    @Transactional
    public AppUser saveUser(AppUser appUser) {
        try {
            logger.info("Saving appUser: {}", appUser);
            userDAO.save(appUser);
            return appUser;
        } catch (Exception e) {
            logger.error("Failed to save appUser: {}", appUser, e);
            throw new RuntimeException("Failed to save appUser", e);
        }
    }

    public AppUser findUserById(Integer id) {
        return userDAO.findById(id);
    }

    public List<AppUser> findAllUsers() {
        return userDAO.findAll();
    }

    @Transactional
    public AppUser updateUser(AppUser appUser, Integer id) {
        try {
            logger.info("Updating appUser: {}", appUser);
            appUser.setId(id);
            userDAO.update(appUser);
            return appUser;
        } catch (Exception e) {
            logger.error("Failed to update appUser: {}", appUser, e);
            throw new RuntimeException("Failed to update appUser", e);
        }
    }

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
    public Integer getUserID(AppUser appUser){
        return userDAO.getId(appUser);
    }
}