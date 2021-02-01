package com.ezyreach.profiling.repository;

import com.ezyreach.profiling.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {
}
