package com.aston.second_task.dto.incoming;

import com.aston.second_task.entity.AppUser;

import java.util.Objects;

public class CourierDTOInc {

    private AppUser appUser;
    private String vehicleRegistrationNumber;
    private String vehicleModel;
    private String status;

    public CourierDTOInc() {
    }

    public CourierDTOInc(AppUser appUser, String vehicleRegistrationNumber, String vehicleModel, String status) {

        this.appUser = appUser;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierDTOInc that = (CourierDTOInc) o;
        return Objects.equals(appUser, that.appUser) && Objects.equals(vehicleRegistrationNumber, that.vehicleRegistrationNumber) && Objects.equals(vehicleModel, that.vehicleModel) && Objects.equals(status, that.status);
    }

    public int hashCode() {
        return Objects.hash(appUser, vehicleRegistrationNumber, vehicleModel, status);
    }
}