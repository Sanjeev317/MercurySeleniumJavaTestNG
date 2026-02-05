package com.enterprise.mercury.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import java.util.List;

/**
 * Page Object for Claim Creation with Claimant Details
 * Handles FNOL wizard flow: Steps 1-10 from manual test (policy search and claimant name selection)
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class ClaimCreationClaimantDetailsPage extends BasePage {
    
    // ========================================
    // Locators - Navigation
    // ========================================
    private final By claimTabExpandButton = By.xpath("//*[@id='TabBar-ClaimTab']/div[3]/div");
    private final By newClaimMenuItem = By.xpath("//*[@id='TabBar-ClaimTab-ClaimTab_FNOLWizard']/div");
    
    // ========================================
    // Locators - Policy Search Screen
    // ========================================
    private final By policyNumberInput = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-policyNumber");
    private final By lossDateInput = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-date");
    private final By lossTimeInput = By.xpath("//input[@type='text' and (@placeholder='hh:mm' or contains(@aria-label,'hh:mm'))]");
    private final By timeAmPmButton = By.xpath("//button[contains(@class,'gw-time') or contains(text(),'AA') or contains(text(),'aa')]");
    private final By searchButton = By.xpath("//div[contains(@id,'FNOLWizardFindPolicyPanelSet-Search') and contains(@class,'gw-actionable')]");
    private final By nextButton = By.xpath("//div[contains(@id,'FNOLWizard') and contains(@id,'Next') and contains(@class,'gw-action')]");
    
    // ========================================
    // Locators - Claimant Details Screen
    // ========================================
    private final By claimantNameDropdown = By.xpath("//select[contains(@name,'Name') or contains(@id,'Name')]");
    
    /**
     * Constructor
     */
    public ClaimCreationClaimantDetailsPage() {
        super();
        logger.info("ClaimCreationClaimantDetailsPage initialized");
    }
    
    // ========================================
    // Navigation Methods
    // ========================================
    
    /**
     * Click on Claim Tab expand button to reveal menu
     */
    @Step("Click Claim Tab expand button")
    public void clickClaimTabExpand() {
        logger.info("Clicking Claim Tab expand button");
        click(claimTabExpandButton);
    }
    
    /**
     * Click on New Claim menu item
     */
    @Step("Click New Claim menu item")
    public void clickNewClaimMenuItem() {
        logger.info("Clicking New Claim menu item");
        click(newClaimMenuItem);
    }
    
    // ========================================
    // Policy Search Methods
    // ========================================
    
    /**
     * Enter policy number in the search field
     * @param policyNumber Policy number to search
     */
    @Step("Enter Policy Number: {policyNumber}")
    public void enterPolicyNumber(String policyNumber) {
        logger.info("Entering Policy Number: {}", policyNumber);
        type(policyNumberInput, policyNumber);
    }
    
    /**
     * Enter loss date
     * @param lossDate Loss date in MM/DD/YYYY format
     */
    @Step("Enter Loss Date: {lossDate}")
    public void enterLossDate(String lossDate) {
        logger.info("Entering Loss Date: {}", lossDate);
        type(lossDateInput, lossDate);
    }
    
    /**
     * Enter loss time with AM/PM toggle
     * Uses JavaScript to set complete time value to avoid validation errors
     * 
     * @param time Time in HH:MM format
     * @param amPm AM or PM indicator
     */
    @Step("Enter Loss Time: {time} {amPm}")
    public void enterLossTimeWithAmPm(String time, String amPm) {
        logger.info("Entering Loss Time with AM/PM: {} {}", time, amPm);
        
        try {
            WebElement timeField = driver.findElement(lossTimeInput);
            timeField.clear();
            
            // Set complete time value with AM/PM using JavaScript
            String completeTime = time + " " + amPm;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", timeField, completeTime);
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", timeField);
            
            Thread.sleep(500);
            logger.info("Complete Loss Time entered and validated: {}", completeTime);
            
        } catch (Exception e) {
            logger.error("Failed to enter loss time with AM/PM", e);
            throw new RuntimeException("Failed to enter loss time: " + time + " " + amPm, e);
        }
    }
    
    /**
     * Click the Time of Loss AM/PM button to toggle
     */
    @Step("Click Time of Loss AM/PM button")
    public void clickTimeAmPmButton() {
        logger.info("Clicking Time of Loss AM/PM button");
        try {
            click(timeAmPmButton);
        } catch (Exception e) {
            logger.warn("AM/PM button not needed - time already set with AM/PM");
        }
    }
    
    /**
     * Click Search button to execute policy search
     */
    @Step("Click Search button")
    public void clickSearchButton() {
        logger.info("Clicking Search button");
        click(searchButton);
        
        // Wait for search results
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Click Next button to proceed to next wizard step
     */
    @Step("Click Next button")
    public void clickNextButton() {
        logger.info("Clicking Next button");
        
        // Wait for overlay to disappear and button to be enabled
        try {
            By clickOverlay = By.id("gw-click-overlay");
            List<WebElement> overlays = driver.findElements(clickOverlay);
            if (!overlays.isEmpty()) {
                logger.info("Waiting for click overlay to disappear...");
                Thread.sleep(2000);
            }
            
            // Wait for Next button to be enabled
            WebElement nextBtn = driver.findElement(nextButton);
            int attempts = 0;
            while (attempts < 10 && "true".equals(nextBtn.getAttribute("aria-disabled"))) {
                logger.info("Next button is disabled, waiting... (attempt {})", attempts + 1);
                Thread.sleep(1000);
                nextBtn = driver.findElement(nextButton);
                attempts++;
            }
            
            if ("true".equals(nextBtn.getAttribute("aria-disabled"))) {
                logger.warn("Next button is still disabled after waiting, trying to click anyway");
            }
            
        } catch (Exception e) {
            logger.warn("Error while waiting for Next button: {}", e.getMessage());
        }
        
        click(nextButton);
        
        // Wait for next page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // ========================================
    // Claimant Details Methods
    // ========================================
    
    /**
     * Select claimant name from dropdown
     * @param claimantName Name or ID of claimant (e.g., "Person:1757311")
     * @throws RuntimeException if claimant name cannot be selected
     */
    @Step("Select Claimant Name: {claimantName}")
    public void selectClaimantName(String claimantName) {
        logger.info("Selecting Claimant Name: {}", claimantName);
        
        try {
            selectByVisibleText(claimantNameDropdown, claimantName);
            logger.info("Successfully selected claimant name: {}", claimantName);
            
            // Wait for page to update after selection
            Thread.sleep(2000);
            
        } catch (Exception e) {
            logger.error("Failed to select claimant name: {}. Error: {}", claimantName, e.getMessage());
            throw new RuntimeException("Unable to select claimant name '" + claimantName + "' from dropdown. " +
                                     "Please verify the claimant exists in the dropdown options.", e);
        }
    }
    
    // ========================================
    // Composite Methods
    // ========================================
    
    /**
     * Fill complete policy search information
     * @param policyNumber Policy number
     * @param lossDate Loss date
     * @param lossTime Loss time
     * @param amPm AM or PM
     */
    @Step("Fill Policy Search Information")
    public void fillPolicySearchInfo(String policyNumber, String lossDate, String lossTime, String amPm) {
        logger.info("Filling Policy Search Information");
        enterPolicyNumber(policyNumber);
        enterLossDate(lossDate);
        enterLossTimeWithAmPm(lossTime, amPm);
    }
    
    /**
     * Complete claim creation with claimant name selection - Steps 1-10 only
     * @param policyNumber Policy number
     * @param lossDate Loss date
     * @param lossTime Loss time
     * @param amPm AM or PM
     * @param claimantName Claimant name
     */
    @Step("Complete Claim Creation with Claimant Name Selection")
    public void completeClaimCreationWithClaimantDetails(String policyNumber, String lossDate, 
                                                          String lossTime, String amPm,
                                                          String claimantName) {
        logger.info("Completing Claim Creation with Claimant Name Selection (Steps 1-10)");
        
        // Step 1-3: Navigate to New Claim
        clickClaimTabExpand();
        clickNewClaimMenuItem();
        
        // Step 4-7: Fill policy search and click AM/PM toggle
        fillPolicySearchInfo(policyNumber, lossDate, lossTime, amPm);
        
        // Step 8: Click Search
        clickSearchButton();
        
        // Step 9: Click Next
        clickNextButton();
        
        // Step 10: Select claimant name - STOP HERE
        selectClaimantName(claimantName);
    }
}
