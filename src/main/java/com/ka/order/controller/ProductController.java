package com.ka.order.controller;

import com.ka.order.entity.ProductEntity;
import com.ka.order.repository.ProductRepository;
import com.ka.swagger.api.ProductApi;
import com.ka.swagger.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements ProductApi {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductController(final ProductRepository productRepository, final ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> createProduct(final Product product) {
        final ProductEntity entity = modelMapper.map(product, ProductEntity.class);
        productRepository.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(final BigDecimal id) {
        if (!productRepository.existsById(id.longValue())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(id.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> getProduct(final BigDecimal id) {
        final Optional<ProductEntity> productEntity = productRepository.findById(id.longValue());

        if (productEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Product product = modelMapper.map(productEntity.get(), Product.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        final List<ProductEntity> products = productRepository.findAll();
        final List<Product> resultList = new ArrayList<>();

        products.forEach(p -> resultList.add(modelMapper.map(p, Product.class)));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateProduct(final Product product) {
        final Optional<ProductEntity> productEntityOptional = productRepository.findById(product.getId());

        if (productEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final ProductEntity foundEntity = productEntityOptional.get();
        foundEntity.setProductType(product.getProductType().getValue());
        foundEntity.setProductPackage(product.getProductPackage());

        productRepository.save(foundEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
