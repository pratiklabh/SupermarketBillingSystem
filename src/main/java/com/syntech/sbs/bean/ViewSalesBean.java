package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.repository.SalesDetailsRepository;
import com.syntech.sbs.repository.SalesRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viewSalesBean")
@ViewScoped
public class ViewSalesBean {

    private List<Sales> salesList;
    private Sales sales;
    private List<SalesDetails> salesDetails;
    private GenericLazyDataModel<Sales> lazySaless;

    @Inject
    private SalesRepository salesRepo;

    @Inject
    private SalesDetailsRepository salesDetailRepo;

    @PostConstruct
    public void init() {
        sales = new Sales();
        lazySaless = new GenericLazyDataModel<>(salesRepo, Sales.class);
        salesDetails = new ArrayList<>(); 
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

    public GenericLazyDataModel<Sales> getLazySaless() {
        return lazySaless;
    }

    public void setLazySaless(GenericLazyDataModel<Sales> lazySaless) {
        this.lazySaless = lazySaless;
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

}
