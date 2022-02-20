package com.ka.order.converter;

import com.ka.order.entity.ProductEntity;
import com.ka.swagger.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<Product, ProductEntity> {

    @Override
    public ProductEntity convert(final MappingContext<Product, ProductEntity> mappingContext) {
        final Product productDto = mappingContext.getSource();
        return convert(productDto);
    }

    public ProductEntity convert(final Product productDto) {
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDto.getId());
        productEntity.setProductPackage(productDto.getProductPackage());
        productEntity.setProductType(productDto.getProductType().getValue());

        return productEntity;
    }
}
