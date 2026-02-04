package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.utils.DataReader;
import com.enterprise.mercury.ui.pages.ClaimCreationPage;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Claim Creation Test Suite
 * Tests for Claim Creation and Policy Search functionality using JSON test data
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("Claim Management")
@Feature("Claim Creation")
public class ClaimCreationTest extends BaseTest {
    
    /**
     * Test successful claim creation with valid policy search
     */
    @Test(priority = 1, description = "Verify new claim creation with valid policy search data")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Create New Claim with Policy Search")
    @Description("Test to verify user can successfully create a new claim and search for policy with valid data. " +
                 "This test covers the complete flow from navigating to claim center, selecting policy type, " +
                 "entering policy details, personal information, and executing policy search.")
    public void testCreateClaimWithValidPolicySearch() {
        logger.info("Starting testCreateClaimWithValidPolicySearch");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("claimCreationData.json");
        String policyType = testData.get("validPolicySearch").get("policyType").asText();
        String type = testData.get("validPolicySearch").get("type").asText();
        String policyNumber = testData.get("validPolicySearch").get("policyNumber").asText();
        String firstName = testData.get("validPolicySearch").get("firstName").asText();
        String lastName = testData.get("validPolicySearch").get("lastName").asText();
        String date = testData.get("validPolicySearch").get("date").asText();
        String time = testData.get("validPolicySearch").get("time").asText();
        String expectedResult = testData.get("validPolicySearch").get("expectedResult").asText();
        
        logger.info("Test data loaded - Policy Number: {}, First Name: {}, Last Name: {}", 
                    policyNumber, firstName, lastName);
        
        // Create page object
        ClaimCreationPage claimPage = new ClaimCreationPage();
        
        // Navigate to New Claim page
        claimPage.clickClaimTabExpand();
        claimPage.clickNewClaimMenuItem();
        
        // Wait for page load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify New Claim page is displayed
        // Assert.assertTrue(claimPage.isNewClaimPageDisplayed(), 
        //                  "New Claim page should be displayed");
        
        // Fill policy information
        claimPage.fillPolicyInformation(policyType, type, policyNumber);
        
        logger.info("Policy information filled successfully");
        
        // Fill personal information
        claimPage.fillPersonalInformation(firstName, lastName, date, time);
        claimPage.enterTimeOfLossAMPM();

        
        
        logger.info("Personal information filled successfully");
        
        // Click Search button
        claimPage.clickSearchButton();
        
        // Wait for search results
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify search results are displayed
        Assert.assertTrue(claimPage.isSearchResultsDisplayed(), 
                         "Policy search results should be displayed");
        
        String searchResultText = claimPage.getSearchResultsText();
        logger.info("Search results displayed: {}", searchResultText);
        
        Assert.assertTrue(searchResultText.contains("Policy") || searchResultText.contains("search"), 
                         "Search results should contain policy information");
        
        logger.info("testCreateClaimWithValidPolicySearch completed successfully");
    }
    
    /**
     * Test claim creation with invalid policy number
     */
    // @Test(priority = 2, description = "Verify error handling with invalid policy number")
    // @Severity(SeverityLevel.CRITICAL)
    // @Story("Negative Testing - Invalid Policy Search")
    // @Description("Test to verify appropriate error message is displayed when searching with invalid policy number. " +
    //              "This test validates the system's error handling and validation mechanisms.")
    // public void testCreateClaimWithInvalidPolicyNumber() {
    //     logger.info("Starting testCreateClaimWithInvalidPolicyNumber");
        
    //     // Read test data from JSON
    //     JsonNode testData = DataReader.readUITestData("claimCreationData.json");
    //     String policyType = testData.get("invalidPolicySearch").get("policyType").asText();
    //     String type = testData.get("invalidPolicySearch").get("type").asText();
    //     String policyNumber = testData.get("invalidPolicySearch").get("policyNumber").asText();
    //     String firstName = testData.get("invalidPolicySearch").get("firstName").asText();
    //     String lastName = testData.get("invalidPolicySearch").get("lastName").asText();
    //     String date = testData.get("invalidPolicySearch").get("date").asText();
    //     String time = testData.get("invalidPolicySearch").get("time").asText();
    //     String expectedError = testData.get("invalidPolicySearch").get("expectedError").asText();
        
    //     logger.info("Test data loaded - Invalid Policy Number: {}", policyNumber);
        
    //     // Create page object
    //     ClaimCreationPage claimPage = new ClaimCreationPage();
        
    //     // Navigate to New Claim page
    //     claimPage.clickClaimTabExpand();
    //     claimPage.clickNewClaimMenuItem();
        
    //     // Wait for page load
    //     try {
    //         Thread.sleep(2000);
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //     }
        
    //     // Verify New Claim page is displayed
    //     Assert.assertTrue(claimPage.isNewClaimPageDisplayed(), 
    //                      "New Claim page should be displayed");
        
