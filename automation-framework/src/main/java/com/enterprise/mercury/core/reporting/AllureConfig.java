package com.enterprise.mercury.core.reporting;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;

/**
 * Allure Report Configuration and Helper Methods
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class AllureConfig {
    
    private static final Logger logger = LogManager.getLogger(AllureConfig.class);
    
    // Private constructor
    private AllureConfig() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Add step to Allure report
     * 
     * @param stepName Step name
     */
    public static void addStep(String stepName) {
        Allure.step(stepName);
        logger.info("Allure Step: {}", stepName);
    }
    
    /**
     * Add parameter to Allure report
     * 
     * @param name Parameter name
     * @param value Parameter value
     */
    public static void addParameter(String name, String value) {
        Allure.parameter(name, value);
        logger.debug("Allure Parameter: {} = {}", name, value);
    }
    
    /**
     * Add attachment to Allure report
     * 
     * @param name Attachment name
     * @param content Content as String
     */
    public static void addAttachment(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
        logger.debug("Allure Attachment added: {}", name);
    }
    
    /**
     * Add JSON attachment to Allure report
     * 
     * @param name Attachment name
     * @param jsonContent JSON content
     */
    public static void addJsonAttachment(String name, String jsonContent) {
        Allure.addAttachment(name, "application/json", jsonContent);
        logger.debug("Allure JSON Attachment added: {}", name);
    }
    
    /**
     * Add screenshot attachment
     * 
     * @param name Screenshot name
     * @param screenshot Screenshot bytes
     */
    public static void addScreenshot(String name, byte[] screenshot) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        logger.debug("Allure Screenshot added: {}", name);
    }
    
    /**
     * Add description to test
     * 
     * @param description Test description
     */
    public static void addDescription(String description) {
        Allure.description(description);
        logger.debug("Allure Description: {}", description);
    }
    
    /**
     * Add link to test
     * 
     * @param name Link name
     * @param url Link URL
     */
    public static void addLink(String name, String url) {
        Allure.link(name, url);
        logger.debug("Allure Link: {} -> {}", name, url);
    }
}
