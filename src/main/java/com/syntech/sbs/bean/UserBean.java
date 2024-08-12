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
import javax.inject.Named;

@Named("UserBean")
@ViewScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user = new User();
    private List<User> users;
    

    @EJB
    private UserService userService;
    

    @PostConstruct
    public void init(){
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
    
    public void saveUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            System.out.println("Attempting to save user: " + user.getUsername());
            userService.saveUser(user);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully"));
            System.out.println("User saved successfully: " + user.getUsername());
            user = new User(); // Clear form after submission
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user"));
            e.printStackTrace();
        }
    }
    
    public void deleteUser(User user) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            userService.deleteUser(user.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Success", "User deleted successfully"));
            users = userService.getAllUsers(); // Refresh the user list
        
        } catch (Exception e) {
            
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Failed to delete user"));
            e.printStackTrace();
        }
    }
    
    
    
    public void prepareEditUser(User user) {
        this.user = user;
    }

    public void updateUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            userService.updateUser(user);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
            users = userService.getAllUsers(); // Refresh the user list
            user = new User(); // Clear form after update
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update user"));
            e.printStackTrace();
        }
    }
    
}