    //     // Complete policy search with invalid data
    //     claimPage.completePolicySearch(policyType, type, policyNumber, firstName, lastName, date, time);
        
    //     logger.info("Policy search executed with invalid data");
        
    //     // Wait for error message
    //     try {
    //         Thread.sleep(2000);
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //     }
        
    //     // Verify error message is displayed
    //     Assert.assertTrue(claimPage.isErrorMessageDisplayed(), 
    //                      "Error message should be displayed for invalid policy number");
        
    //     String actualError = claimPage.getErrorMessage();
    //     logger.info("Error message displayed: {}", actualError);
        
    //     Assert.assertTrue(actualError.contains("policy") || actualError.contains("not found") || 
    //                      actualError.contains("invalid"), 
    //                      "Error message should indicate invalid policy");
        
    //     logger.info("testCreateClaimWithInvalidPolicyNumber completed successfully");
    // }
    
    // /**
    //  * Test claim creation workflow step by step
    //  */
    // @Test(priority = 3, description = "Verify claim creation workflow with step-by-step validation")
    // @Severity(SeverityLevel.NORMAL)
    // @Story("Claim Creation Workflow")
    // @Description("Test to verify each step of claim creation workflow is functioning correctly. " +
    //              "This test validates navigation, dropdown selections, field inputs, and search functionality.")
    // public void testClaimCreationWorkflow() {
    //     logger.info("Starting testClaimCreationWorkflow");
        
    //     // Read test data from JSON
    //     JsonNode testData = DataReader.readUITestData("claimCreationData.json");
    //     String policyType = testData.get("validPolicySearch").get("policyType").asText();
    //     String type = testData.get("validPolicySearch").get("type").asText();
    //     String policyNumber = testData.get("validPolicySearch").get("policyNumber").asText();
    //     String firstName = testData.get("validPolicySearch").get("firstName").asText();
    //     String lastName = testData.get("validPolicySearch").get("lastName").asText();
    //     String date = testData.get("validPolicySearch").get("date").asText();
    //     String time = testData.get("validPolicySearch").get("time").asText();
        
    //     logger.info("Test data loaded for workflow test");
        
    //     // Create page object
    //     ClaimCreationPage claimPage = new ClaimCreationPage();
        
    //     // Step 1: Click Claim Tab expand button
    //     claimPage.clickClaimTabExpand();
    //     logger.info("Step 1: Clicked Claim Tab expand button");
        
    //     // Step 2: Click New Claim menu item
    //     claimPage.clickNewClaimMenuItem();
    //     logger.info("Step 2: Clicked New Claim menu item");
        
    //     // Wait for page load
    //     try {
    //         Thread.sleep(2000);
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //     }
        
    //     // Step 3: Verify New Claim page is displayed
    //     Assert.assertTrue(claimPage.isNewClaimPageDisplayed(), 
    //                      "Step 3: New Claim page should be displayed");
    //     logger.info("Step 3: New Claim page is displayed");
        
    //     // Step 4: Select Policy Type
    //     claimPage.selectPolicyType(policyType);
    //     logger.info("Step 4: Selected Policy Type: {}", policyType);
        
    //     // Step 5: Select Type
    //     claimPage.selectType(type);
    //     logger.info("Step 5: Selected Type: {}", type);
        
    //     // Step 6-7: Enter Policy Number
    //     claimPage.enterPolicyNumber(policyNumber);
    //     logger.info("Step 6-7: Entered Policy Number: {}", policyNumber);
        
    //     // Step 8-9: Enter First Name
    //     claimPage.enterFirstName(firstName);
    //     logger.info("Step 8-9: Entered First Name: {}", firstName);
        
    //     // Step 10-11: Enter Last Name
    //     claimPage.enterLastName(lastName);
    //     logger.info("Step 10-11: Entered Last Name: {}", lastName);
        
    //     // Step 12-13: Enter Date
    //     claimPage.enterDate(date);
    //     logger.info("Step 12-13: Entered Date: {}", date);
        
    //     // Step 14-15: Enter Time
    //     claimPage.enterTime(time);
    //     logger.info("Step 14-15: Entered Time: {}", time);
        
    //     // Step 16: Click Search button
    //     claimPage.clickSearchButton();
    //     logger.info("Step 16: Clicked Search button");
        
    //     // Wait for search execution
    //     try {
    //         Thread.sleep(3000);
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //     }
        
    //     // Verify search results or page navigation
    //     String currentUrl = getCurrentUrl();
    //     logger.info("Current URL after search: {}", currentUrl);
        
    //     Assert.assertTrue(claimPage.isSearchResultsDisplayed() || 
    //                      currentUrl.contains("search") || currentUrl.contains("result"), 
    //                      "Policy search should be executed and results displayed");
        
    //     logger.info("testClaimCreationWorkflow completed successfully");
    // }
}
