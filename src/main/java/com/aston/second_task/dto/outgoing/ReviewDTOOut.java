package com.aston.second_task.dto.outgoing;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.entity.User;

import java.util.Objects;

public class ReviewDTOOut {
    private UserDTOOut user;
    private RestaurantDTOOut restaurant;

    public ReviewDTOOut() {
    }

    public ReviewDTOOut(UserDTOOut user, RestaurantDTOOut restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public UserDTOOut getUser() {
        return user;
    }

    public void setUser(UserDTOOut user) {
        this.user = user;
    }

    public RestaurantDTOOut getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTOOut restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDTOOut that = (ReviewDTOOut) o;
        return Objects.equals(user, that.user) && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, restaurant);
    }
}