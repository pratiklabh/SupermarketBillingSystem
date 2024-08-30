package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.repository.SupplierRepository;
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

@Named("SupplierBean")
@ViewScoped
public class SupplierBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Supplier supplier;

    private List<Supplier> suppliers;
    private boolean editMode = false;

    private GenericLazyDataModel<Supplier> lazySuppliers;

    @Inject
    private SupplierRepository supplierRepo;

    @PostConstruct
    public void init() {
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
        supplier = new Supplier();
        lazySuppliers = new GenericLazyDataModel<>(supplierRepo, Supplier.class);
    }

    public GenericLazyDataModel<Supplier> getLazySuppliers() {
        return lazySuppliers;
    }

    public void setLazySuppliers(GenericLazyDataModel<Supplier> lazySuppliers) {
        this.lazySuppliers = lazySuppliers;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void saveOrUpdateSupplier() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            String duplicateMessage = checkForDuplicateSupplier();
            if (duplicateMessage != null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        duplicateMessage, "Error"));
                return; // Exit the method if a duplicate is found
            }

            if (editMode) {
                supplierRepo.update(supplier);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "Supplier updated successfully"));
            } else {
                supplierRepo.save(supplier);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "Supplier saved successfully"));
            }

            suppliers = supplierRepo.findAll(); // Refresh the supplier list
            supplier = new Supplier(); // Clear form after submission
            editMode = false; // Reset the edit mode flag
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save supplier"));
        }
    }

    public void deleteSupplier(Supplier supplier) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            supplierRepo.delete(supplier.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Supplier deleted successfully"));
            suppliers = supplierRepo.findAll(); // Refresh the supplier list
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete supplier"));
        }
    }

    public void prepareEditSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.editMode = true;
    }

    public void prepareNewSupplier() {
        this.editMode = false;
    }

    private String checkForDuplicateSupplier() {
        // Check for duplicate phone number
        Supplier existingSupplier = supplierRepo.findByPhone(supplier.getPhone());
        if (existingSupplier != null && !existingSupplier.getId().equals(supplier.getId())) {
            return "Phone number already exists";
        }

        return null; // No duplicates found
    }
}
