package com.ka.order.controller;

import com.ka.order.entity.ContactEntity;
import com.ka.order.entity.OrderEntity;
import com.ka.order.entity.ProductEntity;
import com.ka.order.repository.ContactRepository;
import com.ka.order.repository.OrderRepository;
import com.ka.order.repository.ProductRepository;
import com.ka.swagger.api.OrderApi;
import com.ka.swagger.model.CreateOrderRequest;
import com.ka.swagger.model.Order;
import com.ka.swagger.model.UpdateOrderRequest;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class OrderController implements OrderApi {

    private final OrderRepository orderRepository;
    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;


    public OrderController(final OrderRepository orderRepository,
                           final ContactRepository contactRepository,
                           final ProductRepository productRepository,
                           final ModelMapper modelMapper,
                           final RabbitTemplate rabbitTemplate) {

        this.orderRepository = orderRepository;
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseEntity<Void> createOrder(final CreateOrderRequest request) {
        final Optional<ContactEntity> contactEntityOptional = contactRepository.findById(request.getContactId());
        final Optional<ProductEntity> productEntityOptional = productRepository.findById(request.getProductId());

        if (contactEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (productEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final OrderEntity entity = new OrderEntity();
        entity.setContact(contactEntityOptional.get());
        entity.setProduct(productEntityOptional.get());

        orderRepository.save(entity);
        rabbitTemplate.convertAndSend(entity);

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
    public ResponseEntity<Void> updateOrder(final UpdateOrderRequest request) {
        final Optional<OrderEntity> orderEntityOptional = orderRepository.findById(request.getId());

        if (orderEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Optional<ContactEntity> contactEntityOptional = contactRepository.findById(request.getContactId());
        final Optional<ProductEntity> productEntityOptional = productRepository.findById(request.getProductId());

        final OrderEntity foundEntity = orderEntityOptional.get();

        contactEntityOptional.ifPresent(foundEntity::setContact);
        productEntityOptional.ifPresent(foundEntity::setProduct);

        orderRepository.save(foundEntity);

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
}
