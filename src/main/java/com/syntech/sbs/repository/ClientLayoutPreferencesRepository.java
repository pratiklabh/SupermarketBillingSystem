package com.syntech.sbs.repository;

import com.syntech.sbs.model.ClientLayoutPreferences;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientLayoutPreferencesRepository extends GenericRepository<ClientLayoutPreferences>{

    @PersistenceContext(name="sbs")
    private EntityManager entityManager;

    public ClientLayoutPreferencesRepository() {
        super(ClientLayoutPreferences.class);
    }
    
    @Override
    protected EntityManager entityManager() {
        return entityManager;
    }
    
    

}
