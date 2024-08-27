package com.syntech.sbs.model;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales_details")
public class SalesDetails extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rate", nullable = false)
    private BigInteger rate;

    @Column(name = "discount", nullable = false)
    private BigInteger discount;

    @Column(name = "unit", nullable = false)
    private String unit;

    public SalesDetails() {
    }

    public SalesDetails(Sales sales, Product product, Integer quantity,
            BigInteger rate, BigInteger discount, String unit) {
        this.sales = sales;
        this.product = product;
        this.quantity = quantity;
        this.rate = rate;
        this.discount = discount;
        this.unit = unit;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    public BigInteger getDiscount() {
        return discount;
    }

    public void setDiscount(BigInteger discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
