package com.enterprise.mercury.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Login Page Object
 * Contains all locators and methods for Login page interactions
 * Updated for SauceDemo website
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class LoginPage extends BasePage {
    
    // Locators - Updated for SauceDemo website
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By loginLogo = By.cssSelector(".login_logo");
    private final By loginContainer = By.cssSelector(".login_container");
    private final By loginTitle = By.cssSelector(".login_logo"); // Using logo as title for SauceDemo
    private final By rememberMeCheckbox = By.id("remember-me"); // Placeholder (doesn't exist on SauceDemo)
    private final By forgotPasswordLink = By.linkText("Forgot Password?"); // Placeholder (doesn't exist on SauceDemo)
    
    /**
     * Constructor
     */
    public LoginPage() {
        super();
        logger.info("LoginPage initialized");
    }
    
    /**
     * Enter username
     * 
     * @param username Username to enter
     */
    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameField, username);
    }
    
    /**
     * Enter password
     * 
     * @param password Password to enter
     */
    @Step("Enter password")
    public void enterPassword(String password) {
        logger.info("Entering password");
        type(passwordField, password);
    }
    
    /**
     * Click login button
     */
    @Step("Click login button")
    public void clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);
    }
    
    /**
     * Check Remember Me checkbox
     */
    @Step("Check Remember Me")
    public void checkRememberMe() {
        logger.info("Checking Remember Me checkbox");
        click(rememberMeCheckbox);
    }
    
    /**
     * Click Forgot Password link
     */
    @Step("Click Forgot Password")
    public void clickForgotPassword() {
        logger.info("Clicking Forgot Password link");
        click(forgotPasswordLink);
    }
    
    /**
     * Get error message text
     * 
     * @return Error message
     */
    @Step("Get error message")
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(errorMessage);
    }
    
    /**
     * Check if error message is displayed
     * 
     * @return true if error displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isDisplayed(errorMessage);
    }
    
    /**
     * Get login page title
     * 
     * @return Login page title
     */
    public String getLoginTitle() {
        logger.info("Getting login page title");
        return getText(loginTitle);
    }
    
    /**
     * Verify login page is displayed
     * 
     * @return true if login page is loaded
     */
    public boolean isLoginPageDisplayed() {
        logger.info("Verifying login page is displayed");
        return isDisplayed(loginTitle) && isDisplayed(usernameField) && isDisplayed(passwordField);
    }
    
    /**
     * Complete login with credentials
     * 
     * @param username Username
     * @param password Password
     */
    @Step("Login with username: {username}")
    public void login(String username, String password) {
        logger.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Login with Remember Me option
     * 
     * @param username Username
     * @param password Password
     */
    @Step("Login with Remember Me - username: {username}")
    public void loginWithRememberMe(String username, String password) {
        logger.info("Performing login with Remember Me for username: {}", username);
        enterUsername(username);
        enterPassword(password);
        checkRememberMe();
        clickLoginButton();
    }
}
