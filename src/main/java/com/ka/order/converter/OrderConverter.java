package com.ka.order.converter;

import com.ka.order.entity.OrderEntity;
import com.ka.swagger.model.Order;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements Converter<Order, OrderEntity> {

    private final ContactConverter contactConverter;
    private final ProductConverter productConverter;

    public OrderConverter(final ContactConverter contactConverter, final ProductConverter productConverter) {
        this.contactConverter = contactConverter;
        this.productConverter = productConverter;
    }

    @Override
    public OrderEntity convert(final MappingContext<Order, OrderEntity> mappingContext) {
        final Order orderDto = mappingContext.getSource();

        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setContact(contactConverter.convert(orderDto.getContact()));
        orderEntity.setProduct(productConverter.convert(orderDto.getProduct()));

        return orderEntity;
    }
}
