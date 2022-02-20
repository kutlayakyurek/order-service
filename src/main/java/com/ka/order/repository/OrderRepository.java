package com.ka.order.repository;

import com.ka.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.contact.customer.id = :customerId")
    List<OrderEntity> findOrdersByCustomerId(Long customerId);
}
