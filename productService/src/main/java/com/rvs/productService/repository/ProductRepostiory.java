package com.rvs.productService.repository;

import com.rvs.productService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ProductRepostiory extends MongoRepository<Product, String> {
}
