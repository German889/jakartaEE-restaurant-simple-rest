package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Courier {
    public Courier(){}

    public Courier(Integer id, AppUser appUser, String vehicleRegistrationNumber, String vehicleModel, String status) {
        this.id = id;
        this.appUser = appUser;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private AppUser appUser;
        private String vehicleRegistrationNumber;
    private String vehicleModel;
    private String status;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(id, courier.id) && Objects.equals(appUser, courier.appUser) && Objects.equals(vehicleRegistrationNumber, courier.vehicleRegistrationNumber) && Objects.equals(vehicleModel, courier.vehicleModel) && Objects.equals(status, courier.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUser, vehicleRegistrationNumber, vehicleModel, status);
    }
}
