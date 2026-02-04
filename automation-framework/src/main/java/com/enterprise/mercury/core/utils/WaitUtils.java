package com.enterprise.mercury.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

/**
 * Reusable Wait Utilities for explicit waits
 * Provides various wait strategies for different scenarios
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class WaitUtils {
    
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 20;
    private static final int DEFAULT_POLLING = 500;
    
    // Private constructor to prevent instantiation
    private WaitUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Wait for element to be visible
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @return WebElement when visible
     */
    public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be visible: {}", locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be visible with default timeout
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement when visible
     */
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return waitForElementVisible(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to be clickable
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @return WebElement when clickable
     */
    public static WebElement waitForElementClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be clickable with default timeout
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement when clickable
     */
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to be present in DOM
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @return WebElement when present
     */
    public static WebElement waitForElementPresent(WebDriver driver, By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be present: {}", locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be present with default timeout
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement when present
     */
    public static WebElement waitForElementPresent(WebDriver driver, By locator) {
        return waitForElementPresent(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for all elements to be visible
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @return List of WebElements
     */
    public static List<WebElement> waitForAllElementsVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        logger.debug("Waiting for all elements to be visible: {}", locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    /**
     * Wait for element to be invisible
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @return true when element is invisible
     */
    public static boolean waitForElementInvisible(WebDriver driver, By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be invisible: {}", locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for text to be present in element
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param text Expected text
     * @param timeoutInSeconds Wait timeout
     * @return true when text is present
     */
    public static boolean waitForTextToBePresentInElement(WebDriver driver, By locator, String text, int timeoutInSeconds) {
        logger.debug("Waiting for text '{}' to be present in element: {}", text, locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    /**
     * Wait for attribute to contain value
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param attribute Attribute name
     * @param value Expected value
     * @param timeoutInSeconds Wait timeout
     * @return true when attribute contains value
     */
    public static boolean waitForAttributeContains(WebDriver driver, By locator, String attribute, String value, int timeoutInSeconds) {
        logger.debug("Waiting for attribute '{}' to contain '{}' in element: {}", attribute, value, locator);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
    }
    
    /**
     * Wait for URL to contain text
     * 
     * @param driver WebDriver instance
     * @param urlPart URL part to check
     * @param timeoutInSeconds Wait timeout
     * @return true when URL contains text
     */
    public static boolean waitForUrlContains(WebDriver driver, String urlPart, int timeoutInSeconds) {
        logger.debug("Waiting for URL to contain: {}", urlPart);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }
    
    /**
     * Wait for title to contain text
     * 
     * @param driver WebDriver instance
     * @param title Title text to check
     * @param timeoutInSeconds Wait timeout
     * @return true when title contains text
     */
    public static boolean waitForTitleContains(WebDriver driver, String title, int timeoutInSeconds) {
        logger.debug("Waiting for title to contain: {}", title);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.titleContains(title));
    }
    
    /**
     * Fluent wait for element with custom polling
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Wait timeout
     * @param pollingInMillis Polling interval
     * @return WebElement when found
     */
    public static WebElement fluentWaitForElement(WebDriver driver, By locator, int timeoutInSeconds, int pollingInMillis) {
        logger.debug("Fluent wait for element: {}", locator);
        
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class);
        
        return wait.until(driver1 -> driver1.findElement(locator));
    }
    
    /**
     * Fluent wait with default polling
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement when found
     */
    public static WebElement fluentWaitForElement(WebDriver driver, By locator) {
        return fluentWaitForElement(driver, locator, DEFAULT_TIMEOUT, DEFAULT_POLLING);
    }
    
    /**
     * Wait for custom condition
     * 
     * @param driver WebDriver instance
     * @param condition Custom expected condition
     * @param timeoutInSeconds Wait timeout
     * @param <T> Return type
     * @return Result of condition
     */
    public static <T> T waitForCondition(WebDriver driver, Function<WebDriver, T> condition, int timeoutInSeconds) {
        logger.debug("Waiting for custom condition");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(condition);
    }
    
    /**
     * Simple thread sleep (use sparingly)
     * 
     * @param milliseconds Sleep duration
     */
    public static void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted during sleep", e);
        }
    }
}
