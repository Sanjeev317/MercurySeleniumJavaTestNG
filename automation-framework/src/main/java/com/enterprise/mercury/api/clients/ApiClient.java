package com.enterprise.mercury.api.clients;

import com.enterprise.mercury.core.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Base API Client for RestAssured
 * Provides common methods for API testing
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class ApiClient {
    
    protected static final Logger logger = LogManager.getLogger(ApiClient.class);
    protected static ConfigManager config = ConfigManager.getInstance();
    
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;
    
    /**
     * Constructor - Initialize request and response specifications
     */
    public ApiClient() {
        initializeRequestSpec();
        initializeResponseSpec();
    }
    
    /**
     * Initialize Request Specification
     */
    private void initializeRequestSpec() {
        String baseUrl = config.getApiBaseUrl();
        logger.info("Initializing API client with base URL: {}", baseUrl);
        
        RestAssured.baseURI = baseUrl;
        
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
        
        logger.info("Request specification initialized");
    }
    
    /**
     * Initialize Response Specification
     */
    private void initializeResponseSpec() {
        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
        
        logger.info("Response specification initialized");
    }
    
    /**
     * Perform GET request
     * 
     * @param endpoint API endpoint
     * @return Response
     */
    public Response get(String endpoint) {
        logger.info("Performing GET request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("GET request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform GET request with query parameters
     * 
     * @param endpoint API endpoint
     * @param queryParams Query parameters
     * @return Response
     */
    public Response get(String endpoint, Map<String, ?> queryParams) {
        logger.info("Performing GET request to: {} with query params", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("GET request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform POST request
     * 
     * @param endpoint API endpoint
     * @param body Request body
     * @return Response
     */
    public Response post(String endpoint, Object body) {
        logger.info("Performing POST request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("POST request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform POST request with JSON string
     * 
     * @param endpoint API endpoint
     * @param jsonBody JSON string body
     * @return Response
     */
    public Response post(String endpoint, String jsonBody) {
        logger.info("Performing POST request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("POST request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform PUT request
     * 
     * @param endpoint API endpoint
     * @param body Request body
     * @return Response
     */
    public Response put(String endpoint, Object body) {
        logger.info("Performing PUT request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("PUT request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform PATCH request
     * 
     * @param endpoint API endpoint
     * @param body Request body
     * @return Response
     */
    public Response patch(String endpoint, Object body) {
        logger.info("Performing PATCH request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("PATCH request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Perform DELETE request
     * 
     * @param endpoint API endpoint
     * @return Response
     */
    public Response delete(String endpoint) {
        logger.info("Performing DELETE request to: {}", endpoint);
        
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        
        logger.info("DELETE request completed - Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Set authentication header
     * 
     * @param token Auth token
     */
    public void setAuthToken(String token) {
        logger.info("Setting authentication token");
        requestSpec = new RequestSpecBuilder()
                .addRequestSpecification(requestSpec)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
    
    /**
     * Set custom header
     * 
     * @param headerName Header name
     * @param headerValue Header value
     */
    public void setHeader(String headerName, String headerValue) {
        logger.info("Setting header: {} = {}", headerName, headerValue);
        requestSpec = new RequestSpecBuilder()
                .addRequestSpecification(requestSpec)
                .addHeader(headerName, headerValue)
                .build();
    }
    
    /**
     * Set multiple headers
     * 
     * @param headers Map of headers
     */
    public void setHeaders(Map<String, String> headers) {
        logger.info("Setting multiple headers");
        requestSpec = new RequestSpecBuilder()
                .addRequestSpecification(requestSpec)
                .addHeaders(headers)
                .build();
    }
    
    /**
     * Reset request specification to default
     */
    public void resetRequestSpec() {
        logger.info("Resetting request specification");
        initializeRequestSpec();
    }
}
