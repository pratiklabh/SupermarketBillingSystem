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
    private BigInteger rate;
    private String unit;
    private BigInteger subTotal;
    private Supplier supplier;
    private BigInteger discount;
    private BigInteger total;
    private String code; 
    private String type;
    private String description; 
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
        suppliers = supplierRepository.findAll();
        selectedPurchase = new Purchase();
    }

    public void cancel() {
        selectedPurchase = new Purchase(); // Reset the form
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation cancelled"));
    }

    public void addItem() {
        subTotal = BigInteger.valueOf(quantity).multiply(rate);
        PurchaseDetails detail = new PurchaseDetails();
        detail.setProductName(productName);
        detail.setQuantity(quantity);
        detail.setRate(rate);
        detail.setUnit(unit);
        detail.setDiscount(discount);
        detail.setCode(code); 
        detail.setType(type);

        purchaseDetailsList.add(detail);
        calculateTotal();
        clearItemFields();
    }

    public void completePurchase() {
        try {
            // Create a new Purchase object
            Purchase purchase = new Purchase();
            purchase.setSupplier(supplier);
            purchase.setDate(LocalDateTime.now());
            purchase.setTotal(total);

            // Set the PurchaseDetails list for this purchase
            for (PurchaseDetails details : purchaseDetailsList) {
                details.setPurchase(purchase);  // Associate each detail with the purchase
            }
            purchase.setPurchaseDetails(purchaseDetailsList);

            // Save the Purchase (which will also save PurchaseDetails due to cascade)
            purchaseRepository.save(purchase);

            // Update the stock after purchase completion
            updateStock();

            // Clear form and data
            clear();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase completed successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to complete purchase"));
        }
    }

    private void updateStock() {
        for (PurchaseDetails details : purchaseDetailsList) {
            try {
                // Fetch the product by name
                Product product = productRepository.findByName(details.getProductName());

                if (product == null) {
                    // Product not found, create a new Product
                    product = new Product();
                    product.setName(details.getProductName());
                    product.setCode(details.getCode());
                    product.setType(details.getType());
                    product.setDescription(details.getDescription());
                    product.setRate(details.getRate());
                    product.setDiscount(details.getDiscount());
                    product.setUnit(details.getUnit());
                    // Save the new product
                    productRepository.save(product);
                }

                // Create/update a Stock entry
                Stock stock = stockRepository.findByProductName(product.getName());

                if (stock == null) {
                    stock = new Stock();
                    stock.setProduct(product);
                    stock.setQuantity(details.getQuantity());
                } else {
                    // Increase stock if already present
                    stock.setQuantity(stock.getQuantity() + details.getQuantity());
                }

                // Update other stock details
                stock.setRate(details.getRate());
                stock.setUnit(details.getUnit());
                stock.setDate(LocalDateTime.now());

                // Save the stock entry
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

    public void clear() {
        supplier = null;
        discount = BigInteger.ZERO;
        total = BigInteger.ZERO;
        purchaseDetailsList.clear();
    }

    public void clearItemFields() {
        productName = "";
        quantity = 1;
        rate = BigInteger.ZERO;
        unit = "";
        subTotal = BigInteger.ZERO;
        discount = BigInteger.ZERO;
        code = ""; 
        type = ""; 
        description = ""; 
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

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigInteger getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigInteger subTotal) {
        this.subTotal = subTotal;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public List<PurchaseDetails> getPurchaseDetailsList() {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList) {
        this.purchaseDetailsList = purchaseDetailsList;
    }

    // Getters and Setters for new fields
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
