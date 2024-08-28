package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.model.PurchaseDetails;
import com.syntech.sbs.repository.PurchaseDetailsRepository;
import com.syntech.sbs.repository.PurchaseRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viewPurchaseBean")
@ViewScoped
public class ViewPurchaseBean {

    private List<Purchase> purchaseList;
    private Purchase purchase;
    private List<PurchaseDetails> purchaseDetails;
    private GenericLazyDataModel<Purchase> lazyPurchases;

    @Inject
    private PurchaseRepository purchaseRepo;

    @Inject
    private PurchaseDetailsRepository purchaseDetailRepo;

    @PostConstruct
    public void init() {
        purchase = new Purchase();
        lazyPurchases = new GenericLazyDataModel<>(purchaseRepo, Purchase.class);
        purchaseDetails = new ArrayList<>(); 
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

}
