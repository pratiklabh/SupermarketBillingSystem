package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Stock;
import com.syntech.sbs.repository.StockRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ViewScoped
@Named("stockBean")
public class StockBean implements Serializable{

    @Inject
    private StockRepository stockRepo;
    
    private Stock stock;
    
    private List<Stock> stockList;
    
    private GenericLazyDataModel<Stock> lazyStock;
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session == null || session.getAttribute("valid_user") == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Please log in first", "You need to log in to access this page."));
            try {
                context.getExternalContext().redirect("adminLogin.xhtml");
            } catch (IOException e) {
            }
        }
        stock = new Stock();
        lazyStock = new GenericLazyDataModel<>(stockRepo, Stock.class);
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public GenericLazyDataModel<Stock> getLazyStock() {
        return lazyStock;
    }

    public void setLazyStock(GenericLazyDataModel<Stock> lazyStock) {
        this.lazyStock = lazyStock;
    }
    
}
