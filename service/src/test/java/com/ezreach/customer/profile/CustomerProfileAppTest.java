package com.ezreach.customer.profile;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
public class CustomerProfileAppTest {

	@Autowired
	private DataSource dataSource;
	
	@Test
	public void contextLoads() throws Exception {
	}
}
