package com.ezyreach.profiling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class CustomerRepositoryTestConfiguration {

    @Bean
    public DataSource dataSource () {

        //Setting up data source
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        return dataSource;
    }
}
