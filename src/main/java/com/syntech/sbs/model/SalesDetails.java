package com.syntech.sbs.model;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sales_details")
public class SalesDetails extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "sales_id", nullable = false)
    @NotNull(message = "Sales cannot be null")
    private Sales sales;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product cannot be null")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

    @Column(name = "rate", nullable = false)
    @NotNull(message = "Rate cannot be null")
    @Min(value = 1, message = "Rate must be greater than zero")
    private BigInteger rate;

    @Column(name = "discount", nullable = false)
    @NotNull(message = "Discount cannot be null")
    @Min(value = 0, message = "Discount must be zero or greater")
    private BigInteger discount;

    @Column(name = "unit", nullable = false)
    @NotNull(message = "Unit cannot be null")
    @Size(min = 1, max = 50, message = "Unit must be between 1 and 50 characters")
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
