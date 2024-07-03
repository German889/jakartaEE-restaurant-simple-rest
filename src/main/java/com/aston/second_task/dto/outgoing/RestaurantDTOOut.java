package com.aston.second_task.dto.outgoing;

import java.math.BigDecimal;
import java.util.Objects;

public class RestaurantDTOOut {
    private String name;
    private String address;
    private BigDecimal rating;

    public RestaurantDTOOut() {
    }

    public RestaurantDTOOut(String name, String address, BigDecimal rating) {
        this.name = name;
        this.address = address;
        this.rating = rating;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDTOOut that = (RestaurantDTOOut) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(rating, that.rating);
    }

    public int hashCode() {
        return Objects.hash(name, address, rating);
    }
}
