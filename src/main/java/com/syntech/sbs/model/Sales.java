package com.syntech.sbs.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sales")
public class Sales extends BaseIdEntity {

     @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer cannot be null")
    private User customer;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    @Column(name = "total", nullable = false)
    @NotNull(message = "Total cannot be null")
    @Min(value = 0, message = "Total must be zero or greater")
    private BigInteger total;

    @Column(name = "payment_mode", nullable = false)
    @NotNull(message = "Payment mode cannot be null")
    @Size(min = 1, max = 50, message = "Payment mode must be between 1 and 50 characters")
    private String paymentMode;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesDetails> salesDetails;

    public Sales() {
    }

    public Sales(User customer, LocalDateTime date, BigInteger total, String paymentMode, List<SalesDetails> salesDetails) {
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.paymentMode = paymentMode;
        this.salesDetails = salesDetails;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public List<SalesDetails> getSalesDetails() {
        return salesDetails;
    }

    public void setSalesDetails(List<SalesDetails> salesDetails) {
        this.salesDetails = salesDetails;
    }

}
