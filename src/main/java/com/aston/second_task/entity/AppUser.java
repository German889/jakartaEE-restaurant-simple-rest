package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class AppUser {

    public AppUser(){}

    private AppUser(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.address = builder.address;
        this.role = builder.role;
        this.reviewedRestaurants = builder.reviewedRestaurants;
    }
    public static class Builder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
        private String address;
        private String role;
        private Set<Restaurant> reviewedRestaurants;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder reviewedRestaurants(Set<Restaurant> reviewedRestaurants) {
            this.reviewedRestaurants = reviewedRestaurants;
            return this;
        }

        public AppUser build() {
            return new AppUser(this);
        }

    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String role;

    @ManyToMany
    @JoinTable(
            name = "review",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<Restaurant> reviewedRestaurants;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Restaurant> getReviewedRestaurants() {
        return reviewedRestaurants;
    }

    public void setReviewedRestaurants(Set<Restaurant> reviewedRestaurants) {
        this.reviewedRestaurants = reviewedRestaurants;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(email, appUser.email) && Objects.equals(phone, appUser.phone) && Objects.equals(password, appUser.password) && Objects.equals(address, appUser.address) && Objects.equals(role, appUser.role);
    }

    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, password, address, role);
    }
}
