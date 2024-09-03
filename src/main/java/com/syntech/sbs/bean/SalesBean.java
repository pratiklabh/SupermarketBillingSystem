package com.syntech.sbs.bean;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.model.Stock;
import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.ProductRepository;
import com.syntech.sbs.repository.SalesRepository;
import com.syntech.sbs.repository.StockRepository;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("salesBean")
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
    private BigInteger rate = BigInteger.ZERO;
    private String unit;
    private BigInteger subTotal = BigInteger.ZERO;
    private BigInteger discount = BigInteger.ZERO;
    private BigInteger total = BigInteger.ZERO;
    private String paymentMode;
    private List<SalesDetails> salesDetailsList = new ArrayList<>();

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SalesRepository salesRepository;

    @Inject
    private UserRepository userRepo;

    @Inject
    private StockRepository stockRepository;
    
    @PostConstruct
    public void init() {
        customerPhone = "";
        customerName = "";
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
        return customers.stream()
                .map(User::getPhone)
                .filter(phone -> phone.startsWith(query))
                .collect(Collectors.toList());
    }

    public void searchProduct() {
        selectedProduct = productRepository.findByCode(productCode);
        if (selectedProduct != null) {
            rate = selectedProduct.getRate();
            discount = selectedProduct.getDiscount();
            unit = selectedProduct.getUnit();
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

    public void addItem() {
        if (selectedProduct == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No product selected"));
            return;
        }

        subTotal = BigInteger.valueOf(quantity).multiply(rate);
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

    public void completeSale() {
        try {
            selectedSale.setDate(LocalDateTime.now());
            selectedSale.setCustomer(selectedCustomer);
            selectedSale.setTotal(total);
            selectedSale.setPaymentMode(paymentMode);

            for (SalesDetails details : salesDetailsList) {
                details.setSales(selectedSale);
            }
            selectedSale.setSalesDetails(salesDetailsList);

            // Save the sale (which will also save SalesDetails due to cascade)
            salesRepository.save(selectedSale);

            // Update the stock after sale completion
            updateStock();

            // Clear form and data
            clear();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sale completed successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to complete sale"));
        }
    }

    private void updateStock() {
        for (SalesDetails details : salesDetailsList) {
            try {
                // Fetch the stock by product name
                Stock stock = stockRepository.findByProductName(details.getProduct().getName());

                if (stock != null) {
                    // Decrease stock quantity
                    stock.setQuantity(stock.getQuantity() - details.getQuantity());

                    // Update stock details
                    stock.setDate(LocalDateTime.now());

                    // Save the stock entry
                    stockRepository.save(stock);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Stock not found for product: " + details.getProduct().getName()));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update stock"));
            }
        }
    }

    private void calculateTotal() {
        // Calculate the gross total cost of all products
        BigInteger grossTotal = salesDetailsList.stream()
                .map(details -> BigInteger.valueOf(details.getQuantity()).multiply(details.getRate()))
                .reduce(BigInteger.ZERO, BigInteger::add);

        // Calculate the total discount
        BigInteger discountTotal = salesDetailsList.stream()
                .map(details -> BigInteger.valueOf(details.getQuantity()).multiply(details.getDiscount()))
                .reduce(BigInteger.ZERO, BigInteger::add);

        // Subtract the total discount from the gross total
        total = grossTotal.subtract(discountTotal);
    }

    public void clearProductFields() {
        productCode = "";
        rate = BigInteger.ZERO;
        quantity = 1;
        unit = "";
        subTotal = BigInteger.ZERO;
        discount = BigInteger.ZERO;
        selectedProduct = null;
    }

    public void clear() {
        selectedSale = new Sales();
        salesDetailsList.clear();
        total = BigInteger.ZERO;
        customerPhone = "";
        customerName = "";
        clearProductFields();

    }

    public void cancel() {
        selectedSale = new Sales(); // Reset the form
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation cancelled"));
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

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public BigInteger getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigInteger subTotal) {
        this.subTotal = subTotal;
    }
}
