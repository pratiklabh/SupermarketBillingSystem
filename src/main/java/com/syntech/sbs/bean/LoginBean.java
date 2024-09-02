package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable{

    private String username;
    private String password;

    @Inject
    private UserRepository userRepo;

    @Inject
    private SessionBean sessionBean;

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
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        User user = userRepo.findByUsernameAndPassword(username, password);

        if (user != null) {
            sessionBean.storeUserInSession(user);
            return "userList?faces-redirect=true";
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Invalid credentials", "Please try again."));
            return null;
        }
    }

}
