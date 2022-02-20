package com.ka.order.converter;

import com.ka.order.entity.CustomerEntity;
import com.ka.swagger.model.Customer;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Converter<Customer, CustomerEntity> {

    @Override
    public CustomerEntity convert(final MappingContext<Customer, CustomerEntity> mappingContext) {
        final Customer customerDto = mappingContext.getSource();
        return convert(customerDto);
    }

    public CustomerEntity convert(final Customer customerDto) {
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setEmail(customerDto.getEmail());

        return customerEntity;
    }
}
