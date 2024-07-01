package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.ReviewDTOInc;
import com.aston.second_task.dto.outgoing.ReviewDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.entity.Review;
import com.aston.second_task.entity.User;
import com.aston.second_task.mapper.ReviewMapper;
import com.aston.second_task.service.interfaces.RestaurantService;
import com.aston.second_task.service.interfaces.ReviewService;
import com.aston.second_task.service.interfaces.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/reviews")
public class ReviewController {
    @Inject
    private ReviewService reviewService;
    @Inject
    private UserService userService;
    @Inject
    private RestaurantService restaurantService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("id") Integer id){
        Review review = reviewService.findReviewById(id);
        ReviewDTOOut reviewDTOOut = ReviewMapper.INSTANCE.reviewToReviewDTOOut(review);
        return Response.ok(reviewDTOOut).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReviews(){
        List<Review> reviews = reviewService.findAllReviews();
        List<ReviewDTOOut> reviewDTOOuts = reviews.stream()
                .map(ReviewMapper.INSTANCE::reviewToReviewDTOOut)
                .collect(Collectors.toList());
        return Response.ok(reviewDTOOuts).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveReview(ReviewDTOInc reviewDTOInc){
        try{
            Review review = ReviewMapper.INSTANCE.reviewDTOIncToReview(reviewDTOInc);
            User user = review.getUser();
            Restaurant restaurant = review.getRestaurant();
            user.setId(userService.getUserID(user));
            restaurant.setId(restaurantService.getRestaurantID(restaurant));
            review.setUser(user);
            review.setRestaurant(restaurant);
            reviewService.saveReview(review);
            return Response.ok("review saved").build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error saving review: "+ e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReview(ReviewDTOInc reviewDTOInc, @PathParam("id") Integer id){
        try{
            Review review = ReviewMapper.INSTANCE.reviewDTOIncToReview(reviewDTOInc);
            User user = review.getUser();
            Restaurant restaurant = review.getRestaurant();
            user.setId(userService.getUserID(user));
            restaurant.setId(restaurantService.getRestaurantID(restaurant));
            review.setUser(user);
            review.setRestaurant(restaurant);
            reviewService.updateReview(review, id);
            return Response.ok("review updated").build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating review: "+e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteReview(@PathParam("id") Integer id){
        try{
            reviewService.deleteReview(id);
            return Response.ok().build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting user: "+e.getMessage())
                    .build();
        }
    }
}
