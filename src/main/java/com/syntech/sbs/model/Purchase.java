package com.syntech.sbs.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "purchase")
public class Purchase extends BaseIdEntity {

    @ManyToOne
    @NotNull(message = "Supplier must not be null")
    private Supplier supplier;

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotNull(message = "Total must not be null")
    @Min(value = 0, message = "Total must be a positive value")
    private BigInteger total;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "Purchase details must not be empty")
    private List<PurchaseDetails> purchaseDetails;

    public Purchase() {
    }

    public Purchase(Supplier supplier, LocalDateTime date,
            BigInteger total, List<PurchaseDetails> purchaseDetails) {
        this.supplier = supplier;
        this.date = date;
        this.total = total;
        this.purchaseDetails = purchaseDetails;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public List<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.supplier);
        hash = 53 * hash + Objects.hashCode(this.date);
        hash = 53 * hash + Objects.hashCode(this.total);
        hash = 53 * hash + Objects.hashCode(this.purchaseDetails);
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
        final Purchase other = (Purchase) obj;
        if (!Objects.equals(this.supplier, other.supplier)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return Objects.equals(this.purchaseDetails, other.purchaseDetails);
    }

    
}
