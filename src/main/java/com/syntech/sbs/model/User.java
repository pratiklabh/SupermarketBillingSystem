package com.syntech.sbs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")

public class User extends BaseIdEntity {

    @Column(nullable = false)
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Username is required.")
    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @NotBlank(message = "Email is required.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone is required.")
    @Column(unique = true, nullable = false)
    private String phone;

    @NotBlank(message = "Role is required.")
    @Column(nullable = false)
    private String role;

    @NotBlank(message = "Status is required.")
    @Column(nullable = false)
    private String status;

    public User() {
    }

    public User(String name, String username, String password, String email,
            String phone, String role, String status) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
