package com.ka.order.repository;

import com.ka.order.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query("SELECT c FROM ContactEntity c WHERE c.customer.id = :customerId")
    List<ContactEntity> findContactsByCustomerId(Long customerId);
}
