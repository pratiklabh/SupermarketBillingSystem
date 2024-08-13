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
            String duplicateMessage = checkForDuplicateUser();
            if (duplicateMessage != null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        duplicateMessage, "Error"));
                return; // Exit the method if a duplicate is found
            }

            if (editMode) {
                userService.updateUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "User updated successfully"));
            } else {
                userService.saveUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "User saved successfully"));
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

    private String checkForDuplicateUser() {
        // Check if updating and the current user's ID is not null
        if (editMode && user.getId() != null) {
            User existingUserByUsername = userService.findUserByUsername(user.getUsername());
            if (existingUserByUsername != null && !existingUserByUsername.getId().equals(user.getId())) {
                return "Username already exists";
            }

            User existingUserByEmail = userService.findUserByEmail(user.getEmail());
            if (existingUserByEmail != null && !existingUserByEmail.getId().equals(user.getId())) {
                return "Email already exists";
            }

            User existingUserByPhone = userService.findUserByPhone(user.getPhone());
            if (existingUserByPhone != null && !existingUserByPhone.getId().equals(user.getId())) {
                return "Phone number already exists";
            }
        } else {
            // Check for duplicates without considering the current user
            if (userService.findUserByUsername(user.getUsername()) != null) {
                return "Username already exists";
            }

            if (userService.findUserByEmail(user.getEmail()) != null) {
                return "Email already exists";
            }

            if (userService.findUserByPhone(user.getPhone()) != null) {
                return "Phone number already exists";
            }
        }
        return null; // No duplicates found
    }

}
