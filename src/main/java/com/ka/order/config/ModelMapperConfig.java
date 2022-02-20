package com.ka.order.config;

import com.ka.order.converter.CustomerConvertor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final CustomerConvertor customerConvertor;

    public ModelMapperConfig(final CustomerConvertor customerConvertor) {
        this.customerConvertor = customerConvertor;
    }


    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.addConverter(customerConvertor);

        return mapper;
    }
}
