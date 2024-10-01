package com.syntech.sbs.repository;

import com.syntech.sbs.model.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ContactRepository extends GenericRepository<Contact>{

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;
    
    public ContactRepository() {
        super(Contact.class);
    }

    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }

}
