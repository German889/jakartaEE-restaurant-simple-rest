package com.aston.second_task.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "couriers", schema = "public")
public class Courier {
    public Courier(){}

    public Courier(Integer id, User user, String vehicleRegistrationNumber, String vehicleModel, String status) {
        this.id = id;
        this.user = user;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courierid")
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

    @Column(name = "vehicleregistrationnumber")
    private String vehicleRegistrationNumber;

    @Column(name = "vehiclemodel")
    private String vehicleModel;

    @Column(name = "status")
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        Courier courier = (Courier) o;
        return Objects.equals(id, courier.id) && Objects.equals(user, courier.user) && Objects.equals(vehicleRegistrationNumber, courier.vehicleRegistrationNumber) && Objects.equals(vehicleModel, courier.vehicleModel) && Objects.equals(status, courier.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, vehicleRegistrationNumber, vehicleModel, status);
    }
}
