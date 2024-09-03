package com.syntech.sbs.model;

import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.rate);
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.unit);
        hash = 23 * hash + Objects.hashCode(this.discount);
        hash = 23 * hash + Objects.hashCode(this.code);
        hash = 23 * hash + Objects.hashCode(this.stock);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.discount, other.discount)) {
            return false;
        }
        
        return Objects.equals(this.stock, other.stock);
    }

    @Override
    public String toString() {
        return "Product{id="+ getId() + ",name=" + name + ", rate=" + rate + ", type=" + type + ", description=" + description + ", unit=" + unit + ", discount=" + discount + ", code=" + code + '}';
    }
    
    
    
}