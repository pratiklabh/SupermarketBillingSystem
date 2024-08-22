package com.syntech.sbs.bean;

import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.model.PurchaseDetails;
import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.repository.PurchaseRepository;
import com.syntech.sbs.repository.SupplierRepository;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class PurchaseBean implements Serializable {

    private Purchase selectedPurchase;
    private List<Supplier> suppliers;
    private String productName;
    private int quantity;
    private double rate;
    private String unit;
    private double subTotal;
    private Supplier supplier;
    private Long discount;
    private Long total;
    private List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    @PostConstruct
    public void init() {
        suppliers = supplierRepository.findAll();
        selectedPurchase = new Purchase();
    }

    public void savePurchase() {
        try {
            // Implement save logic here (e.g., save purchase to the database)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase saved successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save purchase"));
        }
    }

    public void cancel() {
        selectedPurchase = new Purchase(); // Reset the form
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation cancelled"));
    }

    public void addItem() {
        subTotal = quantity * rate;
        PurchaseDetails detail = new PurchaseDetails();
        detail.setProductName(productName);
        detail.setQuantity(quantity);
        detail.setRate(rate);
        detail.setUnit(unit);
        detail.setDiscount(discount);

        purchaseDetailsList.add(detail);
        calculateTotal();
        clearItemFields();
    }

    public void completePurchase() {
        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setDate(LocalDateTime.now());
        purchase.setTotal(total);
        purchase.setDiscount(discount);
        purchase.setPurchaseDetails(purchaseDetailsList);

        // Set the purchase reference in purchase details
        for (PurchaseDetails details : purchaseDetailsList) {
            details.setPurchase(purchase);
        }

        // Save purchase to the database (this will also save purchaseDetails because of cascade)
        purchaseRepository.save(purchase);

        clear();
    }

    private void calculateTotal() {
        // Calculate the gross total cost of all products
        long grossTotal = purchaseDetailsList.stream()
                .mapToLong(details -> (long) (details.getQuantity() * details.getRate()))
                .sum();

        // Calculate the total discount
        long totalDiscount = purchaseDetailsList.stream()
                .mapToLong(details -> (long) (details.getQuantity() * details.getDiscount()))
                .sum();

        // Subtract the total discount from the gross total
        total = grossTotal - totalDiscount;
    }

    public void clear() {
        supplier = null;
        discount = 0L;
        total = 0L;
        purchaseDetailsList.clear();
    }

    public void clearItemFields() {
        productName = "";
        quantity = 1;
        rate = 0;
        unit = "";
        subTotal = 0.0;
        discount = 0L;
    }

    // Getters and Setters
    public Purchase getSelectedPurchase() {
        if (selectedPurchase == null) {
            selectedPurchase = new Purchase();
        }
        return selectedPurchase;
    }

    public void setSelectedPurchase(Purchase selectedPurchase) {
        this.selectedPurchase = selectedPurchase;
    }

    public List<Supplier> getSuppliers() {
        if (suppliers == null) {
            suppliers = new ArrayList<>();
        }
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<PurchaseDetails> getPurchaseDetailsList() {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList) {
        this.purchaseDetailsList = purchaseDetailsList;
    }
}
