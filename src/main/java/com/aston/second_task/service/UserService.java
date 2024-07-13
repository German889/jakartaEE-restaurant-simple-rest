package com.aston.second_task.service;

import com.aston.second_task.entity.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    AppUser findUserById(Integer id);
    List<AppUser> findAllUsers();
    AppUser updateUser(AppUser appUser, Integer id);
    Integer deleteUser(Integer id);
    Integer getUserID(AppUser appUser);
}