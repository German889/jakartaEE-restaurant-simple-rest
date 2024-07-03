package com.aston.second_task.dto.incoming;

import java.util.Objects;

public class UserDTOInc {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String role;

    public UserDTOInc() {
    }
    public UserDTOInc(String firstName, String lastName, String email, String phone, String password, String address, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTOInc that = (UserDTOInc) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(password, that.password) && Objects.equals(address, that.address) && Objects.equals(role, that.role);
    }

    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phone, password, address, role);
    }
}
