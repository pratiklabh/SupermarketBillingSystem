package com.syntech.sbs.repository;

import com.syntech.sbs.model.BaseIdEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

// T will be replaced by a specific entity class that extends BaseIdEntity 
public abstract class GenericRepository <T extends BaseIdEntity>{ 
    
    protected abstract EntityManager entityManager();
    
    //entityClass holds objects ->represents specific entity class
    // for which repo is being used
    private Class<T> entityClass;
    
    protected CriteriaQuery<T> criteriaQuery;
    protected CriteriaBuilder criteriaBuilder;
    protected Root<T> root;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery;
    }

    public void setCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        this.criteriaQuery = criteriaQuery;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public Root<T> getRoot() {
        return root;
    }

    public void setRoot(Root<T> root) {
        this.root = root;
    }
    
    @PostConstruct
    protected void _startQuery(){
        this.criteriaBuilder = entityManager().getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = this.criteriaQuery.from(entityClass);
    }
    
    public GenericRepository<T> startQuery(){
        this._startQuery();
        return this;
    }
    

    @Transactional
    public void save(T entity){
        entityManager().persist(entity);
    }

    @Transactional
    public void update(T entity){
        entityManager().merge(entity);
    }

    public T findById(Long id){
        return entityManager().find(entityClass, id);
    }

    public void delete(Long id){
            entityManager().remove(entityClass);
    }

    public List<T> findAll(){
        return entityManager().createQuery(criteriaQuery).getResultList();
        
    }
}