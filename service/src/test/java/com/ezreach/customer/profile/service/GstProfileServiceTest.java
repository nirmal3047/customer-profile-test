package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.GstProfile;
import com.ezreach.customer.profile.repository.GstProfileDao;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName="dataSource")
class GstProfileServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private GstProfileService gstProfileService;

    @MockBean
    private GstProfileDao gstProfileDao;

    @Test
    public void test() {
        assertThat(gstProfileService).isNotNull();
    }

    @Test
    @DisplayName("Test createGstProfile - Success")
    public void test0() {

    }

    @Test
    @DisplayName("Test createGstProfile - Fail - GST Not Found")
    public void test1() {

    }

    @Test
    @DisplayName("Test createGstProfile - GST Server Cannot be reached")
    public void test2() {

    }

    @Test
    @DisplayName("Test getGstProfile - Success")
    public void test3() {
        GstProfile mockGstProfile = new GstProfile();
        UUID userId = UUID.randomUUID();
        doReturn(Optional.of(mockGstProfile)).when(gstProfileDao).findByUserId(userId); //Edit this

    }

    @Test
    @DisplayName("Test getGstProfile - Fail - GST Profile Not Found")
    public void test4() {

    }
}