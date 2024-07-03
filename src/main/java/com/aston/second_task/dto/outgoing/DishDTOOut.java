package com.aston.second_task.dto.outgoing;

import java.util.Objects;

public class DishDTOOut {
    private String name;
    private String price;
    private String imageURL;
    public DishDTOOut(){}

    public DishDTOOut(String name, String price, String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        DishDTOOut that = (DishDTOOut) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(imageURL, that.imageURL);
    }

    public int hashCode() {
        return Objects.hash(name, price, imageURL);
    }
}
