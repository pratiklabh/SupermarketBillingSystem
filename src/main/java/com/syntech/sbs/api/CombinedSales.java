package com.syntech.sbs.api;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

public class CombinedSales {
    private LocalDateTime date;
    private String productName;
    private BigInteger rate;
    private int quantity;
    private BigInteger discount;
    private BigInteger total;

    public CombinedSales() {
    }

    public CombinedSales(LocalDateTime date, String productName, BigInteger rate, int quantity, BigInteger discount, BigInteger total) {
        this.date = date;
        this.productName = productName;
        this.rate = rate;
        this.quantity = quantity;
        this.discount = discount;
        this.total = total;
    }

    

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigInteger getDiscount() {
        return discount;
    }

    public void setDiscount(BigInteger discount) {
        this.discount = discount;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.date);
        hash = 83 * hash + Objects.hashCode(this.productName);
        hash = 83 * hash + Objects.hashCode(this.rate);
        hash = 83 * hash + this.quantity;
        hash = 83 * hash + Objects.hashCode(this.discount);
        hash = 83 * hash + Objects.hashCode(this.total);
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
        final CombinedSales other = (CombinedSales) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.discount, other.discount)) {
            return false;
        }
        return Objects.equals(this.total, other.total);
    }

    
    
    @Override
    public String toString() {
        return "CombinedSales{" + "date=" + date + ", productName=" + productName + ", rate=" + rate + ", quantity=" + quantity + ", discount=" + discount + ", total=" + total + '}';
    }

    
}

