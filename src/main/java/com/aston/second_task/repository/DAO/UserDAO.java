package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface UserDAO {
    User save(User user);
    User findById(Integer id);
    List<User> findAll();
    User update(User user);
    Integer delete(Integer id);
    Integer getId(User user);
    void setEntityManager(EntityManager em);
}
