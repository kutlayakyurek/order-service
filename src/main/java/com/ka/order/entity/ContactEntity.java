package com.ka.order.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TBL_CONTACT")
@EqualsAndHashCode(callSuper = true)
public class ContactEntity extends AbstractEntity {

    private long customerId;
    private String address;
    private String phoneNumber;

}
