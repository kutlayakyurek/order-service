package com.ka.order.controller;

import com.ka.order.entity.CustomerEntity;
import com.ka.order.repository.CustomerRepository;
import com.ka.swagger.api.CustomerApi;
import com.ka.swagger.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerController(final CustomerRepository customerRepository, final ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<Void> createCustomer(final Customer customer) {
        final CustomerEntity entity = modelMapper.map(customer, CustomerEntity.class);
        customerRepository.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(final BigDecimal id) {
        final Optional<CustomerEntity> customerEntity = customerRepository.findById(id.longValue());

        if (customerEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerRepository.deleteById(id.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> getCustomer(final BigDecimal id) {
        final Optional<CustomerEntity> customerEntity = customerRepository.findById(id.longValue());

        if (customerEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Customer customer = modelMapper.map(customerEntity.get(), Customer.class);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        final List<CustomerEntity> customers = customerRepository.findAll();
        final List<Customer> resultList = new ArrayList<>();

        customers.forEach(c -> resultList.add(modelMapper.map(c, Customer.class)));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCustomer(final Customer customer) {
        final Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customer.getId());

        if (customerEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final CustomerEntity foundEntity = customerEntityOptional.get();
        foundEntity.setFirstName(customer.getFirstName());
        foundEntity.setLastName(customer.getLastName());
        foundEntity.setEmail(customer.getEmail());

        customerRepository.save(foundEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
