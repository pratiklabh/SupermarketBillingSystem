package com.syntech.sbs.repository;

import com.syntech.sbs.model.BaseIdEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

// T will be replaced by a specific entity class that extends BaseIdEntity 
public abstract class GenericRepository <T extends BaseIdEntity>{ 
    
    //entityClass holds objects ->represents specific entity class
    // for which repo is being used
    private Class<T> entityClass;

    public GenericRepository() {
    }

    //allows repo to be aware of specific entity class it is handling
    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Transactional
    public abstract void save(T entity);
    
    @Transactional
    public abstract void update(T entity);
    
    public abstract void delete(Long id);
    
    public abstract T findById(Long id);
        
    public abstract List<T> findAll();
}
