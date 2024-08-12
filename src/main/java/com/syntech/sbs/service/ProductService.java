package com.syntech.sbs.service;

import com.syntech.sbs.model.Product;
import com.syntech.sbs.repository.ProductRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ProductService {
    
    @Inject
    private ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }
    
    public void updateProduct(Product product){
        productRepository.update(product);
    }
    
    public void deleteProduct(Long id){
        productRepository.delete(id);
    }
    
    public List<Product> getAllProducts(){
        return productRepository.getAll();
    }
    
    public Product getProductByCode(Long code){
        return productRepository.getByCode(code);
    }
    
    
}
