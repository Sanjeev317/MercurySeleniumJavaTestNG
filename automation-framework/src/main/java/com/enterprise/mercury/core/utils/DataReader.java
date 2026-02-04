package com.enterprise.mercury.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Data Reader Utility for reading test data from JSON files
 * Supports both Jackson and Gson for JSON parsing
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class DataReader {
    
    private static final Logger logger = LogManager.getLogger(DataReader.class);
    private static final String TEST_DATA_BASE_PATH = "../test-assets/test-data/";
    
    // Private constructor
    private DataReader() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Read JSON file and return as JsonNode (Jackson)
     * 
     * @param fileName JSON file name
     * @return JsonNode object
     */
    public static JsonNode readJsonAsJsonNode(String fileName) {
        String filePath = TEST_DATA_BASE_PATH + fileName;
        logger.info("Reading JSON file: {}", filePath);
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            
            if (!jsonFile.exists()) {
                throw new IOException("File not found: " + filePath);
            }
            
            JsonNode jsonNode = mapper.readTree(jsonFile);
            logger.debug("JSON file parsed successfully");
            return jsonNode;
            
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}", filePath, e);
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Read JSON file and return as JsonObject (Gson)
     * 
     * @param fileName JSON file name
     * @return JsonObject
     */
    public static JsonObject readJsonAsJsonObject(String fileName) {
        String filePath = TEST_DATA_BASE_PATH + fileName;
        logger.info("Reading JSON file: {}", filePath);
        
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            logger.debug("JSON file parsed successfully");
            return jsonObject;
            
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}", filePath, e);
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Read JSON file and convert to Java Object
     * 
     * @param fileName JSON file name
     * @param clazz Target class type
     * @param <T> Generic type
     * @return Object of type T
     */
    public static <T> T readJsonAsObject(String fileName, Class<T> clazz) {
        String filePath = TEST_DATA_BASE_PATH + fileName;
        logger.info("Reading JSON file and converting to {}: {}", clazz.getSimpleName(), filePath);
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            
            if (!jsonFile.exists()) {
                throw new IOException("File not found: " + filePath);
            }
            
            T object = mapper.readValue(jsonFile, clazz);
            logger.debug("JSON converted to {} successfully", clazz.getSimpleName());
            return object;
            
        } catch (IOException e) {
            logger.error("Failed to convert JSON to object: {}", filePath, e);
            throw new RuntimeException("Unable to convert JSON to object: " + filePath, e);
        }
    }
    
    /**
     * Read JSON file and return as Map
     * 
     * @param fileName JSON file name
     * @return Map of key-value pairs
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readJsonAsMap(String fileName) {
        String filePath = TEST_DATA_BASE_PATH + fileName;
        logger.info("Reading JSON file as Map: {}", filePath);
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            
            if (!jsonFile.exists()) {
                throw new IOException("File not found: " + filePath);
            }
            
            Map<String, Object> map = mapper.readValue(jsonFile, Map.class);
            logger.debug("JSON converted to Map successfully");
            return map;
            
        } catch (IOException e) {
            logger.error("Failed to convert JSON to Map: {}", filePath, e);
            throw new RuntimeException("Unable to convert JSON to Map: " + filePath, e);
        }
    }
    
    /**
     * Read JSON content as String
     * 
     * @param fileName JSON file name
     * @return JSON content as String
     */
    public static String readJsonAsString(String fileName) {
        String filePath = TEST_DATA_BASE_PATH + fileName;
        logger.info("Reading JSON file as String: {}", filePath);
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            logger.debug("JSON file read as String successfully");
            return content;
            
        } catch (IOException e) {
            logger.error("Failed to read JSON file as String: {}", filePath, e);
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Get specific value from JSON by key path
     * 
     * @param fileName JSON file name
     * @param keyPath Key path (e.g., "user.credentials.username")
     * @return Value as String
     */
    public static String getJsonValue(String fileName, String keyPath) {
        logger.info("Getting value for key path: {} from file: {}", keyPath, fileName);
        
        try {
            JsonNode rootNode = readJsonAsJsonNode(fileName);
            String[] keys = keyPath.split("\\.");
            
            JsonNode currentNode = rootNode;
            for (String key : keys) {
                currentNode = currentNode.get(key);
                if (currentNode == null) {
                    throw new RuntimeException("Key not found: " + keyPath);
                }
            }
            
            String value = currentNode.asText();
            logger.debug("Retrieved value: {}", value);
            return value;
            
        } catch (Exception e) {
            logger.error("Failed to get value for key path: {}", keyPath, e);
            throw new RuntimeException("Unable to get value for key: " + keyPath, e);
        }
    }
    
    /**
     * Read UI test data from JSON
     * 
     * @param fileName JSON file name (e.g., "ui/loginData.json")
     * @return JsonNode object
     */
    public static JsonNode readUITestData(String fileName) {
        return readJsonAsJsonNode("ui/" + fileName);
    }
    
    /**
     * Read API test data from JSON
     * 
     * @param fileName JSON file name (e.g., "api/loginPayload.json")
     * @return JsonNode object
     */
    public static JsonNode readAPITestData(String fileName) {
        return readJsonAsJsonNode("api/" + fileName);
    }
}
