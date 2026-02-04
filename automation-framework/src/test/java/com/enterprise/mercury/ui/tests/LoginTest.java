package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.utils.DataReader;
import com.enterprise.mercury.ui.pages.LoginPage;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login Test Suite
 * Tests for Login functionality using JSON test data
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("User Management")
@Feature("Login")
public class LoginTest extends BaseTest {
    
    /**
     * Test successful login with valid credentials from JSON
     */
    @Test(priority = 1, description = "Verify successful login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("User Authentication")
    @Description("Test to verify user can login successfully with valid username and password from JSON test data")
    public void testSuccessfulLogin() {
        logger.info("Starting testSuccessfulLogin");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("loginData.json");
        String username = testData.get("validUser").get("username").asText();
        String password = testData.get("validUser").get("password").asText();
        String expectedTitle = testData.get("validUser").get("expectedPageTitle").asText();
        
        logger.info("Test data loaded - Username: {}", username);
        
        // Create page object
        LoginPage loginPage = new LoginPage();
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        
        // Perform login
        loginPage.login(username, password);
        
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify successful login
        String currentUrl = getCurrentUrl();
        logger.info("Current URL after login: {}", currentUrl);
        
        Assert.assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"), 
                         "Should navigate to dashboard/home page after successful login");
        
        logger.info("testSuccessfulLogin completed successfully");
    }
    
    /**
     * Test login with invalid credentials
     */
    @Test(priority = 2, description = "Verify login fails with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Authentication")
    @Description("Test to verify appropriate error message is displayed for invalid credentials")
    public void testLoginWithInvalidCredentials() {
        logger.info("Starting testLoginWithInvalidCredentials");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("loginData.json");
        String username = testData.get("invalidUser").get("username").asText();
        String password = testData.get("invalidUser").get("password").asText();
        String expectedError = testData.get("invalidUser").get("expectedError").asText();
        
        logger.info("Test data loaded - Username: {}", username);
        
        // Create page object
        LoginPage loginPage = new LoginPage();
        
        // Perform login with invalid credentials
        loginPage.login(username, password);
        
        // Wait for error message
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        
        String actualError = loginPage.getErrorMessage();
        logger.info("Error message displayed: {}", actualError);
        
        Assert.assertTrue(actualError.contains(expectedError) || actualError.toLowerCase().contains("invalid"), 
                         "Error message should indicate invalid credentials");
        
        logger.info("testLoginWithInvalidCredentials completed successfully");
    }
    
    /**
     * Test login with empty credentials
     */
    @Test(priority = 3, description = "Verify login validation with empty fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Authentication")
    @Description("Test to verify validation when username and password fields are empty")
    public void testLoginWithEmptyCredentials() {
        logger.info("Starting testLoginWithEmptyCredentials");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("loginData.json");
        String username = testData.get("emptyUser").get("username").asText();
        String password = testData.get("emptyUser").get("password").asText();
        
        // Create page object
        LoginPage loginPage = new LoginPage();
        
        // Attempt login with empty credentials
        loginPage.login(username, password);
        
        // Wait for validation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify user remains on login page or error is shown
        Assert.assertTrue(loginPage.isLoginPageDisplayed() || loginPage.isErrorMessageDisplayed(), 
                         "Should remain on login page or show error for empty credentials");
        
        logger.info("testLoginWithEmptyCredentials completed successfully");
    }
    
    /**
     * Test login with Remember Me option
     */
    @Test(priority = 4, description = "Verify Remember Me functionality")
    @Severity(SeverityLevel.MINOR)
    @Story("User Authentication")
    @Description("Test to verify Remember Me checkbox functionality during login")
    public void testLoginWithRememberMe() {
        logger.info("Starting testLoginWithRememberMe");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("loginData.json");
        String username = testData.get("validUser").get("username").asText();
        String password = testData.get("validUser").get("password").asText();
        
        // Create page object
        LoginPage loginPage = new LoginPage();
        
        // Perform login with Remember Me
        loginPage.loginWithRememberMe(username, password);
        
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify successful login
        String currentUrl = getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"), 
                         "Should navigate to dashboard/home page after login with Remember Me");
        
        logger.info("testLoginWithRememberMe completed successfully");
    }
}
