package com.syntech.sbs.listener;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AdminPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String requestURI = context.getExternalContext().getRequestContextPath() + context.getExternalContext().getRequestServletPath();
        String loginURI = context.getExternalContext().getRequestContextPath() + "/adminLogin.xhtml";

        boolean loggedIn = (session != null && session.getAttribute("valid_user") != null);
        boolean loginRequest = requestURI.equals(loginURI);
        boolean adminRequest = requestURI.startsWith(context.getExternalContext().getRequestContextPath() + "/");

        if (!loggedIn && adminRequest && !loginRequest) {
            try {
                context.getExternalContext().redirect(loginURI);
            } catch (IOException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Redirect Error", "An error occurred while redirecting to the login page."));
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
