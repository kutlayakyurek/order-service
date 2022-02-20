package com.ka.order.controller;

import com.ka.order.entity.ContactEntity;
import com.ka.order.entity.CustomerEntity;
import com.ka.order.repository.ContactRepository;
import com.ka.order.repository.CustomerRepository;
import com.ka.swagger.api.CustomerApi;
import com.ka.swagger.model.Contact;
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
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public CustomerController(final CustomerRepository customerRepository,
                              final ContactRepository contactRepository,
                              final ModelMapper modelMapper) {

        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<Void> createCustomer(final Customer customer) {
        final CustomerEntity entity = modelMapper.map(customer, CustomerEntity.class);
        customerRepository.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(final BigDecimal id) {
        if (!customerRepository.existsById(id.longValue())) {
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

    @Override
    public ResponseEntity<Void> createContact(final Contact contact) {
        final ContactEntity entity = modelMapper.map(contact, ContactEntity.class);
        contactRepository.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteContact(final BigDecimal contactId) {
        if (!contactRepository.existsById(contactId.longValue())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        contactRepository.deleteById(contactId.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> getContact(final BigDecimal contactId) {
        final Optional<ContactEntity> contactEntity = contactRepository.findById(contactId.longValue());

        if (contactEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Contact contact = modelMapper.map(contactEntity.get(), Contact.class);

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Contact>> getContacts(final BigDecimal customerId) {
        final List<ContactEntity> contacts = contactRepository.findAll();
        final List<Contact> resultList = new ArrayList<>();

        contacts.forEach(c -> resultList.add(modelMapper.map(c, Contact.class)));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateContact(final Contact contact) {
        final Optional<ContactEntity> contactEntityOptional = contactRepository.findById(contact.getId());

        if (contactEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final ContactEntity foundEntity = contactEntityOptional.get();
        foundEntity.setCustomerId(contact.getCustomerId());
        foundEntity.setAddress(contact.getAddress());
        foundEntity.setPhoneNumber(contact.getPhoneNumber());

        contactRepository.save(foundEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
