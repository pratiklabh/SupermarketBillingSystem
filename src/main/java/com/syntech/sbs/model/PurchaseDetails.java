package com.syntech.sbs.model;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_details")
public class PurchaseDetails extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "rate", nullable = false)
    private BigInteger rate;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "discount")
    private BigInteger discount;

    @Column(name = "code")
    private String code; 

    @Column(name = "type")
    private String type; 

    @Column(name = "description")
    private String description; 

    public PurchaseDetails() {
    }

    public PurchaseDetails(Purchase purchase, String productName, int quantity, 
            BigInteger rate, String unit, BigInteger discount, String code, 
            String type, String description) {
        this.purchase = purchase;
        this.productName = productName;
        this.quantity = quantity;
        this.rate = rate;
        this.unit = unit;
        this.discount = discount;
        this.code = code;
        this.type = type;
        this.description = description;
    }

    // Getters and setters for existing fields
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
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
}
