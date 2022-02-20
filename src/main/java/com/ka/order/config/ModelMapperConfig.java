package com.ka.order.config;

import com.ka.order.converter.CustomerConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final CustomerConverter customerConvertor;

    public ModelMapperConfig(final CustomerConverter customerConvertor) {
        this.customerConvertor = customerConvertor;
    }


    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.addConverter(customerConvertor);

        return mapper;
    }
}
