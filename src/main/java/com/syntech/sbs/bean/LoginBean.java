package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.service.UserService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private String username;
    private String password;
    
    @Inject
    private UserService userService;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Login method
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userService.authenticate(username, password);
        
        if (user != null) {
            if ("admin".equals(user.getUsername())) {
                return "adminDashboard?faces-redirect=true";
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Access Denied", "You are not authorized to access this page."));
                return null;
            }
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return null;
        }
    }
}
