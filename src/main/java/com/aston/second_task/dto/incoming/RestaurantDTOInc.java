package com.aston.second_task.dto.incoming;

import java.math.BigDecimal;
import java.util.Objects;

public class RestaurantDTOInc {
    private String name;
    private String address;
    private BigDecimal rating;
    private String email;
    private String phone;
    private String workingHours;

    public RestaurantDTOInc() {
    }

    public RestaurantDTOInc(String name, String address, BigDecimal rating, String email, String phone, String workingHours) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.email = email;
        this.phone = phone;
        this.workingHours = workingHours;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDTOInc that = (RestaurantDTOInc) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(rating, that.rating) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(workingHours, that.workingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, rating, email, phone, workingHours);
    }
}
