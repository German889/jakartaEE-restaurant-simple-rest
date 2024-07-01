package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.entity.User;

import java.util.Objects;

public class ReviewDTOInc {
    private User user;
    private Restaurant restaurant;

    public ReviewDTOInc() {
    }

    public ReviewDTOInc(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDTOInc that = (ReviewDTOInc) o;
        return Objects.equals(user, that.user) && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, restaurant);
    }
}