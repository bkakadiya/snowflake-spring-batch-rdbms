package com.bkakadiya.batch.mapper;

import com.bkakadiya.batch.entity.*;
import org.springframework.jdbc.core.*;

import java.sql.*;


public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(rs.getLong("C_CUSTKEY"),
                rs.getString("C_NAME"),
                rs.getString("C_ADDRESS"),
                rs.getLong("C_NATIONKEY"),
                rs.getString("C_PHONE"),
                rs.getDouble("C_ACCTBAL"),
                rs.getString("C_MKTSEGMENT"),
                rs.getString("C_COMMENT")
        );
    }
}
