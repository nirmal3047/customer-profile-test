package com.ezreach.customer.profile.repository;

import com.ezreach.customer.profile.entity.GstProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GstProfileDao extends JpaRepository<GstProfile, UUID> {

    /**
     *
     * @param userId
     * @return
     */
    @Query("SELECT g FROM GstProfile g WHERE g.userId = ?1")
    public Optional<GstProfile> findByUserId(UUID userId);
}
