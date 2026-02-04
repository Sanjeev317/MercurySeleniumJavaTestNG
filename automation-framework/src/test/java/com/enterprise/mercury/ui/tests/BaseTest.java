package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.config.ConfigManager;
import com.enterprise.mercury.core.driver.DriverFactory;
import com.enterprise.mercury.core.listeners.TestListener;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Base Test class for all UI tests
 * Handles WebDriver initialization and cleanup
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
    
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected ConfigManager config;
    
    /**
     * Setup method - runs before each test
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("=================================================");
        logger.info("Setting up test environment");
        logger.info("=================================================");
        
        // Load configuration
        config = ConfigManager.getInstance();
        
        // Get browser and headless settings from config
        String browser = config.getBrowser();
        boolean headless = config.isHeadless();
        
        logger.info("Browser: {}", browser);
        logger.info("Headless: {}", headless);
        logger.info("Environment: {}", config.getEnvironment());
        
        // Initialize WebDriver
        driver = DriverFactory.initializeDriver(browser, headless);
        
        // Add environment info to Allure
        Allure.parameter("Browser", browser);
        Allure.parameter("Environment", config.getEnvironment());
        Allure.parameter("Base URL", config.getBaseUrl());
        
        // Navigate to base URL
        String baseUrl = config.getBaseUrl();
        if (baseUrl != null && !baseUrl.isEmpty()) {
            logger.info("Navigating to base URL: {}", baseUrl);
            driver.get(baseUrl);
        }
        
        logger.info("Test setup completed successfully");
    }
    
    /**
     * Teardown method - runs after each test
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("=================================================");
        logger.info("Tearing down test environment");
        logger.info("=================================================");
        
        // Quit WebDriver
        if (DriverFactory.isDriverInitialized()) {
            DriverFactory.quitDriver();
            logger.info("WebDriver quit successfully");
        }
        
        logger.info("Test teardown completed");
    }
    
    /**
     * Navigate to specific URL
     * 
     * @param url URL to navigate
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }
    
    /**
     * Get current page title
     * 
     * @return Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     * 
     * @return Current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
