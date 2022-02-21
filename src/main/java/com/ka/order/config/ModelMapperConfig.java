package com.ka.order.config;

import com.ka.order.converter.ContactConverter;
import com.ka.order.converter.CustomerConverter;
import com.ka.order.converter.OrderConverter;
import com.ka.order.converter.ProductConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final CustomerConverter customerConvertor;
    private final ContactConverter contactConverter;
    private final OrderConverter orderConverter;
    private final ProductConverter productConverter;

    public ModelMapperConfig(final CustomerConverter customerConvertor,
                             final ContactConverter contactConverter,
                             final OrderConverter orderConverter,
                             final ProductConverter productConverter) {

        this.customerConvertor = customerConvertor;
        this.contactConverter = contactConverter;
        this.orderConverter = orderConverter;
        this.productConverter = productConverter;
    }


    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.addConverter(customerConvertor);
        mapper.addConverter(contactConverter);
        mapper.addConverter(orderConverter);
        mapper.addConverter(productConverter);

        return mapper;
    }
}
