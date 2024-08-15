package com.syntech.sbs.bean;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.repository.ProductRepository;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private List<Product> products;
    
    @EJB
    private ProductRepository productRepo;

    @PostConstruct
    public void init(){
        products = productRepo.findAll();
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    
    public void saveProduct(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            System.out.println("Attempting to save: "+product);
            productRepo.save(product);
             facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                     "Success", "Product saved successfully"));
            System.out.println("Product saved successfully: " + product.getName());
            
            product = new Product();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Failed to save product"));
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(Product product){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            productRepo.delete(product.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Sucess", "Product Deleted Successfully"));
            products = productRepo.findAll(); //refresh product list
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Failed to delete product"));
            e.printStackTrace();
        }
    }
    
    public void prepareEditProduct(Product product) {
        this.product = product;
    }

    public void updateProduct() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            productRepo.update(product);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Success", "Product updated successfully"));
            products = productRepo.findAll(); // Refresh the user list
            product = new Product(); // Clear form after update
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Failed to update product"));
            e.printStackTrace();
        }
    }
}
