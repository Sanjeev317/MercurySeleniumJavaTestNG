package com.enterprise.mercury.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Claim Creation Page Object
 * Contains all locators and methods for Claim Creation and Policy Search interactions
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class ClaimCreationPage extends BasePage {
    
    // Locators - Based on Guidewire ClaimCenter UAT2 environment
    // Guidewire uses specific naming conventions: ComponentName-SubComponent-FieldName
    
    // Navigation Locators - Tab and Menu
    private final By claimTabExpandButton = By.xpath("//*[@id=\"TabBar-ClaimTab\"]/div[3]/div");
    private final By newClaimMenuItem = By.xpath("//*[@id=\"TabBar-ClaimTab-ClaimTab_FNOLWizard\"]/div");
    
    // Dropdown Locators - Guidewire pattern: ComponentName-FieldName
    // Using direct select element within the div structure
    private final By policyTypeDropdown = By.xpath("//*[@id='FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-ClaimLossType']//select");
    private final By typeDropdown = By.xpath("//*[@id='FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-Type']//select");
    
    // Input Field Locators - Guidewire pattern: using name attribute
    private final By policyNumberField = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-policyNumber");
    private final By firstNameField = By.xpath("//input[contains(@name,'FirstName') or contains(@name,'first')]");
    private final By lastNameField = By.xpath("//input[contains(@name,'LastName') or contains(@name,'last')]");
    private final By dateField = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-date");
    private final By timeField = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-LossTime_time");
    private final By timeField_AM_PM = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-LossTime_ampm-button");
    
    // Button Locators - Guidewire buttons use div elements
    private final By searchButton = By.xpath("//div[contains(@id,'Search') and contains(@class,'gw-action')]");
    private final By nextButton = By.xpath("//div[contains(@id,'Next') and contains(@class,'gw-action')]");
    private final By finishButton = By.xpath("//div[contains(@id,'Finish') and contains(@class,'gw-action')]");
    
    // Result/Error Locators
    private final By searchResults = By.xpath("//div[contains(@class,'gw-ListView') or contains(@id,'SearchResults')]");
    private final By errorMessage = By.xpath("//div[contains(@class,'gw-error') or contains(@class,'gw-warning')]");
    private final By newClaimPage = By.xpath("//div[contains(@class,'FNOLWizard') or contains(text(),'First Notice of Loss')]");
    
    /**
     * Constructor
     */
    public ClaimCreationPage() {
        super();
        logger.info("ClaimCreationPage initialized");
    }
    
    /**
     * Click on Claim Tab expand button
     */
    @Step("Click on Claim Tab expand button")
    public void clickClaimTabExpand() {
        logger.info("Clicking Claim Tab expand button");
        click(claimTabExpandButton);
    }
    
    /**
     * Click on New Claim menu item
     */
    @Step("Click on New Claim menu item")
    public void clickNewClaimMenuItem() {
        logger.info("Clicking New Claim menu item");
        click(newClaimMenuItem);
    }
    
    /**
     * Select Policy Type from dropdown
     * 
     * @param policyType Policy type to select
     */
    @Step("Select Policy Type: {policyType}")
    public void selectPolicyType(String policyType) {
        logger.info("Selecting Policy Type: {}", policyType);
        selectByVisibleText(policyTypeDropdown, policyType);
    }
    
    /**
     * Select Type from dropdown
     * 
     * @param type Type to select
     */
    @Step("Select Type: {type}")
    public void selectType(String type) {
        logger.info("Selecting Type: {}", type);
        selectByVisibleText(typeDropdown, type);
    }
    
    /**
     * Enter policy number
     * 
     * @param policyNumber Policy number to enter
     */
    @Step("Enter Policy Number: {policyNumber}")
    public void enterPolicyNumber(String policyNumber) {
        logger.info("Entering Policy Number: {}", policyNumber);
        click(policyNumberField);
        type(policyNumberField, policyNumber);
    }
    
    /**
     * Enter first name
     * 
     * @param firstName First name to enter
     */
    @Step("Enter First Name: {firstName}")
    public void enterFirstName(String firstName) {
        logger.info("Entering First Name: {}", firstName);
        click(firstNameField);
        type(firstNameField, firstName);
    }
    
    /**
     * Enter last name
     * 
     * @param lastName Last name to enter
     */
    @Step("Enter Last Name: {lastName}")
    public void enterLastName(String lastName) {
        logger.info("Entering Last Name: {}", lastName);
        click(lastNameField);
        type(lastNameField, lastName);
    }
    
    /**
     * Enter date
     * 
     * @param date Date to enter
     */
    @Step("Enter Date: {date}")
    public void enterDate(String date) {
        logger.info("Entering Date: {}", date);
        click(dateField);
        type(dateField, date);
    }
    
    /**
     * Enter time
     * 
     * @param time Time to enter
     */
    @Step("Enter Time: {time}")
    public void enterTime(String time) {
        logger.info("Entering Time: {}", time);
        click(timeField);
        type(timeField, time);
    }
    

    @Step("Enter Time of Loss AM/PM")
    public void enterTimeOfLossAMPM() {
        logger.info("Clicking Time of Loss AM/PM button");
        click(timeField_AM_PM);
    }   
    /**
     * Click Search button
     */
    @Step("Click Search button")
    public void clickSearchButton() {
        logger.info("Clicking Search button");
        click(searchButton);
    }
    
    /**
     * Composite method to fill policy information
     * 
     * @param policyType Policy type
     * @param type Type
     * @param policyNumber Policy number
     */
    @Step("Fill Policy Information")
    public void fillPolicyInformation(String policyType, String type, String policyNumber) {
        logger.info("Filling Policy Information");
        selectPolicyType(policyType);
        selectType(type);
        enterPolicyNumber(policyNumber);
    }
    
    /**
     * Composite method to fill personal information
     * 
     * @param firstName First name
     * @param lastName Last name
     * @param date Date
     * @param time Time
     */
    @Step("Fill Personal Information")
    public void fillPersonalInformation(String firstName, String lastName, String date, String time) {
        logger.info("Filling Personal Information");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterDate(date);
        enterTime(time);
    }

    public void timeOfLoss() {
        logger.info("Clicking Time of Loss AM/PM button");
        click(timeField_AM_PM);
    }
    
    /**
     * Complete policy search flow
     * 
     * @param policyType Policy type
     * @param type Type
     * @param policyNumber Policy number
     * @param firstName First name
     * @param lastName Last name
     * @param date Date
     * @param time Time
     */
    @Step("Complete Policy Search Flow")
    public void completePolicySearch(String policyType, String type, String policyNumber, 
                                      String firstName, String lastName, String date, String time) {
        logger.info("Completing Policy Search Flow");
        fillPolicyInformation(policyType, type, policyNumber);
        fillPersonalInformation(firstName, lastName, date, time);
        clickSearchButton();
    }
    
    /**
     * Verify New Claim page is displayed
     * 
     * @return true if New Claim page is displayed
     */
    public boolean isNewClaimPageDisplayed() {
        logger.info("Checking if New Claim page is displayed");
        return isDisplayed(newClaimPage);
    }
    
    /**
     * Verify search results are displayed
     * 
     * @return true if search results are displayed
     */
    public boolean isSearchResultsDisplayed() {
        logger.info("Checking if search results are displayed");
        return isDisplayed(searchResults);
    }
    
    /**
     * Get search results text
     * 
     * @return Search results text
     */
    public String getSearchResultsText() {
        logger.info("Getting search results text");
        return getText(searchResults);
    }
    
    /**
     * Get error message
     * 
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(errorMessage);
    }
    
    /**
     * Check if error message is displayed
     * 
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isDisplayed(errorMessage);
    }
}
