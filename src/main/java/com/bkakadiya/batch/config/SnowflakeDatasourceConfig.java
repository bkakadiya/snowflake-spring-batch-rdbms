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
@EnableJpaRepositories(basePackages = "com.bkakadiya.batch.repository.snowflake",
        entityManagerFactoryRef = "snowflakeEntityManagerFactory",
        transactionManagerRef= "snowflakeTransactionManager")
public class SnowflakeDatasourceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.snowflake.datasource")
    public DataSourceProperties snowflakeDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean(name="snowflakeDatasource")
    @ConfigurationProperties("app.datasource.snowflake.datasource.configuration")
    public DataSource snowflakeDataSource() {
        return snowflakeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "snowflakeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean snowflakeEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(snowflakeDataSource())
                .packages(Customer.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager snowflakeTransactionManager(
            final @Qualifier("snowflakeEntityManagerFactory") LocalContainerEntityManagerFactoryBean snowflakeEntityManagerFactory) {
        return new JpaTransactionManager(snowflakeEntityManagerFactory.getObject());
    }
}
