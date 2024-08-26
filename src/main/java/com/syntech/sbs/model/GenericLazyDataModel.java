package com.syntech.sbs.model;

import com.syntech.sbs.repository.GenericRepository;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public class GenericLazyDataModel<T extends BaseIdEntity> extends LazyDataModel<T> {

    private GenericRepository<T> genericRepo;

    public GenericLazyDataModel(GenericRepository<T> genericRepo,Class<T> entityClass) {
        this.genericRepo = genericRepo;
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return genericRepo.countEntity(filterBy);
    }

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        
        return genericRepo.getEntity(first, pageSize, sortBy, filterBy);
    }

    
}
