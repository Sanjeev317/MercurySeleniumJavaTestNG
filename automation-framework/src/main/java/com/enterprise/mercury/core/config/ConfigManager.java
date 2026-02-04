package com.enterprise.mercury.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager to read properties from config.properties file
 * Implements Singleton pattern for global configuration access
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class ConfigManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;
    
    private static final String CONFIG_FILE_PATH = "resources/config.properties";
    
    // Private constructor for Singleton
    private ConfigManager() {
        loadProperties();
    }
    
    /**
     * Get singleton instance of ConfigManager
     * 
     * @return ConfigManager instance
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    /**
     * Load properties from config file
     */
    private void loadProperties() {
        properties = new Properties();
        
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            logger.info("Configuration loaded successfully from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", CONFIG_FILE_PATH, e);
            
            // Try loading from classpath as fallback
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (input != null) {
                    properties.load(input);
                    logger.info("Configuration loaded from classpath");
                } else {
                    throw new RuntimeException("config.properties not found in classpath");
                }
            } catch (IOException ex) {
                throw new RuntimeException("Unable to load configuration file", ex);
            }
        }
    }
    
    /**
     * Get property value by key
     * 
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }
    
    /**
     * Get property value with default fallback
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    // Convenience methods for common configurations
    
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    public String getEnvironment() {
        return getProperty("environment", "qa");
    }
    
    public String getBaseUrl() {
        String env = getEnvironment();
        return getProperty("base.url." + env);
    }
    
    public String getApiBaseUrl() {
        String env = getEnvironment();
        return getProperty("api.base.url." + env);
    }
    
    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    public String getUsername() {
        return getProperty("username");
    }
    
    public String getPassword() {
        return getProperty("password");
    }
    
    public boolean isParallelExecution() {
        return Boolean.parseBoolean(getProperty("parallel.execution", "false"));
    }
    
    public int getThreadCount() {
        return Integer.parseInt(getProperty("thread.count", "3"));
    }
    
    public boolean isTakeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }
    
    /**
     * Print all configuration properties (for debugging)
     */
    public void printAllProperties() {
        logger.info("=== Configuration Properties ===");
        properties.forEach((key, value) -> logger.info("{} = {}", key, value));
        logger.info("================================");
    }
}
