package com.syntech.sbs.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "purchase")
public class Purchase extends BaseIdEntity {

    @ManyToOne
    private Supplier supplier;

    private LocalDateTime date;


    private Long total;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<PurchaseDetails> purchaseDetails;

    public Purchase() {
    }

    public Purchase(Supplier supplier, LocalDateTime date, 
                    Long total, List<PurchaseDetails> purchaseDetails) {
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

}
