package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.SalesDetailsRepository;
import com.syntech.sbs.repository.SalesRepository;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viewSalesBean")
@ViewScoped
public class ViewSalesBean implements Serializable {

    private List<Sales> salesList;
    private List<User> customers;
    private Sales sales;
    private List<SalesDetails> salesDetails;
    private GenericLazyDataModel<Sales> lazySales;
    private String phone;

    @Inject
    private SalesRepository salesRepo;

    @Inject
    private SalesDetailsRepository salesDetailRepo;

    @Inject
    private UserRepository customerRepo;

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        sessionBean.checkSession();

        sales = new Sales();
        lazySales = new GenericLazyDataModel<>(salesRepo, Sales.class);
        customers = customerRepo.findAll();
        salesDetails = new ArrayList<>();
        salesList = new ArrayList<>();
    }

    // Getter and Setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Sales> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList) {
        this.salesList = salesList;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public GenericLazyDataModel<Sales> getLazySales() {
        return lazySales;
    }

    public void setLazySales(GenericLazyDataModel<Sales> lazySales) {
        this.lazySales = lazySales;
    }

    public List<SalesDetails> getSalesDetails() {
        return salesDetails;
    }

    public void setSalesDetails(List<SalesDetails> salesDetails) {
        this.salesDetails = salesDetails;
    }

    public void viewDetails(Sales selectedSales) {
        if (selectedSales != null) {
            System.out.println("Selected Sales ID: " + selectedSales.getId());
            this.sales = selectedSales;
            salesDetails = salesDetailRepo.findBySalesId(selectedSales.getId());
        } else {
            System.out.println("Selected Sales is null!");
        }
    }

    public List<String> completeCustomerPhone(String query) {
        return customers.stream()
                .map(User::getPhone)
                .filter(p -> p.startsWith(query))
                .collect(Collectors.toList());
    }

    public void searchSalesByPhone() {
        System.err.println("Phone: " + phone);

        User customer = customerRepo.findByPhone(phone);

        if (customer != null) {
            salesList = salesRepo.findByCustomerId(customer.getId());
            salesDetails.clear();
        } else {
            salesList = new ArrayList<>();
        }
    }

}
