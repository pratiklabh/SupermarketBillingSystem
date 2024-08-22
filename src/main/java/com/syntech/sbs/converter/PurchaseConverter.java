package com.syntech.sbs.converter;

import com.syntech.sbs.model.Purchase;
import com.syntech.sbs.repository.PurchaseRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter("purchaseConverter")
public class PurchaseConverter implements Converter {

    @Inject
    private PurchaseRepository purchaseRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return purchaseRepository.findById(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Conversion error: Invalid Purchase ID", e);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof Purchase) {
            Purchase purchase = (Purchase) object;
            return String.valueOf(purchase.getId());
        } else {
            // Log the unexpected object type
            System.err.println("Conversion error: Object is of type " + object.getClass().getName());
            throw new RuntimeException("Conversion error: Object is not of type Purchase");
        }
    }

}
