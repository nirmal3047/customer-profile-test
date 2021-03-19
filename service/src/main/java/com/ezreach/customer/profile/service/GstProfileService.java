package com.ezreach.customer.profile.service;

import com.ezreach.customer.profile.entity.GstProfile;
import com.ezreach.customer.profile.exception.GstNotFoundException;
import com.ezreach.customer.profile.exception.GstServerDownException;
import com.ezreach.customer.profile.repository.GstProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GstProfileService {

	private WebClient webClient;
    private GstProfileDao gstProfileDao;

    @Autowired
    public GstProfileService(GstProfileDao gstProfileDao, WebClient webClient) {
        this.gstProfileDao = gstProfileDao;
        this.webClient = webClient;
    }

    /**
     * Creates a GST Profile for given userId and GSTIN after fetching details from GST Server
     * And update user group in cognito
     * @param userId
     * @return
     */
    @Transactional
    public GstProfile createGstProfile(UUID userId, String gstin) {
        GstProfile gstProfile = new GstProfile();
        gstProfile.setUserId(userId);
        gstProfile.setProfileId(UUID.randomUUID());
        
        String gstDetails = getDataFromGst(gstin);
        gstProfile.setGstDetails(gstDetails);

        updateCognitoUserPool();   //Not functional yet
        
        return gstProfileDao.save(gstProfile);
    }
    
    /**
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    public GstProfile getGstProfile(UUID userId) throws Exception {
    	Optional<GstProfile> gstProfile = gstProfileDao.findByUserId(userId);
    	if(! gstProfile.isPresent()) {
    		throw new GstNotFoundException("GST record not available!", "");
    	}
    	return gstProfile.get();
    }

    /**
     * Fetch GST data from GST server
     * @return
     */
    public String getDataFromGst(String gstin) {
        String gstDetails = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //String gstDetails = "dummy data";
        return gstDetails;
    }

    /**
     * Adds the user to EZREACH_INVOICE_GROUP in AWS Cognito
     */
    public void updateCognitoUserPool() {
        //Logic to be written
    }

}
