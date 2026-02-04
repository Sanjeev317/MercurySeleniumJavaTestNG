package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.config.ConfigManager;
import com.enterprise.mercury.core.driver.DriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Helper Test to Identify Page Elements
 * This test helps you find the correct locators by printing page information
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("Helper Utilities")
@Feature("Element Identification")
public class ElementIdentifierTest extends BaseTest {
    
    /**
     * Test to identify elements on the page
     * This will print the page source and common elements to help you find correct locators
     */
    @Test(priority = 1, description = "Identify elements on ClaimCenter home page")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Element Identification")
    @Description("Helper test to identify elements on the page for updating locators")
    public void identifyPageElements() {
        logger.info("Starting element identification");
        
        // Wait for page to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        logger.info("Current Page URL: {}", driver.getCurrentUrl());
        logger.info("Current Page Title: {}", driver.getTitle());
        
        // Print all clickable elements (links and buttons)
        logger.info("\n========== CLICKABLE ELEMENTS ==========");
        printElements(By.tagName("a"), "Links");
        printElements(By.tagName("button"), "Buttons");
        printElements(By.xpath("//input[@type='button' or @type='submit']"), "Input Buttons");
        
        // Print all input fields
        logger.info("\n========== INPUT FIELDS ==========");
        printElements(By.tagName("input"), "Input Fields");
        
        // Print all select dropdowns
        logger.info("\n========== DROPDOWNS ==========");
        printElements(By.tagName("select"), "Select Dropdowns");
        
        // Print elements containing "claim" text
        logger.info("\n========== ELEMENTS CONTAINING 'CLAIM' ==========");
        printElements(By.xpath("//*[contains(translate(text(), 'CLAIM', 'claim'), 'claim')]"), "Claim Elements");
        
        // Print elements with "menu" or "tab" in class
        logger.info("\n========== NAVIGATION ELEMENTS ==========");
        printElements(By.xpath("//*[contains(@class, 'menu') or contains(@class, 'tab') or contains(@class, 'nav')]"), "Navigation");
        
        logger.info("\n========== PAGE SOURCE (First 1000 chars) ==========");
        String pageSource = driver.getPageSource();
        if (pageSource.length() > 1000) {
            logger.info(pageSource.substring(0, 1000) + "...");
        } else {
            logger.info(pageSource);
        }
    }
    
    /**
     * Helper method to print element information
     */
    private void printElements(By locator, String elementType) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            logger.info("\n--- {} (Found: {}) ---", elementType, elements.size());
            
            int count = 0;
            for (WebElement element : elements) {
                if (count >= 10) {
                    logger.info("... and {} more", elements.size() - count);
                    break;
                }
                
                try {
                    String id = element.getAttribute("id");
                    String name = element.getAttribute("name");
                    String className = element.getAttribute("class");
                    String text = element.getText();
                    String type = element.getAttribute("type");
                    String value = element.getAttribute("value");
                    
                    StringBuilder info = new StringBuilder();
                    if (id != null && !id.isEmpty()) info.append("id='").append(id).append("' ");
                    if (name != null && !name.isEmpty()) info.append("name='").append(name).append("' ");
                    if (type != null && !type.isEmpty()) info.append("type='").append(type).append("' ");
                    if (className != null && !className.isEmpty()) info.append("class='").append(className).append("' ");
                    if (text != null && !text.isEmpty() && text.length() < 50) info.append("text='").append(text.trim()).append("' ");
                    if (value != null && !value.isEmpty() && value.length() < 50) info.append("value='").append(value).append("' ");
                    
                    if (info.length() > 0) {
                        logger.info("  [{}] {}", count + 1, info.toString());
                    }
                    
                    count++;
                } catch (Exception e) {
                    // Skip elements that throw exceptions
                }
            }
        } catch (Exception e) {
            logger.error("Error finding {}: {}", elementType, e.getMessage());
        }
    }
}
