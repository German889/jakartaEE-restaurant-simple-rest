package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Review;
import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.ReviewDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveReview() {
        Review review = new Review();
        review.setUser(new User());

        reviewService.saveReview(review);

        verify(reviewDAO, times(1)).save(review);
    }

    @Test
    void findReviewById() {
        Integer id = 1;
        Review review = new Review();
        review.setId(id);
        review.setUser(new User());

        when(reviewDAO.findById(id)).thenReturn(review);

        Review foundReview = reviewService.findReviewById(id);

        assertNotNull(foundReview);
        assertEquals(id, foundReview.getId());
        verify(reviewDAO, times(1)).findById(id);
    }

    @Test
    void findAllReviews() {
        Review review1 = new Review();
        review1.setId(1);
        review1.setUser(new User());

        Review review2 = new Review();
        review2.setId(2);
        review2.setUser(new User());

        List<Review> reviews = Arrays.asList(review1, review2);

        when(reviewDAO.findAll()).thenReturn(reviews);

        List<Review> foundReviews = reviewService.findAllReviews();

        assertNotNull(foundReviews);
        assertEquals(2, foundReviews.size());
        verify(reviewDAO, times(1)).findAll();
    }

    @Test
    void updateReview() {
        Integer id = 1;
        Review review = new Review();
        review.setUser(new User());

        reviewService.updateReview(review, id);

        assertEquals(id, review.getId());
        verify(reviewDAO, times(1)).update(review);
    }

    @Test
    void deleteReview() {
        Integer id = 1;

        reviewService.deleteReview(id);

        verify(reviewDAO, times(1)).delete(id);
    }
}