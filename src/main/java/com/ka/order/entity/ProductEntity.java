package com.ka.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TBL_PRODUCT")
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends AbstractEntity {

    private String productType;
    private String productPackage;

}
