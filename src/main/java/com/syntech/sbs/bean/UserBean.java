package com.syntech.sbs.bean;

import com.syntech.sbs.model.GenericLazyDataModel;
import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pageSize;

    @Inject
    private User user;

    private List<User> users;
    private boolean editMode = false;

    private GenericLazyDataModel<User> lazyUsers;

    @Inject
    private UserRepository userRepo;

    @PostConstruct
    public void init() {
        user = new User();
        lazyUsers = new GenericLazyDataModel<>(userRepo, User.class);

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        lazyUsers.setRowCount(userRepo.countEntity(new HashMap<>()));
    }

    public GenericLazyDataModel<User> getLazyUsers() {
        return lazyUsers;
    }

    public void setLazyUsers(GenericLazyDataModel<User> lazyUsers) {
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
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", duplicateMessage));
                return;
            }

            if (editMode) {
                userRepo.update(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
            } else {
                userRepo.save(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully"));
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
        this.user = user;  // Set the selected user for editing
        this.editMode = true;  // Set the edit mode to true
    }

    public void prepareNewUser() {
        this.user = new User();  // Reset the user object to a new instance
        this.editMode = false;  // Set the edit mode to false
    }

    private String checkForDuplicateUser() {
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Username already exists";
        }

        existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Email already exists";
        }

        existingUser = userRepo.findByPhone(user.getPhone());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return "Phone number already exists";
        }

        return null; // No duplicates found
    }
    
    public int getTotalCustomers() {
        return userRepo.findAll().size();
    }
}
