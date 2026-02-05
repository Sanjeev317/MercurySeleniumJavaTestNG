package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.utils.DataReader;
import com.enterprise.mercury.ui.pages.ClaimCreationClaimantDetailsPage;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.annotations.Test;

/**
 * Test Class for Claim Creation with Claimant Details
 * Tests FNOL wizard flow with policy search and claimant name selection (Steps 1-10)
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("Claim Management")
@Feature("Claim Creation with Claimant Details")
public class ClaimCreationClaimantDetailsTest extends BaseTest {
    
    /**
     * Test claim creation with valid policy number and claimant name selection
     * Steps: 1-10 (Stop at claimant name selection)
     */
    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test claim creation with valid policy search and claimant name selection")
    @Story("Verify user can create claim with valid policy and select claimant name")
    public void testClaimCreationWithValidClaimantDetails() {
        logger.info("Starting test: Claim Creation with Valid Claimant Details");
        
        // Read test data
        JsonNode testData = DataReader.readUITestData("claimCreationClaimantDetailsData.json");
        JsonNode validData = testData.get("validClaimWithClaimantDetails");
        
        String policyNumber = validData.get("policyNumber").asText();
        String lossDate = validData.get("lossDate").asText();
        String lossTime = validData.get("lossTime").asText();
        String timeAmPm = validData.get("timeAmPm").asText();
        String claimantName = validData.get("claimantName").asText();
        
        logger.info("Test Data - Policy: {}, Date: {}, Time: {} {}, Claimant: {}", 
                    policyNumber, lossDate, lossTime, timeAmPm, claimantName);
        
        // Initialize page object
        ClaimCreationClaimantDetailsPage claimPage = new ClaimCreationClaimantDetailsPage();
        
        // Execute claim creation flow (Steps 1-10)
        claimPage.completeClaimCreationWithClaimantDetails(
            policyNumber, 
            lossDate, 
            lossTime, 
            timeAmPm,
            claimantName
        );
        
        logger.info("Completed test: Claim Creation with Valid Claimant Details");
    }
    
    /**
     * Test claim creation with invalid policy number
     * Verify error handling for non-existent policy
     */
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test claim creation with invalid policy number")
    @Story("Verify system handles invalid policy number correctly")
    public void testClaimCreationWithInvalidPolicy() {
        logger.info("Starting test: Claim Creation with Invalid Policy");
        
        // Read test data
        JsonNode testData = DataReader.readUITestData("claimCreationClaimantDetailsData.json");
        JsonNode invalidData = testData.get("invalidPolicyNumber");
        
        String policyNumber = invalidData.get("policyNumber").asText();
        String lossDate = invalidData.get("lossDate").asText();
        String lossTime = invalidData.get("lossTime").asText();
        String timeAmPm = invalidData.get("timeAmPm").asText();
        
        logger.info("Test Data - Invalid Policy: {}, Date: {}, Time: {} {}", 
                    policyNumber, lossDate, lossTime, timeAmPm);
        
        // Initialize page object
        ClaimCreationClaimantDetailsPage claimPage = new ClaimCreationClaimantDetailsPage();
        
        // Navigate to New Claim
        claimPage.clickClaimTabExpand();
        claimPage.clickNewClaimMenuItem();
        
        // Fill policy search with invalid policy number
        claimPage.fillPolicySearchInfo(policyNumber, lossDate, lossTime, timeAmPm);
        
        // Click Search - should show error or no results
        claimPage.clickSearchButton();
        
        logger.info("Completed test: Claim Creation with Invalid Policy");
    }
}
