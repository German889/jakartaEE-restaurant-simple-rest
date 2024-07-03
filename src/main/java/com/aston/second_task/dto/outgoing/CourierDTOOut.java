package com.aston.second_task.dto.outgoing;

import java.util.Objects;

public class CourierDTOOut {
    private UserDTOOut user;
    private String vehicleModel;
    private String status;
    public CourierDTOOut(){}

    public CourierDTOOut(UserDTOOut user, String vehicleModel, String status) {
        this.user = user;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    public UserDTOOut getUser() {
        return user;
    }

    public void setUser(UserDTOOut user) {
        this.user = user;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierDTOOut that = (CourierDTOOut) o;
        return Objects.equals(user, that.user) && Objects.equals(vehicleModel, that.vehicleModel) && Objects.equals(status, that.status);
    }

    public int hashCode() {
        return Objects.hash(user, vehicleModel, status);
    }
}
