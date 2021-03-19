package com.ezreach.customer.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="gst_profile", schema="onboard")
public class GstProfile {

    @Id
    @Column(name="profile_id")
    private UUID profileId;

    @Column(name="user_id")
    private UUID userId;

    @Column(name="gst_details")
    private String gstDetails;

    public GstProfile() {}

    /**
     *
     * @param profileId Generated at the microservice
     * @param userId provided by AWS Cognito
     * @param gstin Customer's GST identification number
     * @param gstDetails GST details fetched from the GST server
     * @param profileType Type of profile
     */
    public GstProfile(UUID profileId, UUID userId, String gstDetails) {
        this.profileId = profileId;
        this.userId = userId;
        this.gstDetails = gstDetails;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getGstDetails() {
        return gstDetails;
    }

    public void setGstDetails(String gstDetails) {
        this.gstDetails = gstDetails;
    }
}
