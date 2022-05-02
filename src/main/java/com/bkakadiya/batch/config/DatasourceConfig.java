package com.bkakadiya.batch.config;

import com.bkakadiya.batch.entity.*;
import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.orm.jpa.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.*;

import javax.sql.*;

@Configuration
@EnableJpaRepositories(basePackages = "com.bkakadiya.batch.repository.rdbms",
        entityManagerFactoryRef = "rdbmsEntityManagerFactory",
        transactionManagerRef= "rdbmsTransactionManager")
public class DatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties rdbmsDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource rdbmsDataSource() {
        return rdbmsDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "rdbmsEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean rdbmsEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(rdbmsDataSource())
                .packages(Customer.class)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager rdbmsTransactionManager(
            final @Qualifier("rdbmsEntityManagerFactory") LocalContainerEntityManagerFactoryBean rdbmsEntityManagerFactory) {
        return new JpaTransactionManager(rdbmsEntityManagerFactory.getObject());
    }



}
