package com.syntech.sbs.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="template")
public class Template extends BaseIdEntity{
    
    @Lob
    private String template;

    public Template() {
    }

    public Template(String template) {
        this.template = template;
    }

    
    
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.template);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Template other = (Template) obj;
        return Objects.equals(this.template, other.template);
    }
    
    

    
}
