package com.ezreach.customer.profile.repository;

import com.ezreach.customer.profile.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerDao extends JpaRepository<Customer, UUID> {

    /**
     * Fetch customer's details from the database with given userId
     */
    @Query("SELECT c FROM Customer c WHERE c.userId = ?1")
    public Optional<Customer> findByUserId(UUID userID);
}
