package com.enterprise.mercury.api.tests;

import com.enterprise.mercury.api.clients.ApiClient;
import com.enterprise.mercury.core.listeners.TestListener;
import com.enterprise.mercury.core.utils.DataReader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * API Test Suite for Login Endpoints
 * Tests API authentication and login functionality
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("API Testing")
@Feature("Authentication API")
@Listeners(TestListener.class)
public class LoginApiTest {
    
    private static final Logger logger = LogManager.getLogger(LoginApiTest.class);
    private ApiClient apiClient;
    
    /**
     * Setup before class
     */
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        logger.info("=================================================");
        logger.info("Setting up API test environment");
        logger.info("=================================================");
        
        apiClient = new ApiClient();
        
        logger.info("API client initialized");
    }
    
    /**
     * Test successful login via API with valid credentials
     */
    @Test(priority = 1, description = "Verify successful login via API with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("API Authentication")
    @Description("Test to verify API returns 200 status and auth token for valid credentials")
    public void testApiLoginWithValidCredentials() {
        logger.info("Starting testApiLoginWithValidCredentials");
        
        // Read test data from JSON
        String requestBody = DataReader.readJsonAsString("api/loginPayload.json");
        logger.info("Request payload loaded from JSON");
        
        // Perform POST request to login endpoint
        Response response = apiClient.post("/api/v1/auth/login", requestBody);
        
        // Verify status code
        int statusCode = response.getStatusCode();
        logger.info("Response status code: {}", statusCode);
        Assert.assertEquals(statusCode, 200, "Status code should be 200 for successful login");
        
        // Verify response contains token
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Response should contain authentication token");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        logger.info("Authentication token received");
        
        // Verify response contains user data
        String username = response.jsonPath().getString("user.username");
        Assert.assertNotNull(username, "Response should contain username");
        logger.info("Username in response: {}", username);
        
        // Attach response to Allure report
        Allure.addAttachment("Response Body", "application/json", response.asString());
        
        logger.info("testApiLoginWithValidCredentials completed successfully");
    }
    
    /**
     * Test login API with invalid credentials
     */
    @Test(priority = 2, description = "Verify login API fails with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("API Authentication")
    @Description("Test to verify API returns 401 status for invalid credentials")
    public void testApiLoginWithInvalidCredentials() {
        logger.info("Starting testApiLoginWithInvalidCredentials");
        
        // Create invalid credentials payload
        String invalidPayload = """
                {
                    "username": "invaliduser",
                    "password": "wrongpassword"
                }
                """;
        
        // Perform POST request to login endpoint
        Response response = apiClient.post("/api/v1/auth/login", invalidPayload);
        
        // Verify status code is 401 Unauthorized
        int statusCode = response.getStatusCode();
        logger.info("Response status code: {}", statusCode);
        Assert.assertTrue(statusCode == 401 || statusCode == 403, 
                         "Status code should be 401 or 403 for invalid credentials");
        
        // Verify error message in response
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Response should contain error message");
        logger.info("Error message: {}", errorMessage);
        
        // Attach response to Allure report
        Allure.addAttachment("Error Response", "application/json", response.asString());
        
        logger.info("testApiLoginWithInvalidCredentials completed successfully");
    }
    
    /**
     * Test login API with missing required fields
     */
    @Test(priority = 3, description = "Verify login API validation with missing fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("API Authentication")
    @Description("Test to verify API returns 400 status for missing required fields")
    public void testApiLoginWithMissingFields() {
        logger.info("Starting testApiLoginWithMissingFields");
        
        // Create payload with missing password
        String incompletePayload = """
                {
                    "username": "testuser"
                }
                """;
        
        // Perform POST request to login endpoint
        Response response = apiClient.post("/api/v1/auth/login", incompletePayload);
        
        // Verify status code is 400 Bad Request
        int statusCode = response.getStatusCode();
        logger.info("Response status code: {}", statusCode);
        Assert.assertEquals(statusCode, 400, "Status code should be 400 for missing required fields");
        
        // Verify validation error message
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Response should contain validation error");
        logger.info("Validation error: {}", errorMessage);
        
        // Attach response to Allure report
        Allure.addAttachment("Validation Error", "application/json", response.asString());
        
        logger.info("testApiLoginWithMissingFields completed successfully");
    }
    
    /**
     * Test login API response time
     */
    @Test(priority = 4, description = "Verify login API response time is acceptable")
    @Severity(SeverityLevel.MINOR)
    @Story("API Performance")
    @Description("Test to verify API responds within acceptable time limit (under 3 seconds)")
    public void testApiLoginResponseTime() {
        logger.info("Starting testApiLoginResponseTime");
        
        // Read test data from JSON
        String requestBody = DataReader.readJsonAsString("api/loginPayload.json");
        
        // Perform POST request to login endpoint
        Response response = apiClient.post("/api/v1/auth/login", requestBody);
        
        // Get response time
        long responseTime = response.getTime();
        logger.info("API response time: {} ms", responseTime);
        
        // Verify response time is under 3 seconds (3000 ms)
        Assert.assertTrue(responseTime < 3000, 
                         "API response time should be under 3000ms, but was: " + responseTime + "ms");
        
        // Add response time to Allure report
        Allure.addAttachment("Response Time", String.valueOf(responseTime) + " ms");
        
        logger.info("testApiLoginResponseTime completed successfully");
    }
    
    /**
     * Test login API response schema validation
     */
    @Test(priority = 5, description = "Verify login API response schema")
    @Severity(SeverityLevel.NORMAL)
    @Story("API Authentication")
    @Description("Test to verify API response contains all expected fields")
    public void testApiLoginResponseSchema() {
        logger.info("Starting testApiLoginResponseSchema");
        
        // Read test data from JSON
        String requestBody = DataReader.readJsonAsString("api/loginPayload.json");
        
        // Perform POST request to login endpoint
        Response response = apiClient.post("/api/v1/auth/login", requestBody);
        
        // Verify status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        // Verify response schema
        Assert.assertNotNull(response.jsonPath().getString("token"), "Response should contain 'token'");
        Assert.assertNotNull(response.jsonPath().getString("user"), "Response should contain 'user' object");
        Assert.assertNotNull(response.jsonPath().getString("user.username"), "User object should contain 'username'");
        Assert.assertNotNull(response.jsonPath().getString("user.email"), "User object should contain 'email'");
        
        logger.info("Response schema validation passed");
        
        // Attach response to Allure report
        Allure.addAttachment("Response Schema", "application/json", response.prettyPrint());
        
        logger.info("testApiLoginResponseSchema completed successfully");
    }
}
