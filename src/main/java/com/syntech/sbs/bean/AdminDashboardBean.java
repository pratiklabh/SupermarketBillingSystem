package com.syntech.sbs.bean;

import com.syntech.sbs.repository.ProductRepository;
import com.syntech.sbs.repository.PurchaseRepository;
import com.syntech.sbs.repository.SalesRepository;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named ("adminDashboardBean")
@ViewScoped
public class AdminDashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalCustomers;
    private int totalSales;
    private int totalPurchases;
    private String mostSoldProduct;

    @Inject
    private SalesRepository salesRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    @Inject
    private ProductRepository productRepository;

    @PostConstruct
    public void init() {
//        totalSales = salesRepository.getTotalSales();
//        totalPurchases = purchaseRepository.getTotalPurchases();
//        mostSoldProduct = productRepository.getMostSoldProduct();
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public String getMostSoldProduct() {
        return mostSoldProduct;
    }
}
