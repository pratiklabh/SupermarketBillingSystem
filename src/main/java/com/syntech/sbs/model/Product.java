package com.syntech.sbs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends BaseIdEntity {

    private String name;
    private Long rate;
    private String type;
    private String description;
    private String unit;
    private Long discount;
    private Long quantity;

    @Column(unique = true)
    private Long code;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    // Constructors, Getters, and Setters
    public Product() {
    }

    public Product(String name, Long rate, String type, Long code, String description, String unit, Long discount) {
        this.name = name;
        this.rate = rate;
        this.type = type;
        this.description = description;
        this.unit = unit;
        this.discount = discount;
        this.code = code;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
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

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
