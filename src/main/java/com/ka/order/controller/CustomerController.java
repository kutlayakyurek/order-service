package com.ka.order.controller;

import com.ka.order.entity.ContactEntity;
import com.ka.order.entity.CustomerEntity;
import com.ka.order.entity.OrderEntity;
import com.ka.order.entity.ProductEntity;
import com.ka.order.repository.ContactRepository;
import com.ka.order.repository.CustomerRepository;
import com.ka.order.repository.OrderRepository;
import com.ka.swagger.api.CustomerApi;
import com.ka.swagger.model.Contact;
import com.ka.swagger.model.Customer;
import com.ka.swagger.model.Order;
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
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public CustomerController(final CustomerRepository customerRepository,
                              final ContactRepository contactRepository,
                              final OrderRepository orderRepository,
                              final ModelMapper modelMapper) {

        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
        this.orderRepository = orderRepository;
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
        foundEntity.setAddress(contact.getAddress());
        foundEntity.setPhoneNumber(contact.getPhoneNumber());

        contactRepository.save(foundEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createOrder(final Order order) {
        final OrderEntity entity = modelMapper.map(order, OrderEntity.class);
        orderRepository.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteOrder(final BigDecimal orderId) {
        if (!orderRepository.existsById(orderId.longValue())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderRepository.deleteById(orderId.longValue());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> getOrder(final BigDecimal orderId) {
        final Optional<OrderEntity> orderEntity = orderRepository.findById(orderId.longValue());

        if (orderEntity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Order contact = modelMapper.map(orderEntity.get(), Order.class);

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getOrders(final BigDecimal customerId) {
        final List<OrderEntity> orders = orderRepository.findAll();
        final List<Order> resultList = new ArrayList<>();

        orders.forEach(o -> resultList.add(modelMapper.map(o, Order.class)));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateOrder(final Order order) {
        final Optional<OrderEntity> orderEntityOptional = orderRepository.findById(order.getId());

        if (orderEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final OrderEntity foundEntity = orderEntityOptional.get();
        foundEntity.setProduct(modelMapper.map(order.getProduct(), ProductEntity.class));
        foundEntity.setContact(modelMapper.map(order.getContact(), ContactEntity.class));

        orderRepository.save(foundEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
