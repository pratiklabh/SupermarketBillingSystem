package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user = new User();
    private List<User> users;

    @EJB
    private UserService userService;

    @PostConstruct
    public void init() {
        users = userService.getAllUsers();
        // Load user data if an ID is present in the request parameter
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userId = facesContext.getExternalContext().getRequestParameterMap().get("id");
        if (userId != null) {
            try {
                user = userService.getUserById(Long.parseLong(userId));
            } catch (NumberFormatException e) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid user ID"));
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            if (user.getId() == null) {
                userService.saveUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully"));
            } else {
                userService.updateUser(user);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
            }
            user = new User(); // Clear form after submission
            users = userService.getAllUsers(); // Refresh the user list
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user"));
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public String prepareEdit(Long userId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            user = userService.getUserById(userId);
            return "editUser?faces-redirect=true"; // Redirect to editUser.xhtml
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found"));
            return null;
        }
    }

}
