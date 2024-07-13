package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
public class Restaurant {
    public Restaurant(){}

    private Restaurant(RestaurantBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.rating = builder.rating;
        this.email = builder.email;
        this.phone = builder.phone;
        this.workingHours = builder.workingHours;
        this.reviewOwners = builder.reviewOwners;
    }
    public static class RestaurantBuilder {
        private Integer id;
        private String name;
        private String address;
        private BigDecimal rating;
        private String email;
        private String phone;
        private String workingHours;
        private Set<AppUser> reviewOwners;

        public RestaurantBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public RestaurantBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RestaurantBuilder address(String address) {
            this.address = address;
            return this;
        }

        public RestaurantBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public RestaurantBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RestaurantBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public RestaurantBuilder workingHours(String workingHours) {
            this.workingHours = workingHours;
            return this;
        }

        public RestaurantBuilder reviewOwners(Set<AppUser> reviewOwners) {
            this.reviewOwners = reviewOwners;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String address;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;
    private String email;
    private String phone;
    private String workingHours;
    @ManyToMany
    @JoinTable(
            name = "review",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> reviewOwners;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public Set<AppUser> getReviewOwners() {
        return reviewOwners;
    }

    public void setReviewOwners(Set<AppUser> reviewOwners) {
        this.reviewOwners = reviewOwners;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(rating, that.rating) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(workingHours, that.workingHours);
    }

    public int hashCode() {
        return Objects.hash(id, name, address, rating, email, phone, workingHours);
    }
}
