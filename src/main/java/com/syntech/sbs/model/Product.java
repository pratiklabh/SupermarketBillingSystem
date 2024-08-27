package com.syntech.sbs.model;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product extends BaseIdEntity {

    @NotBlank(message = "Name is required.")
    @Size(min = 2, max = 50, message = "Name should be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Rate is required.")
    @Positive(message = "Rate must be a positive number")
    private BigInteger rate;

    @NotBlank(message = "Type is required.")
    @Size(min = 3, max = 30, message = "Type should be between 3 and 30 characters")
    private String type;

    @Size(max = 255, message = "Description should be up to 255 characters")
    private String description;

    @Size(min = 1, max = 10, message = "Unit should be between 1 and 10 characters")
    private String unit;

    @NotNull(message = "Discount is required.")
    @PositiveOrZero(message = "Discount must be zero or a positive number")
    private BigInteger discount;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{5}$", message = "Code must be exactly 5 digits")
    @NotNull(message = "Code is required.")
    private String code;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    // Constructors, Getters, and Setters
    public Product() {
    }

    public Product(String name, BigInteger rate, String type, String code, String description, String unit, BigInteger discount) {
        this.name = name;
        this.rate = rate;
        this.type = type;
        this.description = description;
        this.unit = unit;
        this.discount = discount;
        this.code = code;
    }


    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    // Other Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigInteger getDiscount() {
        return discount;
    }

    public void setDiscount(BigInteger discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}