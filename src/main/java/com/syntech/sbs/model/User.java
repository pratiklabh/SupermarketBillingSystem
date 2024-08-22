package com.syntech.sbs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")

public class User extends BaseIdEntity {

    @Column(nullable = false)
    @Size(min = 5, max = 30, message = "Full name should be at least 5 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name should be a valid string")
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Username is required.")
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 30, message = "Username should have at least 2 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should be a unique string")
    private String username;

    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
             message = "Password must be at least 6 characters long, containing at least one letter, one number, and one special character.")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone is required.")
    @Column(unique = true, nullable = false)
    @Size(min = 10, max = 10, message = "Phone number should be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be a 10-digit number")
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
