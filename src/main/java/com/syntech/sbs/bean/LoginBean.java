package com.syntech.sbs.bean;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import static org.primefaces.component.focus.FocusBase.PropertyKeys.context;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private String username;
    private String password;

    @Inject
    private UserRepository userRepo;

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
            session.setAttribute("valid_user", user);
            session.setAttribute("name", user.getName());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("phone", user.getPhone());
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(120);

            return "userList?faces-redirect=true";
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Invalid credentials", "Please try again."));
            return null;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.invalidate();
        return "adminLogin?faces-redirect=true";
    }

}
