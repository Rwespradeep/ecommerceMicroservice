package com.rvs.productService.service;

import com.rvs.productService.dto.ProductRequest;
import com.rvs.productService.dto.ProductResponse;
import com.rvs.productService.model.Product;
import com.rvs.productService.repository.ProductRepostiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepostiory productRepostiory;

//    public ProductService(ProductRepostiory productRepostiory) {
//        this.productRepostiory = productRepostiory;
//    } instead of we using this everytime, we can use requiredArgs constructor annotation.

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepostiory.save(product);
        log.info("product is {} saved", product.getId());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepostiory.findAll();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().
                id(product.getId()).
                name(product.getName()).
                description(product.getDescription()).
                price(product.getPrice()).
                build();
    }

}
