package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.User;

import java.util.Objects;

public class CourierDTOInc {

    private User user;
    private String vehicleRegistrationNumber;
    private String vehicleModel;
    private String status;

    public CourierDTOInc() {
    }

    public CourierDTOInc(User user, String vehicleRegistrationNumber, String vehicleModel, String status) {

        this.user = user;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierDTOInc that = (CourierDTOInc) o;
        return Objects.equals(user, that.user) && Objects.equals(vehicleRegistrationNumber, that.vehicleRegistrationNumber) && Objects.equals(vehicleModel, that.vehicleModel) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, vehicleRegistrationNumber, vehicleModel, status);
    }
}