package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("UserBean")
@ViewScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private User user;
    
    private List<User> users;
    private boolean editMode = false;

    @EJB
    private UserService userService;

    @PostConstruct
    public void init() {
        users = userService.getAllUsers();
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
            if (editMode) {
                userService.updateUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
            } else {
                userService.saveUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully"));
            }
            users = userService.getAllUsers(); // Refresh the user list
            user = new User(); // Clear form after submission
            editMode = false; // Reset the edit mode flag
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user"));
        }
    }

    public void deleteUser(User user) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            if (facesContext.isValidationFailed()) {
            // If validation fails, let the dialog remain open
            return;
        }
            userService.deleteUser(user.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully"));
            users = userService.getAllUsers(); // Refresh the user list
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
}
