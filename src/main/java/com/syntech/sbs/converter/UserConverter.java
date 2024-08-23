package com.syntech.sbs.converter;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
   

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter{
    
    @Inject
    private UserRepository userRepository;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty() || value.length() == 0 || value.equals("")) {
            return null;
        }        
        
        return userRepository.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || o.equals("")) {
            return "";
        }
        return String.valueOf(((User) o).getId());
    }
}