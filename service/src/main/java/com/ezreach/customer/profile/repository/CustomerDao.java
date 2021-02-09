package com.ezreach.customer.profile.repository;

import com.ezreach.customer.profile.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerDao extends JpaRepository<Customer, UUID> {
}
