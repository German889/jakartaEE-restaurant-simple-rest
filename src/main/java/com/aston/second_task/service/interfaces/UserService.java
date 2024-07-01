package com.aston.second_task.service.interfaces;

import com.aston.second_task.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserById(Integer id);
    List<User> findAllUsers();
    User updateUser(User user, Integer id);
    Integer deleteUser(Integer id);
    Integer getUserID(User user);
}