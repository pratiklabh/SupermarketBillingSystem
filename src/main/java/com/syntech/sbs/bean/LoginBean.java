package com.syntech.sbs.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private String username;
    private String password;

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
        if ("admin".equals(username) && "admin".equals(password)) {
            return "adminDashboard?faces-redirect=true";
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return null;
        }
    }
}
