package com.syntech.sbs.model;

import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "purchase_details")
public class PurchaseDetails extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    @NotNull(message = "Purchase must not be null")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product cannot be null")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Column(name = "rate", nullable = false)
    @NotNull(message = "Rate must not be null")
    @Min(value = 0, message = "Rate must be a positive value")
    private BigInteger rate;

    @Column(name = "discount")
    @Min(value = 0, message = "Discount must be a positive value")
    private BigInteger discount;

    @Column(name = "subtotal")
    private BigInteger subTotal;

    public PurchaseDetails() {
    }

    public PurchaseDetails(Purchase purchase, Product product, int quantity, BigInteger rate, 
            BigInteger discount, BigInteger subTotal) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.rate = rate;
        this.discount = discount;
        this.subTotal = subTotal;
    }

    

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigInteger getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigInteger subTotal) {
        this.subTotal = subTotal;
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

    public BigInteger getDiscount() {
        return discount;
    }

    public void setDiscount(BigInteger discount) {
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.purchase);
        hash = 29 * hash + Objects.hashCode(this.product);
        hash = 29 * hash + this.quantity;
        hash = 29 * hash + Objects.hashCode(this.rate);
        hash = 29 * hash + Objects.hashCode(this.discount);
        hash = 29 * hash + Objects.hashCode(this.subTotal);
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
        final PurchaseDetails other = (PurchaseDetails) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.purchase, other.purchase)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.discount, other.discount)) {
            return false;
        }
        return Objects.equals(this.subTotal, other.subTotal);
    }

}
