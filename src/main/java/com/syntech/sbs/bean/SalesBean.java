package com.syntech.sbs.bean;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.ProductRepository;
import com.syntech.sbs.repository.SalesRepository;
import com.syntech.sbs.repository.UserRepository;
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
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class SalesBean implements Serializable {

    private String productCode;
    private Sales selectedSale;
    private List<Product> products;
    private Product selectedProduct;

    private String customerPhone;
    private String customerName;
    private User selectedCustomer;
    private List<User> customers;

    private int quantity = 1;  // Default 1
    private double rate;
    private String unit;
    private double subTotal;
    private long discount;
    private long total;
    private String paymentMode;
    private List<SalesDetails> salesDetailsList = new ArrayList<>();

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SalesRepository salesRepository;

    @Inject
    private UserRepository userRepo;

    @PostConstruct
    public void init() {
        customers = userRepo.findAll();
        products = productRepository.findAll();
        selectedSale = new Sales();
    }

    public List<String> completeProductCode(String query) {
        String queryLowerCase = query.toLowerCase();
        return products.stream()
                .map(Product::getCode)
                .filter(code -> code.toLowerCase().startsWith(queryLowerCase))
                .collect(Collectors.toList());
    }

    public List<String> completeCustomerPhone(String query) {
        List<String> matchingPhones = customers.stream()
                .map(User::getPhone)
                .filter(phone -> phone.startsWith(query))
                .collect(Collectors.toList());

        System.out.println("Matching Phones: " + matchingPhones);
        return matchingPhones;
    }

    public void searchProduct() {
        selectedProduct = productRepository.findByCode(productCode);
        if (selectedProduct != null) {
            rate = selectedProduct.getRate();
            discount = selectedProduct.getDiscount();
            unit = selectedProduct.getUnit();
            // Automatically add the product to the table
            addItem();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Product not found"));
            clearProductFields();
        }
    }

    public void searchCustomer() {
        selectedCustomer = userRepo.findByPhone(customerPhone);
        if (selectedCustomer != null) {
            customerName = selectedCustomer.getName();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Customer not found"));
            customerName = "";
        }
    }

    public void addItem() {
        if (selectedProduct == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No product selected"));
            return;
        }

        subTotal = quantity * rate;
        SalesDetails detail = new SalesDetails();
        detail.setProduct(selectedProduct);
        detail.setQuantity(quantity);
        detail.setRate(rate);
        detail.setUnit(unit);
        detail.setDiscount(discount);

        salesDetailsList.add(detail);
        calculateTotal();
        clearProductFields();
    }

    public void incrementQuantity(SalesDetails item) {
        item.setQuantity(item.getQuantity() + 1);
        calculateTotal();
        System.out.println("Updated total after increment: " + total);
    }

    public void decrementQuantity(SalesDetails item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            calculateTotal();
            System.out.println("Updated total after decrement: " + total);
        }
    }

    public void completeSale() {
        Sales sale = new Sales();
        sale.setCustomer(selectedSale.getCustomer());
        sale.setDate(LocalDateTime.now());
        sale.setTotal(total);
        sale.setPaymentMode(paymentMode);
        sale.setSalesDetails(salesDetailsList);

        // Set the sales reference in sales details
        for (SalesDetails details : salesDetailsList) {
            details.setSales(sale);
        }

        // Save sale to the database (this will also save salesDetails because of cascade)
        salesRepository.save(sale);
        clear();
    }

    private void calculateTotal() {
        long grossTotal = salesDetailsList.stream()
                .mapToLong(details -> (long) (details.getQuantity() * details.getRate()))
                .sum();

        long discountTotal = salesDetailsList.stream()
                .mapToLong(SalesDetails::getDiscount)
                .sum();

        total = grossTotal - discountTotal;
    }

    public void clearProductFields() {
        productCode = "";
        rate = 0;
        quantity = 1;
        unit = "";
        subTotal = 0;
        discount = 0;
        selectedProduct = null;
    }

    public void clear() {
        selectedSale = new Sales();
        salesDetailsList.clear();
        total = 0;
        customerPhone = "";
        customerName = "";
        clearProductFields();
    }

    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<SalesDetails> getSalesDetailsList() {
        return salesDetailsList;
    }

    public void setSalesDetailsList(List<SalesDetails> salesDetailsList) {
        this.salesDetailsList = salesDetailsList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
