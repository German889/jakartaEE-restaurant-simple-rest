package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
public class Restaurant {
    public Restaurant(){}

    public Restaurant(Integer id, String name, String address, BigDecimal rating, String email, String phone, String workingHours, Set<AppUser> reviewOwners) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.email = email;
        this.phone = phone;
        this.workingHours = workingHours;
        this.reviewOwners = reviewOwners;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String address;

    @Column(name = "rating", precision = 2, scale = 1)
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
