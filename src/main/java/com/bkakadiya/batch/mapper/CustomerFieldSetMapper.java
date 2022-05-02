package com.bkakadiya.batch.mapper;

import com.bkakadiya.batch.entity.Customer;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;



public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) {
        return new Customer(fieldSet.readLong("C_CUSTKEY"),
                fieldSet.readString("C_NAME"),
                fieldSet.readString("C_ADDRESS"),
                fieldSet.readLong("C_NATIONKEY"),
                fieldSet.readString("C_PHONE"),
                fieldSet.readDouble("C_ACCTBAL"),
                fieldSet.readString("C_MKTSEGMENT"),
                fieldSet.readString("C_COMMENT")
        );
    }
}
