package com.aston.second_task.dao;

import com.aston.second_task.entity.AppUser;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {
    void setConnection(Connection connection);
    AppUser save(AppUser appUser);
    AppUser findById(Integer id);
    List<AppUser> findAll();
    AppUser update(AppUser appUser);
    Integer delete(Integer id);
    Integer getId(AppUser appUser);
}
