package com.bkakadiya.batch.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @Column(name = "C_CUSTKEY")
    private Long id;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_ADDRESS")
    private String address;

    @Column(name = "C_NATIONKEY")
    private Long nationKey;

    @Column(name = "C_PHONE")
    private String phone;

    @Column(name = "C_ACCTBAL")
    private Double accountBal;

    @Column(name = "C_MKTSEGMENT")
    private String marketSegment;

    @Column(name = "C_COMMENT")
    private String comment;
}
