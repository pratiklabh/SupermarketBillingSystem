package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.model.PurchaseDetails;
import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.repository.PurchaseDetailsRepository;
import com.syntech.sbs.repository.PurchaseRepository;
import com.syntech.sbs.repository.SupplierRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viewPurchaseBean")
@ViewScoped
public class ViewPurchaseBean implements Serializable {

    private List<Purchase> purchaseList;
    private Purchase purchase;
    private List<PurchaseDetails> purchaseDetails;
    private GenericLazyDataModel<Purchase> lazyPurchases;
    private String phone;
    private List<Supplier> suppliers;

    @Inject
    private PurchaseRepository purchaseRepo;

    @Inject
    private PurchaseDetailsRepository purchaseDetailRepo;

    @Inject
    private SupplierRepository supplierRepo;

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        sessionBean.checkSession();

        purchase = new Purchase();
        lazyPurchases = new GenericLazyDataModel<>(purchaseRepo, Purchase.class);
        suppliers = supplierRepo.findAll();
        purchaseDetails = new ArrayList<>();
        purchaseList = new ArrayList<>();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public GenericLazyDataModel<Purchase> getLazyPurchases() {
        return lazyPurchases;
    }

    public void setLazyPurchases(GenericLazyDataModel<Purchase> lazyPurchases) {
        this.lazyPurchases = lazyPurchases;
    }

    public List<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    public void viewDetails(Purchase selectedPurchase) {
        if (selectedPurchase != null) {
            System.out.println("Selected Purchase ID: " + selectedPurchase.getId());
            this.purchase = selectedPurchase;
            purchaseDetails = purchaseDetailRepo.findByPurchaseId(selectedPurchase.getId());
        } else {
            System.out.println("Selected Purchase is null!");
        }
    }

    public List<String> completeSupplierPhone(String query) {
        return suppliers.stream()
                .map(Supplier::getPhone)
                .filter(p -> p.startsWith(query))
                .collect(Collectors.toList());
    }

    public void searchPurchaseByPhone() {
        System.err.println("Phone: " + phone);

        Supplier supplier = supplierRepo.findByPhone(phone);

        if (supplier != null) {
            purchaseList = purchaseRepo.findBySupplierId(supplier.getId());
            purchaseDetails.clear();
        } else {
            purchaseList = new ArrayList<>();
        }
    }

}
