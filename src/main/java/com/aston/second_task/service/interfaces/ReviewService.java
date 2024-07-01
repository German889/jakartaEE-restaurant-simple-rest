package com.aston.second_task.service.interfaces;

import com.aston.second_task.entity.Review;

import java.util.List;

public interface ReviewService {
    void saveReview(Review review);
    Review findReviewById(Integer id);
    List<Review> findAllReviews();
    void updateReview(Review review, Integer id);
    void deleteReview(Integer id);
}