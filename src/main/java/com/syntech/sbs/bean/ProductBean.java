package com.syntech.sbs.bean;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.service.ProductService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("ProductBean")
@ViewScoped
public class ProductBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Product product = new Product();
    
    @EJB
    private ProductService productService;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void saveProduct(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            System.out.println("Attempting to save: "+product);
            productService.saveProduct(product);
             facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                     "Success", "Product saved successfully"));
            System.out.println("Product saved successfully: " + product.getName());
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Failed to save product"));
            e.printStackTrace();
        }
    }
}
