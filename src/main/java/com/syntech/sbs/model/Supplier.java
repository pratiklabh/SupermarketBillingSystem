package com.syntech.sbs.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseIdEntity {

    @Column(nullable = false)
    @Size(min = 5, max = 30, message = "Full name should be at least 5 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name should be a valid string")
    @NotBlank(message = "Name is required.")
    private String name;

    @Column(nullable = false)
    @Size(min = 5, max = 100, message = "Address should be between 5 and 100 characters")
    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Phone is required.")
    @Column(unique = true, nullable = false)
    @Size(min = 10, max = 10, message = "Phone number should be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be a 10-digit number")
    private String phone;

    public Supplier() {
    }

    public Supplier(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.phone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supplier other = (Supplier) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return Objects.equals(this.phone, other.phone);
    }

}
