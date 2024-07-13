package com.aston.second_task.dao;

import com.aston.second_task.entity.Courier;

import java.sql.Connection;
import java.util.List;

public interface CourierDAO {
    void save(Courier courier);
    Courier findById(Integer id);
    List<Courier> findAll();
    void update(Courier courier);
    void remove(Integer id);
    void setConnection(Connection connection);
}
