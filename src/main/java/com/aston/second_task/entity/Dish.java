package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "dishes", schema = "public")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dishid")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @ManyToOne
    @JoinColumn(name = "restaurantid")
    private Restaurant restaurant;

    @Column(name = "imageurl")
    private String imageURL;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description) && Objects.equals(price, dish.price) && Objects.equals(restaurant, dish.restaurant) && Objects.equals(imageURL, dish.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, restaurant, imageURL);
    }
}
