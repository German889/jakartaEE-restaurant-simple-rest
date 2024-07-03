package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.Restaurant;

import java.util.Objects;

public class DishDTOInc {
    private String name;
    private String description;
    private String price;
    private Restaurant restaurant;
    private String imageURL;

    public DishDTOInc() {
    }

    public DishDTOInc(String name, String description, String price, Restaurant restaurant, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishDTOInc that = (DishDTOInc) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(restaurant, that.restaurant) && Objects.equals(imageURL, that.imageURL);
    }

    public int hashCode() {
        return Objects.hash(name, description, price, restaurant, imageURL);
    }
}