package com.example.product_order_service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.product_order_service.entity.Product;
import com.example.product_order_service.repo.ProductRepo;

@Service
public class ProductService {
    
    @Autowired
    ProductRepo productRepo;

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()) {
            return productRepo.save(product);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
