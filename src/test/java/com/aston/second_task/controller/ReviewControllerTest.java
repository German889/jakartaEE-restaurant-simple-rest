package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.ReviewDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.dto.outgoing.ReviewDTOOut;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.entity.Review;
import com.aston.second_task.entity.User;
import com.aston.second_task.service.interfaces.RestaurantService;
import com.aston.second_task.service.interfaces.ReviewService;
import com.aston.second_task.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReview() {
        Integer id = 1;
        Review review = new Review();
        review.setId(id);
        User user = new User();
        user.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        review.setUser(user);
        review.setRestaurant(restaurant);

        ReviewDTOOut reviewDTOOut = new ReviewDTOOut();
        reviewDTOOut.setUser(new UserDTOOut());
        reviewDTOOut.setRestaurant(new RestaurantDTOOut());

        when(reviewService.findReviewById(id)).thenReturn(review);

        Response response = reviewController.getReview(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(reviewDTOOut, response.getEntity());
    }

    @Test
    void testGetAllReviews() {
        Review review1 = new Review();
        review1.setId(1);
        User user1 = new User();
        user1.setId(1);
        review1.setUser(user1);
        Review review2 = new Review();
        review2.setId(2);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2);
        review2.setRestaurant(restaurant2);
        List<Review> reviews = Arrays.asList(review1, review2);

        ReviewDTOOut reviewDTOOut1 = new ReviewDTOOut();
        reviewDTOOut1.setUser(new UserDTOOut());
        ReviewDTOOut reviewDTOOut2 = new ReviewDTOOut();
        reviewDTOOut2.setRestaurant(new RestaurantDTOOut());
        List<ReviewDTOOut> reviewDTOOuts = Arrays.asList(reviewDTOOut1, reviewDTOOut2);

        when(reviewService.findAllReviews()).thenReturn(reviews);

        Response response = reviewController.getAllReviews();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(reviewDTOOuts, response.getEntity());
    }

    @Test
    void testSaveReview() {
        ReviewDTOInc reviewDTOInc = new ReviewDTOInc();
        User user = new User();
        Restaurant restaurant = new Restaurant();
        reviewDTOInc.setUser(user);
        reviewDTOInc.setRestaurant(restaurant);

        Review review = new Review();
        review.setUser(user);
        review.setRestaurant(restaurant);

        when(userService.getUserID(user)).thenReturn(1);
        when(restaurantService.getRestaurantID(restaurant)).thenReturn(1);

        Response response = reviewController.saveReview(reviewDTOInc);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("review saved", response.getEntity());
        verify(reviewService, times(1)).saveReview(review);
    }

    @Test
    void testUpdateReview() {
        Integer id = 1;
        ReviewDTOInc reviewDTOInc = new ReviewDTOInc();
        User user = new User();
        Restaurant restaurant = new Restaurant();
        reviewDTOInc.setUser(user);
        reviewDTOInc.setRestaurant(restaurant);

        Review review = new Review();
        review.setUser(user);
        review.setRestaurant(restaurant);

        when(userService.getUserID(user)).thenReturn(1);
        when(restaurantService.getRestaurantID(restaurant)).thenReturn(1);

        Response response = reviewController.updateReview(reviewDTOInc, id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("review updated", response.getEntity());
        verify(reviewService, times(1)).updateReview(review, id);
    }

    @Test
    void testDeleteReview() {
        Integer id = 1;

        Response response = reviewController.deleteReview(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(reviewService, times(1)).deleteReview(id);
    }
}