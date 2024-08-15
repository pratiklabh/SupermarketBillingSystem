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
import javax.inject.Inject;
import javax.inject.Named;

@Named("ProductBean")
@ViewScoped
public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Product product;

    private List<Product> products;
    private boolean editMode = false;

    @EJB
    private ProductRepository productRepo;

    @PostConstruct
    public void init() {
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

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void saveOrUpdateProduct() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            String duplicateMessage = checkForDuplicateProduct();
            if (duplicateMessage != null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        duplicateMessage, "Error"));
                return; // Exit the method if a duplicate is found
            }

            if (editMode) {
                productRepo.update(product);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "Product updated successfully"));
            } else {
                productRepo.save(product);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "Product saved successfully"));
            }

            products = productRepo.findAll(); // Refresh the product list
            product = new Product(); // Clear form after submission
            editMode = false; // Reset the edit mode flag
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save product"));
        }
    }

    public void deleteProduct(Product product) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            productRepo.delete(product.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Product deleted successfully"));
            products = productRepo.findAll(); // Refresh the product list
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete product"));
        }
    }

    public void prepareEditProduct(Product product) {
        this.product = product;
        this.editMode = true;
    }

    public void prepareNewProduct() {
        this.editMode = false;
    }

    private String checkForDuplicateProduct() {
        // Check for duplicate product code
        Product existingProduct = productRepo.findByCode(product.getCode());
        if (existingProduct != null && !existingProduct.getId().equals(product.getId())) {
            return "Product code already exists";
        }

        return null; // No duplicates found
    }

}
