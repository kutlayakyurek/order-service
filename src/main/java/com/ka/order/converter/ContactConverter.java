package com.ka.order.converter;

import com.ka.order.entity.ContactEntity;
import com.ka.swagger.model.Contact;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter implements Converter<Contact, ContactEntity> {

    private final CustomerConverter customerConverter;

    public ContactConverter(final CustomerConverter customerConverter) {
        this.customerConverter = customerConverter;
    }

    @Override
    public ContactEntity convert(final MappingContext<Contact, ContactEntity> mappingContext) {
        final Contact contactDto = mappingContext.getSource();
        return convert(contactDto);
    }

    public ContactEntity convert(Contact contactDto) {
        final ContactEntity contactEntity = new ContactEntity();
        contactEntity.setCustomer(customerConverter.convert(contactDto.getCustomer()));
        contactEntity.setPhoneNumber(contactDto.getPhoneNumber());
        contactEntity.setAddress(contactDto.getAddress());
        contactEntity.setId(contactDto.getId());

        return contactEntity;
    }

}
