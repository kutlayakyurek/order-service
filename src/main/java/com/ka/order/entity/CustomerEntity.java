package com.ka.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "TBL_CUSTOMER")
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;

}
