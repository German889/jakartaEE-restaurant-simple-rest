package com.aston.second_task.repository.DAO;

import com.aston.second_task.entity.Review;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface ReviewDAO {
    void save(Review review);
    Review findById(Integer id);
    List<Review> findAll();
    void update(Review review);
    void delete(Integer id);
    void setEntityManager(EntityManager em);
}
