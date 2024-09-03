package com.syntech.sbs.bean;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.model.PurchaseDetails;
import com.syntech.sbs.model.Stock;
import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.repository.ProductRepository;
import com.syntech.sbs.repository.PurchaseRepository;
import com.syntech.sbs.repository.StockRepository;
import com.syntech.sbs.repository.SupplierRepository;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("purchaseBean")
@ViewScoped
public class PurchaseBean implements Serializable {

    private List<Supplier> suppliers;
    private Supplier supplier;
    private List<Product> products;
    private Product product;
    private int quantity;
    private BigInteger total;
    private BigInteger subTotal;
    private BigInteger rate;
    private BigInteger discount;
    private List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    @Inject
    private StockRepository stockRepository;

    @Inject
    private ProductRepository productRepository;

    @PostConstruct
    public void init() {
        products = productRepository.findAll();
        suppliers = supplierRepository.findAll();
    }

    public void addItem() {
        if (quantity == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Minimum quantity should be 1"));
        } else {
            try {
                subTotal = BigInteger.valueOf(quantity).multiply(rate);
                PurchaseDetails detail = new PurchaseDetails();
                detail.setProduct(product);
                detail.setQuantity(quantity);
                detail.setRate(rate);
                detail.setDiscount(discount);
                detail.setSubTotal(subTotal);

                purchaseDetailsList.add(detail);
                calculateTotal();
                clearItemFields();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error", "Fields cannot be empty"));

            }
        }
    }

    public void completePurchase() {
        if (purchaseDetailsList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Purchase details cannot be empty"));
            return;
        }

        if (total == null || total.equals(BigInteger.ZERO)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Total must be calculated and non-zero"));
            return;
        }

        try {
            Purchase purchase = new Purchase();
            purchase.setSupplier(supplier);
            purchase.setDate(LocalDateTime.now());
            purchase.setTotal(total);

            // Set the purchase for each purchase detail
            for (PurchaseDetails detail : purchaseDetailsList) {
                detail.setPurchase(purchase);
            }

            purchase.setPurchaseDetails(purchaseDetailsList);
            purchaseRepository.save(purchase);
            updateStock();

            clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase completed successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Failed to complete purchase"));
        }
    }

    private void updateStock() {
        for (PurchaseDetails details : purchaseDetailsList) {
            try {
                Product product = productRepository.findByName(details.getProduct().getName());
                Stock stock = stockRepository.findByProductName(product.getName());

                if (stock == null) {
                    stock = new Stock();
                    stock.setProduct(product);
                    stock.setQuantity(details.getQuantity());
                } else {
                    stock.setQuantity(stock.getQuantity() + details.getQuantity());
                }

                stock.setRate(details.getRate());
                stock.setDate(LocalDateTime.now());

                stockRepository.save(stock);

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error", "Failed to update stock"));
            }
        }
    }

    private void calculateTotal() {
        BigInteger grossTotal = purchaseDetailsList.stream()
                .map(details -> BigInteger.valueOf(details.getQuantity()).multiply(details.getRate()))
                .reduce(BigInteger.ZERO, BigInteger::add);

        BigInteger totalDiscount = purchaseDetailsList.stream()
                .map(details -> BigInteger.valueOf(details.getQuantity()).multiply(details.getDiscount()))
                .reduce(BigInteger.ZERO, BigInteger::add);

        total = grossTotal.subtract(totalDiscount);
    }

    public void cancel() {
        clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation cancelled"));
    }

    private void clear() {
        supplier = null;
        product = null;
        quantity = 0;
        total = BigInteger.ZERO;
        purchaseDetailsList.clear();
    }
    public void clearItemFields() {
        quantity = 1;
        rate = BigInteger.ZERO;
        subTotal = BigInteger.ZERO;
        discount = BigInteger.ZERO;
    }

    // Getters and Setters
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public List<PurchaseDetails> getPurchaseDetailsList() {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList) {
        this.purchaseDetailsList = purchaseDetailsList;
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

}
