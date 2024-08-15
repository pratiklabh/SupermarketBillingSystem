package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("UserBean")
@ViewScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private User user;

    private List<User> users;
    private boolean editMode = false;

    private LazyDataModel<User> lazyUsers;

    @Inject
    private UserRepository userRepo;

    @PostConstruct
    public void init() {
        lazyUsers = new LazyDataModel<User>() {
            private static final long serialVersionUID = 1L;

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return userRepo.countUsers(filterBy); 
            }

            @Override
            public List<User> load(int first, int pageSize, 
                    Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                List<User> users = userRepo.getUsers(first, pageSize); // Add pagination support in UserRepository
                this.setRowCount(userRepo.countUsers(filterBy)); // Count the total number of records
                return users;
            }

        };
    }

    public LazyDataModel<User> getLazyUsers() {
        return lazyUsers;
    }

    public void setLazyUsers(LazyDataModel<User> lazyUsers) {
        this.lazyUsers = lazyUsers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void saveOrUpdateUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            String duplicateMessage = checkForDuplicateUser();
            if (duplicateMessage != null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        duplicateMessage, "Error"));
                return; // Exit the method if a duplicate is found
            }

            if (editMode) {
                userRepo.update(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "User updated successfully"));
            } else {
                userRepo.save(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "User saved successfully"));
            }

            users = userRepo.findAll(); // Refresh the user list
            user = new User(); // Clear form after submission
            editMode = false; // Reset the edit mode flag
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user"));
        }
    }

    public void deleteUser(User user) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            userRepo.delete(user.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully"));
            users = userRepo.findAll(); // Refresh the user list
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete user"));
        }
    }

    public void prepareEditUser(User user) {
        this.user = user;
        this.editMode = true;
    }

    public void prepareNewUser() {
        this.editMode = false;
    }

    private String checkForDuplicateUser() {
        // Check for duplicate username
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Username already exists";
        }

        // Check for duplicate email
        existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Email already exists";
        }

        // Check for duplicate phone number
        existingUser = userRepo.findByPhone(user.getPhone());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Phone number already exists";
        }

        return null; // No duplicates found
    }

}
