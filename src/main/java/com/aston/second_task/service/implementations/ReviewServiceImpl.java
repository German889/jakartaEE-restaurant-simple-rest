package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Review;
import com.aston.second_task.repository.DAO.ReviewDAO;
import com.aston.second_task.service.interfaces.ReviewService;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.List;

@ApplicationScoped
public class ReviewServiceImpl implements ReviewService {

    @Inject
    private ReviewDAO reviewDAO;


    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewDAO.save(review);
    }

    @Override
    public Review findReviewById(Integer id) {
        return reviewDAO.findById(id);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewDAO.findAll();
    }

    @Override
    @Transactional
    public void updateReview(Review review, Integer id) {
        review.setId(id);
        reviewDAO.update(review);
    }

    @Override
    @Transactional
    public void deleteReview(Integer id) {
        reviewDAO.delete(id);
    }
}