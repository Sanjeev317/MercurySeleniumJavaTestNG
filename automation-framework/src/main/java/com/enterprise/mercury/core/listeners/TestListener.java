package com.enterprise.mercury.core.listeners;

import com.enterprise.mercury.core.driver.DriverFactory;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestNG Listener for test execution events
 * Handles logging, screenshots, and Allure reporting
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class TestListener implements ITestListener {
    
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("========================================");
        logger.info("TEST SUITE STARTED: {}", context.getName());
        logger.info("========================================");
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("========================================");
        logger.info("TEST SUITE FINISHED: {}", context.getName());
        logger.info("Total Tests: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
        logger.info("========================================");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info(">>> TEST STARTED: {}", getTestMethodName(result));
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✓ TEST PASSED: {} - Duration: {}ms", 
                   getTestMethodName(result), 
                   result.getEndMillis() - result.getStartMillis());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("✗ TEST FAILED: {}", getTestMethodName(result));
        logger.error("Failure Reason: {}", result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        if (DriverFactory.isDriverInitialized()) {
            captureScreenshot(result);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⊘ TEST SKIPPED: {}", getTestMethodName(result));
        if (result.getThrowable() != null) {
            logger.warn("Skip Reason: {}", result.getThrowable().getMessage());
        }
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: {}", getTestMethodName(result));
    }
    
    /**
     * Get formatted test method name
     * 
     * @param result ITestResult
     * @return Test method name with class
     */
    private String getTestMethodName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }
    
    /**
     * Capture screenshot and attach to Allure report
     * 
     * @param result ITestResult
     */
    private void captureScreenshot(ITestResult result) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = getTestMethodName(result) + "_" + timestamp;
            
            saveScreenshot(screenshot, screenshotName);
            logger.info("Screenshot captured: {}", screenshotName);
            
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
        }
    }
    
    /**
     * Attach screenshot to Allure report
     * 
     * @param screenshot Screenshot bytes
     * @param name Screenshot name
     * @return Screenshot bytes
     */
    @Attachment(value = "{name}", type = "image/png")
    private byte[] saveScreenshot(byte[] screenshot, String name) {
        return screenshot;
    }
    
    /**
     * Attach text to Allure report
     * 
     * @param message Text message
     * @return Message string
     */
    @Attachment(value = "Test Log", type = "text/plain")
    public static String attachText(String message) {
        return message;
    }
}
