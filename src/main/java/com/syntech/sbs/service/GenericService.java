package com.syntech.sbs.service;

import com.syntech.sbs.model.BaseIdEntity;
import com.syntech.sbs.repository.GenericRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GenericService<T extends BaseIdEntity> {

    @Inject
    private GenericRepository<T> genericRepo;

    public void saveEntity(T entity) {
        genericRepo.save(entity);
    }

    public void updateEntity(T entity) {
        genericRepo.update(entity);
    }

    public void deleteEntity(Long id) {
        genericRepo.delete(id);
    }

    public T findEntityById(Long id) {
        return (T) genericRepo.findById(id);
    }

    public List<T> findAllEntities() {
        return genericRepo.findAll();
    }
}
