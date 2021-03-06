package com.ka.order.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TBL_CONTACT")
@EqualsAndHashCode(callSuper = true)
public class ContactEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private String address;
    private String phoneNumber;

}
