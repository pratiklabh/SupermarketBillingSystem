package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.service.UserService;
import java.io.Serializable;
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

    @EJB
    private UserService userService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
