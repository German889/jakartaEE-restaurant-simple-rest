package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.Courier;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface CourierDAO {
    void save(Courier courier);
    Courier findById(Integer id);
    List<Courier> findAll();
    void update(Courier courier);
    void remove(Integer id);
    void setEntityManager(EntityManager em);
}
