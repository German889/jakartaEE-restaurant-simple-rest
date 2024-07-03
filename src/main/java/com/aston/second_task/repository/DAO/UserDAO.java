package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.AppUser;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface UserDAO {
    AppUser save(AppUser appUser);
    AppUser findById(Integer id);
    List<AppUser> findAll();
    AppUser update(AppUser appUser);
    Integer delete(Integer id);
    Integer getId(AppUser appUser);
    void setEntityManager(EntityManager em);
}
