package com.enterprise.mercury.ui.pages;

import com.enterprise.mercury.core.driver.DriverFactory;
import com.enterprise.mercury.core.utils.WaitUtils;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Base Page Object class containing common methods for all pages
 * All page objects must extend this class
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public abstract class BasePage {
    
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected Actions actions;
    
    /**
     * Constructor to initialize driver
     */
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.actions = new Actions(driver);
    }
    
    /**
     * Click on element with wait
     * 
     * @param locator Element locator
     */
    @Step("Click on element: {locator}")
    protected void click(By locator) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try {
                WebElement element = WaitUtils.waitForElementClickable(driver, locator);
                element.click();
                logger.info("Clicked on element: {}", locator);
                return; // Success, exit method
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                logger.warn("Stale element on attempt {}, retrying...", i + 1);
                if (i == maxRetries - 1) {
                    logger.error("Failed to click on element after {} attempts: {}", maxRetries, locator, e);
                    throw new RuntimeException("Click failed: " + locator, e);
                }
                // Wait briefly before retry
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                logger.error("Failed to click on element: {}", locator, e);
                throw new RuntimeException("Click failed: " + locator, e);
            }
        }
    }
    
    /**
     * Type text into element
     * 
     * @param locator Element locator
     * @param text Text to type
     */
    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed '{}' into element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to type into element: {}", locator, e);
            throw new RuntimeException("Type failed: " + locator, e);
        }
    }
    
    /**
     * Type text without clearing first
     * 
     * @param locator Element locator
     * @param text Text to type
     */
    @Step("Append '{text}' to element: {locator}")
    protected void typeWithoutClear(By locator, String text) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            element.sendKeys(text);
            logger.info("Appended '{}' to element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to append text to element: {}", locator, e);
            throw new RuntimeException("Append failed: " + locator, e);
        }
    }
    
    /**
     * Get text from element
     * 
     * @param locator Element locator
     * @return Element text
     */
    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            String text = element.getText();
            logger.info("Retrieved text '{}' from element: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw new RuntimeException("Get text failed: " + locator, e);
        }
    }
    
    /**
     * Get attribute value from element
     * 
     * @param locator Element locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(By locator, String attribute) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            String value = element.getAttribute(attribute);
            logger.info("Retrieved attribute '{}' = '{}' from element: {}", attribute, value, locator);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get attribute from element: {}", locator, e);
            throw new RuntimeException("Get attribute failed: " + locator, e);
        }
    }
    
    /**
     * Check if element is displayed
     * 
     * @param locator Element locator
     * @return true if displayed, false otherwise
     */
    protected boolean isDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean displayed = element.isDisplayed();
            logger.info("Element displayed status: {} for {}", displayed, locator);
            return displayed;
        } catch (NoSuchElementException e) {
            logger.info("Element not found: {}", locator);
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     * 
     * @param locator Element locator
     * @return true if enabled, false otherwise
     */
    protected boolean isEnabled(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean enabled = element.isEnabled();
            logger.info("Element enabled status: {} for {}", enabled, locator);
            return enabled;
        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}", locator);
            return false;
        }
    }
    
    /**
     * Select dropdown option by visible text
     * 
     * @param locator Dropdown locator
     * @param visibleText Visible text to select
     */
    @Step("Select '{visibleText}' from dropdown: {locator}")
    protected void selectByVisibleText(By locator, String visibleText) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            Select select = new Select(element);
            
            // Log all available options for debugging
            logger.info("Available options in dropdown:");
            for (WebElement option : select.getOptions()) {
                logger.info("  - '{}' (value='{}')", option.getText(), option.getAttribute("value"));
            }
            
            // Try exact match first
            try {
                select.selectByVisibleText(visibleText);
                logger.info("Selected '{}' from dropdown: {}", visibleText, locator);
            } catch (NoSuchElementException e) {
                // If exact match fails, try partial match
                logger.warn("Exact match failed for '{}', trying partial match", visibleText);
                boolean found = false;
                for (WebElement option : select.getOptions()) {
                    if (option.getText().contains(visibleText) || visibleText.contains(option.getText())) {
                        option.click();
                        logger.info("Selected '{}' (partial match) from dropdown: {}", option.getText(), locator);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to select from dropdown: {}", locator, e);
            throw new RuntimeException("Select failed: " + locator, e);
        }
    }
    
    /**
     * Select dropdown option by value
     * 
     * @param locator Dropdown locator
     * @param value Value to select
     */
    @Step("Select value '{value}' from dropdown: {locator}")
    protected void selectByValue(By locator, String value) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            Select select = new Select(element);
            select.selectByValue(value);
            logger.info("Selected value '{}' from dropdown: {}", value, locator);
        } catch (Exception e) {
            logger.error("Failed to select by value from dropdown: {}", locator, e);
            throw new RuntimeException("Select by value failed: " + locator, e);
        }
    }
    
    /**
     * Select dropdown option by index
     * 
     * @param locator Dropdown locator
     * @param index Index to select
     */
    @Step("Select index {index} from dropdown: {locator}")
    protected void selectByIndex(By locator, int index) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            Select select = new Select(element);
            select.selectByIndex(index);
            logger.info("Selected index {} from dropdown: {}", index, locator);
        } catch (Exception e) {
            logger.error("Failed to select by index from dropdown: {}", locator, e);
            throw new RuntimeException("Select by index failed: " + locator, e);
        }
    }
    
    /**
     * Select from Guidewire custom dropdown (div-based)
     * Guidewire uses div elements instead of standard select dropdowns
     * 
     * @param dropdownLocator The dropdown container locator
     * @param optionText The visible text of the option to select
     */
    @Step("Select '{optionText}' from Guidewire dropdown")
    protected void selectFromGuidewireDropdown(By dropdownLocator, String optionText) {
        try {
            // Wait for dropdown to be visible and clickable
            WebElement dropdown = WaitUtils.waitForElementClickable(driver, dropdownLocator);
            
            // Click on the dropdown div to expand options
            dropdown.click();
            logger.info("Clicked Guidewire dropdown: {}", dropdownLocator);
            
            // Wait a moment for dropdown to expand
            Thread.sleep(500);
            
            // Try multiple possible option locator patterns
            By[] optionLocators = {
                // Pattern 1: Direct child select element option
                By.xpath("//select[contains(@id,'" + getIdFromLocator(dropdownLocator) + "')]//option[text()='" + optionText + "']"),
                // Pattern 2: Sibling div with options
                By.xpath("//*[@id='" + getIdFromLocator(dropdownLocator) + "']//following-sibling::div//div[text()='" + optionText + "']"),
                // Pattern 3: Option within the dropdown div itself
                By.xpath("//*[@id='" + getIdFromLocator(dropdownLocator) + "']//option[text()='" + optionText + "']"),
                // Pattern 4: General popup with option text
                By.xpath("//div[contains(@class,'gw-popup')]//div[normalize-space(text())='" + optionText + "']")
            };
            
            WebElement option = null;
            for (By locator : optionLocators) {
                try {
                    option = WaitUtils.waitForElementClickable(driver, locator, 5);
                    logger.info("Found option using locator: {}", locator);
                    break;
                } catch (Exception e) {
                    logger.debug("Option not found with locator: {}", locator);
                }
            }
            
            if (option != null) {
                option.click();
                logger.info("Selected '{}' from Guidewire dropdown", optionText);
            } else {
                throw new RuntimeException("Could not find option '" + optionText + "' in dropdown");
            }
        } catch (Exception e) {
            logger.error("Failed to select from Guidewire dropdown: {}", dropdownLocator, e);
            throw new RuntimeException("Guidewire dropdown selection failed: " + dropdownLocator, e);
        }
    }
    
    /**
     * Extract ID from By locator
     */
    private String getIdFromLocator(By locator) {
        String locatorStr = locator.toString();
        if (locatorStr.contains("By.id: ")) {
            return locatorStr.substring(locatorStr.indexOf("By.id: ") + 7);
        }
        return "";
    }
    
    /**
     * Wait for element to be visible
     * 
     * @param locator Element locator
     */
    protected void waitForElementVisible(By locator) {
        WaitUtils.waitForElementVisible(driver, locator);
        logger.info("Element is visible: {}", locator);
    }
    
    /**
     * Wait for element to be clickable
     * 
     * @param locator Element locator
     */
    protected void waitForElementClickable(By locator) {
        WaitUtils.waitForElementClickable(driver, locator);
        logger.info("Element is clickable: {}", locator);
    }
    
    /**
     * Find elements by locator
     * 
     * @param locator Element locator
     * @return List of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        logger.info("Found {} elements for locator: {}", elements.size(), locator);
        return elements;
    }
    
    /**
     * Scroll to element
     * 
     * @param locator Element locator
     */
    protected void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {}", locator, e);
            throw new RuntimeException("Scroll failed: " + locator, e);
        }
    }
    
    /**
     * Click using JavaScript
     * 
     * @param locator Element locator
     */
    protected void jsClick(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("JavaScript clicked on element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to JS click on element: {}", locator, e);
            throw new RuntimeException("JS click failed: " + locator, e);
        }
    }
    
    /**
     * Get page title
     * 
     * @return Page title
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }
    
    /**
     * Get current URL
     * 
     * @return Current URL
     */
    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }
    
    /**
     * Navigate to URL
     * 
     * @param url URL to navigate
     */
    @Step("Navigate to URL: {url}")
    protected void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
    }
    
    /**
     * Hover over element
     * 
     * @param locator Element locator
     */
    protected void hover(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to hover over element: {}", locator, e);
            throw new RuntimeException("Hover failed: " + locator, e);
        }
    }
    
    /**
     * Switch to frame by index
     * 
     * @param index Frame index
     */
    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
        logger.info("Switched to frame index: {}", index);
    }
    
    /**
     * Switch to frame by name or ID
     * 
     * @param nameOrId Frame name or ID
     */
    protected void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
        logger.info("Switched to frame: {}", nameOrId);
    }
    
    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched to default content");
    }
    
    /**
     * Accept alert
     */
    protected void acceptAlert() {
        driver.switchTo().alert().accept();
        logger.info("Alert accepted");
    }
    
    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        driver.switchTo().alert().dismiss();
        logger.info("Alert dismissed");
    }
    
    /**
     * Get alert text
     * 
     * @return Alert text
     */
    protected String getAlertText() {
        String alertText = driver.switchTo().alert().getText();
        logger.info("Alert text: {}", alertText);
        return alertText;
    }
}
