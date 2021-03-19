package com.ezreach.customer.profile.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
public class GstRepoTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private GstProfileDao gstProfileDao;

    @Test
    public void testCustomerDaoIsNotNull() {
        assertThat(gstProfileDao).isNotNull();
    }
}
