package com.syntech.sbs.repository;

import com.syntech.sbs.model.BaseIdEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

// T will be replaced by a specific entity class that extends BaseIdEntity 
public abstract class GenericRepository <T extends BaseIdEntity>{ 
    
    protected EntityManager entityManager;
    
    //entityClass holds objects ->represents specific entity class
    // for which repo is being used
    private Class<T> entityClass;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        //get criteria builder instance
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        //create CriteriaQuery object
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        
        //define the root
        Root<T> root = criteriaQuery.from(entityClass);

        //select all attribute of root 
        criteriaQuery.select(root);
        
        
        return entityManager.createQuery(criteriaQuery).getResultList();
        
    }
}