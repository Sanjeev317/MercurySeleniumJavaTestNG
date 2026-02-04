package com.enterprise.mercury.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

/**
 * Thread-safe WebDriver Factory using ThreadLocal pattern
 * Ensures each thread gets its own WebDriver instance for parallel execution
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class DriverFactory {
    
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    
    // ThreadLocal to store WebDriver instance per thread
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    // Private constructor to prevent instantiation
    private DriverFactory() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }
    
    /**
     * Initialize WebDriver based on browser type
     * 
     * @param browserType Browser to launch (chrome, firefox, edge)
     * @param headless Run in headless mode
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browserType, boolean headless) {
        logger.info("Initializing WebDriver for browser: {}, headless: {}", browserType, headless);
        
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "chrome":
                driver = createChromeDriver(headless);
                break;
                
            case "firefox":
                driver = createFirefoxDriver(headless);
                break;
                
            case "edge":
                driver = createEdgeDriver(headless);
                break;
                
            default:
                logger.warn("Invalid browser type: {}. Defaulting to Chrome", browserType);
                driver = createChromeDriver(headless);
                break;
        }
        
        // Configure driver settings
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
        
        // Store driver in ThreadLocal
        driverThreadLocal.set(driver);
        
        logger.info("WebDriver initialized successfully. Session ID: {}", 
                    ((RemoteWebDriver) driver).getSessionId());
        
        return driver;
    }
    
    /**
     * Create Chrome WebDriver instance
     * 
     * @param headless Run in headless mode
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless=new");
        }
        
        // Chrome performance optimizations
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        
        // Disable automation flags
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        logger.debug("Chrome options configured: headless={}", headless);
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver instance
     * 
     * @param headless Run in headless mode
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        logger.debug("Firefox options configured: headless={}", headless);
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge WebDriver instance
     * 
     * @param headless Run in headless mode
     * @return EdgeDriver instance
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");
        
        logger.debug("Edge options configured: headless={}", headless);
        
        return new EdgeDriver(options);
    }
    
    /**
     * Get the WebDriver instance for the current thread
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call initializeDriver() first.");
        }
        
        return driver;
    }
    
    /**
     * Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        
        if (driver != null) {
            try {
                logger.info("Quitting WebDriver. Session ID: {}", 
                           ((RemoteWebDriver) driver).getSessionId());
                driver.quit();
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
                logger.debug("WebDriver removed from ThreadLocal");
            }
        }
    }
    
    /**
     * Check if WebDriver is initialized for current thread
     * 
     * @return true if driver exists, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}
