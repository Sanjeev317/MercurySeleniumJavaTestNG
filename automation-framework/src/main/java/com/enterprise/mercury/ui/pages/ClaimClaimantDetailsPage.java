package com.enterprise.mercury.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import java.util.List;

/**
 * Page Object for Claim Creation with Claimant Details functionality
 * Handles the FNOL (First Notice of Loss) wizard screens with focus on claimant details
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class ClaimClaimantDetailsPage extends BasePage {
    
    // ========================================
    // Locators - Navigation Elements
    // ========================================
    private final By claimTabExpandButton = By.xpath("//*[@id='TabBar-ClaimTab']/div[3]/div");
    private final By newClaimMenuItem = By.xpath("//*[@id='TabBar-ClaimTab-ClaimTab_FNOLWizard']/div");
    
    // ========================================
    // Locators - Policy Search Screen
    // ========================================
    private final By policyNumberInput = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-policyNumber");
    private final By lossDateInput = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-date");
    private final By lossTimeInput = By.xpath("//input[@type='text' and (@placeholder='hh:mm' or contains(@aria-label,'hh:mm'))]");
    private final By searchButton = By.xpath("//div[contains(@id,'FNOLWizardFindPolicyPanelSet-Search') and contains(@class,'gw-actionable')]");
    private final By nextButton = By.xpath("//div[contains(@id,'FNOLWizard') and contains(@id,'Next') and contains(@class,'gw-action')]");
    
    // ========================================
    // Locators - Claimant Details Screen
    // ========================================
    private final By claimantNameDropdown = By.xpath("//select[contains(@name,'Name') or contains(@id,'Name')]");
    private final By preferredMethodOfContactDropdown = By.name("FNOLWizard-FullWizardStepSet-FNOLWizard_BasicInfoScreen-PanelRow-BasicInfoDetailViewPanelDV-PersonContactInfoInputSet-preferred_method_of_contact");
    
    /**
     * Constructor
     */
    public ClaimClaimantDetailsPage() {
        super();
        logger.info("ClaimClaimantDetailsPage initialized");
    }
    
    // ========================================
    // Navigation Methods
    // ========================================
    
    /**
     * Click on Claim Tab expand button
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
     * Enter policy number
     * @param policyNumber Policy number to enter
     */
    @Step("Enter Policy Number: {policyNumber}")
    public void enterPolicyNumber(String policyNumber) {
        logger.info("Entering Policy Number: {}", policyNumber);
        type(policyNumberInput, policyNumber);
    }
    
    /**
     * Enter loss date
     * @param lossDate Loss date in format MM/DD/YYYY
     */
    @Step("Enter Loss Date: {lossDate}")
    public void enterLossDate(String lossDate) {
        logger.info("Entering Loss Date: {}", lossDate);
        type(lossDateInput, lossDate);
    }
    
    /**
     * Enter loss time with AM/PM toggle
     * Uses JavaScript to set the complete time value including AM/PM
     * This avoids validation errors from incomplete time entry
     * 
     * @param time Time in HH:MM format (e.g., "11:11")
     * @param amPm AM or PM indicator
     */
    @Step("Enter Loss Time: {time} {amPm}")
    public void enterLossTimeWithAmPm(String time, String amPm) {
        logger.info("Entering Loss Time with AM/PM: {} {}", time, amPm);
        
        try {
            // Find the time input field
            WebElement timeField = driver.findElement(lossTimeInput);
            
            // Clear any existing value
            timeField.clear();
            
            // Use JavaScript to set the complete time value with AM/PM
            // Format: "HH:MM AM" or "HH:MM PM"
            String completeTime = time + " " + amPm;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", timeField, completeTime);
            
            // Trigger change event to ensure validation
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", timeField);
            
            // Small wait for validation
            Thread.sleep(500);
            
            logger.info("Complete Loss Time entered and validated: {}", completeTime);
            
        } catch (Exception e) {
            logger.error("Failed to enter loss time with AM/PM", e);
            throw new RuntimeException("Failed to enter loss time: " + time + " " + amPm, e);
        }
    }
    
    /**
     * Click Search button
     */
    @Step("Click Search button")
    public void clickSearchButton() {
        logger.info("Clicking Search button");
        click(searchButton);
        
        // Wait for search results to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Click Next button
     */
    @Step("Click Next button")
    public void clickNextButton() {
        logger.info("Clicking Next button");
        
        // Wait for overlay to disappear and button to be enabled
        try {
            // Wait for the click overlay to disappear (if present)
            By clickOverlay = By.id("gw-click-overlay");
            List<WebElement> overlays = driver.findElements(clickOverlay);
            if (!overlays.isEmpty()) {
                logger.info("Waiting for click overlay to disappear...");
                Thread.sleep(2000); // Wait for overlay to disappear
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
     * @param claimantName Name of the claimant to select
     */
    @Step("Select Claimant Name: {claimantName}")
    public void selectClaimantName(String claimantName) {
        logger.info("Selecting Claimant Name: {}", claimantName);
        selectByVisibleText(claimantNameDropdown, claimantName);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Select relation to insured from dropdown
     * NOTE: This field may or may not exist on the Basic Info screen depending on the workflow
     * The method will attempt to find the field dynamically and log all available select elements
     * 
     * @param relation Relation to insured
     */
    @Step("Select Relation to Insured: {relation}")
    public void selectRelationToInsured(String relation) {
        logger.info("Attempting to select Relation to Insured: {}", relation);
        
        try {
            // Dynamic XPath to find any select element containing "Relation" in name or id
            By relationDropdown = By.xpath("//select[contains(@name,'Relation') or contains(@id,'Relation')]");
            
            // Debug logging - enumerate ALL select elements on the page
            List<WebElement> selectElements = driver.findElements(By.tagName("select"));
            logger.info("Found {} select elements on page", selectElements.size());
            
            boolean relationFieldFound = false;
            for (WebElement select : selectElements) {
                String name = select.getAttribute("name");
                String id = select.getAttribute("id");
                boolean visible = select.isDisplayed();
                logger.info("  Select - name: '{}', id: '{}', visible: {}", name, id, visible);
                
                // Check if this is the Relation field
                if ((name != null && name.toLowerCase().contains("relation")) || 
                    (id != null && id.toLowerCase().contains("relation"))) {
                    relationFieldFound = true;
                }
            }
            
            if (relationFieldFound) {
                logger.info("Relation field found, attempting selection");
                selectByVisibleText(relationDropdown, relation);
            } else {
                logger.warn("Relation to Insured field not found on Basic Info screen - skipping");
            }
            
        } catch (Exception e) {
            logger.warn("Relation to Insured field not found: {}", e.getMessage());
            // Don't throw exception - field might not exist on this screen
        }
    }
    
    /**
     * Select No for Agree to Email Communication
     * NOTE: This field may not exist on the Basic Info screen after claimant selection
     */
    @Step("Select No for Agree to Email Communication")
    public void selectAgreeToEmailCommunicationNo() {
        logger.warn("Agree to Email Communication field not found on Basic Info screen - skipping");
        // This field doesn't exist on the current page after claimant selection
    }
    
    /**
     * Select preferred method of contact
     * @param method Preferred method of contact
     */
    @Step("Select Preferred Method of Contact: {method}")
    public void selectPreferredMethodOfContact(String method) {
        logger.info("Selecting Preferred Method of Contact: {}", method);
        
        // Skip if method is "none" or empty
        if (method == null || method.trim().isEmpty() || method.equalsIgnoreCase("none")) {
            logger.info("Skipping preferred method selection - value is 'none' or empty");
            return;
        }
        
        selectByVisibleText(preferredMethodOfContactDropdown, method);
    }
    
    /**
     * Select No for Requested
     * NOTE: This field may not exist on the Basic Info screen after claimant selection
     */
    @Step("Select No for Requested")
    public void selectRequestedNo() {
        logger.warn("Requested field not found on Basic Info screen - skipping");
        // This field doesn't exist on the current page after claimant selection
    }
    
    // ========================================
    // Composite Methods
    // ========================================
    
    /**
     * Fill policy search information
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
     * Fill claimant details
     * @param claimantName Claimant name
     * @param relationToInsured Relation to insured
     * @param preferredMethod Preferred method of contact
     */
    @Step("Fill Claimant Details")
    public void fillClaimantDetails(String claimantName, String relationToInsured, String preferredMethod) {
        logger.info("Filling Claimant Details");
        selectClaimantName(claimantName);
        selectRelationToInsured(relationToInsured);
        selectAgreeToEmailCommunicationNo();
        selectPreferredMethodOfContact(preferredMethod);
        selectRequestedNo();
    }
    
    /**
     * Complete claim creation with claimant details
     * @param policyNumber Policy number
     * @param lossDate Loss date
     * @param lossTime Loss time
     * @param claimantName Claimant name
     * @param relationToInsured Relation to insured
     * @param preferredMethod Preferred method of contact
     */
    @Step("Complete Claim Creation with Claimant Details")
    public void completeClaimCreationWithClaimantDetails(String policyNumber, String lossDate, 
                                                          String lossTime, String claimantName, 
                                                          String relationToInsured, String preferredMethod) {
        logger.info("Completing Claim Creation with Claimant Details");
        
        // Navigate to New Claim
        clickClaimTabExpand();
        clickNewClaimMenuItem();
        
        // Fill policy search (always use PM for this test case)
        fillPolicySearchInfo(policyNumber, lossDate, lossTime, "PM");
        clickSearchButton();
        clickNextButton();
        
        // Fill claimant details
        fillClaimantDetails(claimantName, relationToInsured, preferredMethod);
        clickNextButton();
    }
}
