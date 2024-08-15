package com.syntech.sbs.repository;

import com.syntech.sbs.model.BaseIdEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

// T will be replaced by a specific entity class that extends BaseIdEntity 
public class GenericRepository <T extends BaseIdEntity>{ 

    @PersistenceContext(name = "sbs")
    private EntityManager entityManager;
    
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
    public void save(T entity){
        entityManager.persist(entity);
    }
    
    @Transactional
    public void update(T entity){
        entityManager.merge(entity);
    }
    
    public T findById(Long id){
        return entityManager.find(entityClass, id);
    }
    
    public void delete(Long id){
        T entityClass = findById(id);
        if(entityClass != null){
            entityManager.remove(entityClass);
        }
    }
    
    public List<T> findAll(){
        return entityManager.createQuery(
                "SELECT t from "+entityClass
                        .getSimpleName()+" t", entityClass)
                        .getResultList();
    }
}
