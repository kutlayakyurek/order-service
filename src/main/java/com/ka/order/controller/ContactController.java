package com.ka.order.controller;

import com.ka.order.entity.ContactEntity;
import com.ka.order.entity.CustomerEntity;
import com.ka.order.repository.ContactRepository;
import com.ka.order.repository.CustomerRepository;
import com.ka.swagger.api.ContactApi;
import com.ka.swagger.model.Contact;
import com.ka.swagger.model.CreateContactRequest;
import com.ka.swagger.model.UpdateContactRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class ContactController implements ContactApi {

    private final CustomerRepository customerRepository;
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactController(final ModelMapper modelMapper,
                             final CustomerRepository customerRepository,
                             final ContactRepository contactRepository) {

        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public ResponseEntity<Void> createContact(final CreateContactRequest request) {
        final Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(request.getCustomerId());

        if (customerEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final ContactEntity entity = new ContactEntity();
        entity.setCustomer(customerEntityOptional.get());
        entity.setAddress(request.getAddress());
        entity.setPhoneNumber(request.getPhoneNumber());

        contactRepository.save(entity);

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
    public ResponseEntity<Void> updateContact(final UpdateContactRequest request) {
        final Optional<ContactEntity> contactEntityOptional = contactRepository.findById(request.getId());

        if (contactEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final ContactEntity foundEntity = contactEntityOptional.get();
        foundEntity.setAddress(request.getAddress());
        foundEntity.setPhoneNumber(request.getPhoneNumber());

        contactRepository.save(foundEntity);

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
}
