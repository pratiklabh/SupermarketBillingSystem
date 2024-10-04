package com.syntech.sbs.repository;

import com.syntech.sbs.model.Template;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TemplateRepository extends GenericRepository<Template>{
    
    @PersistenceContext(name="sbs")
    private EntityManager entityManager;

    public TemplateRepository() {
        super(Template.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }
    
}
