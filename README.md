# Enterprise Mercury Insurance Automation Platform 🚀

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.4.0-brightgreen.svg)](https://rest-assured.io/)

Enterprise-grade test automation framework supporting both **UI (Selenium)** and **API (RestAssured)** testing with parallel execution capabilities, Allure reporting, and AI-powered test generation.

---

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Project Structure](#-project-structure)
- [Setup Instructions](#-setup-instructions)
- [Running Tests](#-running-tests)
- [Test Reports](#-test-reports)
- [Configuration](#-configuration)
- [AI Test Generation](#-ai-test-generation)
- [Writing Tests](#-writing-tests)
- [Best Practices](#-best-practices)
- [CI/CD Integration](#-cicd-integration)
- [Troubleshooting](#-troubleshooting)

---

## ✨ Features

### Core Framework Capabilities
- ✅ **Hybrid Framework** - UI (Selenium 4) + API (RestAssured) in one project
- ✅ **Page Object Model** - Clean separation of test logic and UI interactions
- ✅ **Thread-Safe Execution** - ThreadLocal<WebDriver> for parallel testing
- ✅ **Data-Driven Testing** - JSON-based test data management
- ✅ **Parallel Execution** - TestNG parallel execution support
- ✅ **Allure Reporting** - Rich, interactive test reports
- ✅ **Log4j2 Logging** - Comprehensive logging with file rotation
- ✅ **WebDriverManager** - Automatic driver management
- ✅ **Cross-Browser Support** - Chrome, Firefox, Edge
- ✅ **Headless Execution** - For CI/CD pipelines

### AI-Powered Test Generation
- 🤖 **Manual to CSV** - Convert manual test cases to structured CSV
- 🤖 **CSV to Automation** - Generate Page Objects and Test Classes from CSV
- 🤖 **Zero Code Duplication** - Intelligent code generation following POM patterns

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Test Layer                           │
│  ┌──────────────┐              ┌──────────────┐        │
│  │  UI Tests    │              │  API Tests   │        │
│  │  (TestNG)    │              │  (TestNG)    │        │
│  └──────┬───────┘              └──────┬───────┘        │
│         │                             │                 │
├─────────┼─────────────────────────────┼─────────────────┤
│         │                             │                 │
│  ┌──────▼───────┐              ┌─────▼────────┐        │
│  │ Page Objects │              │ API Clients  │        │
│  │   (POM)      │              │ (RestAssured)│        │
│  └──────┬───────┘              └──────┬───────┘        │
│         │                             │                 │
├─────────┼─────────────────────────────┼─────────────────┤
│         │                             │                 │
│  ┌──────▼──────────────────────────────▼──────┐        │
│  │          Core Framework Layer              │        │
│  │  • DriverFactory (ThreadLocal)             │        │
│  │  • ConfigManager (Properties)              │        │
│  │  • WaitUtils (Explicit Waits)              │        │
│  │  • DataReader (JSON Parser)                │        │
│  │  • TestListener (Allure Integration)       │        │
│  └────────────────────────────────────────────┘        │
└─────────────────────────────────────────────────────────┘
```

### Design Principles
- **Single Responsibility Principle** - Each class has one clear purpose
- **DRY (Don't Repeat Yourself)** - Reusable utilities and base classes
- **Separation of Concerns** - Test logic, page interactions, and data are separate
- **Thread Safety** - ThreadLocal pattern for parallel execution
- **Type Safety** - Strong typing with Java generics

---

## 📦 Prerequisites

### Required Software
- **Java JDK 17** or higher - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **Git** - [Download](https://git-scm.com/downloads)

### Optional Tools
- **IntelliJ IDEA** / **Eclipse** - IDE for development
- **Allure Commandline** - For generating reports locally
- **Docker** - For containerized execution

### Verify Installation
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.8+
git --version    # Should show Git version
```

---

## 📁 Project Structure

```
enterprise-mercury-Insu-automation-platform/
│
├── automation-framework/
│   ├── core/
│   │   ├── driver/
│   │   │   └── DriverFactory.java          # Thread-safe WebDriver factory
│   │   ├── config/
│   │   │   └── ConfigManager.java          # Configuration manager
│   │   ├── utils/
│   │   │   ├── WaitUtils.java              # Explicit wait utilities
│   │   │   └── DataReader.java             # JSON data reader
│   │   ├── listeners/
│   │   │   └── TestListener.java           # TestNG listener
│   │   └── reporting/
│   │       └── AllureConfig.java           # Allure helpers
│   │
│   ├── ui/
│   │   ├── pages/
│   │   │   ├── BasePage.java               # Base page with common methods
│   │   │   └── LoginPage.java              # Sample login page
│   │   └── tests/
│   │       ├── BaseTest.java               # Base test class
│   │       └── LoginTest.java              # Sample UI test
│   │
│   ├── api/
│   │   ├── clients/
│   │   │   └── ApiClient.java              # RestAssured client
│   │   └── tests/
│   │       └── LoginApiTest.java           # Sample API test
│   │
│   └── pom.xml                             # Maven dependencies
│
├── test-assets/
│   ├── manual-testcases/                   # Manual test documentation
│   ├── test-data/
│   │   ├── ui/
│   │   │   └── loginData.json              # UI test data
│   │   └── api/
│   │       └── loginPayload.json           # API test data
│   └── generated-scripts/                  # AI-generated scripts
│
├── resources/
│   ├── config.properties                   # Environment configuration
│   ├── log4j2.xml                          # Logging configuration
│   └── testng.xml                          # TestNG suite configuration
│
├── ai-engine/
│   ├── prompts/
│   │   ├── manual_to_csv_prompt.md         # Manual test to CSV conversion
│   │   └── csv_to_selenium_prompt.md       # CSV to Selenium generation
│   └── templates/
│       └── selenium_test_template.txt      # Code templates
│
├── .gitignore                              # Git ignore file
└── README.md                               # This file
```

---

## 🚀 Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-org/enterprise-mercury-automation.git
cd enterprise-mercury-automation
```

### 2. Install Dependencies
```bash
cd automation-framework
mvn clean install -DskipTests
```

### 3. Configure Environment
Edit `resources/config.properties`:
```properties
# Update these values for your environment
browser=chrome
headless=false
environment=qa
base.url.qa=https://your-app-url.com
```

### 4. Verify Setup
```bash
mvn clean test -Dtest=LoginTest#testSuccessfulLogin
```

---

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
# UI Tests only
mvn clean test -Dsuite=ui

# API Tests only
mvn clean test -Dsuite=api
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=LoginTest
```

### Run Specific Test Method
```bash
mvn clean test -Dtest=LoginTest#testSuccessfulLogin
```

### Run with Different Browser
```bash
mvn clean test -Dbrowser=firefox
```

### Run in Headless Mode
```bash
mvn clean test -Dheadless=true
```

### Run with Different Environment
```bash
mvn clean test -Denvironment=uat
```

### Parallel Execution
```bash
mvn clean test -Dparallel=methods -DthreadCount=3
```

### Run Tests by Tags
```bash
# Run smoke tests
mvn clean test -Dgroups=smoke

# Run regression tests
mvn clean test -Dgroups=regression

# Run multiple groups
mvn clean test -Dgroups="smoke,login"
```

---

## 📊 Test Reports

### Allure Reports

#### Generate Report
```bash
# After test execution
mvn allure:report
```

#### Open Report
```bash
mvn allure:serve
```

Report will open automatically in browser at: `http://localhost:port`

### TestNG Reports
Located at: `automation-framework/target/surefire-reports/index.html`

### Logs
- **All logs**: `logs/automation.log`
- **Error logs**: `logs/error.log`
- **Test logs**: `logs/tests.log`

---

## ⚙️ Configuration

### Browser Configuration
```properties
# config.properties
browser=chrome          # chrome, firefox, edge
headless=false          # true for CI/CD
```

### Timeout Configuration
```properties
implicit.wait=10        # seconds
explicit.wait=20        # seconds
page.load.timeout=30    # seconds
```

### Parallel Execution
```properties
parallel.execution=true
thread.count=3          # number of parallel threads
```

### Environment URLs
```properties
base.url.qa=https://qa.example.com
base.url.uat=https://uat.example.com
base.url.prod=https://www.example.com

api.base.url.qa=https://api-qa.example.com
api.base.url.uat=https://api-uat.example.com
api.base.url.prod=https://api.example.com
```

---

## 🤖 AI Test Generation

### Step 1: Convert Manual Test Cases to CSV

Use the prompt in `ai-engine/prompts/manual_to_csv_prompt.md` with your AI tool:

```
INPUT: Manual test case document
OUTPUT: Structured CSV file
```

### Step 2: Generate Automation Code from CSV

Use the prompt in `ai-engine/prompts/csv_to_selenium_prompt.md`:

```
INPUT: CSV test case
OUTPUT: 
  - Page Object class (.java)
  - Test Data JSON (.json)
  - Test Class (.java)
```

### Example Workflow
```
Manual Test Case (Word/Excel)
        ↓
    AI Prompt 1 (manual_to_csv)
        ↓
    CSV File
        ↓
    AI Prompt 2 (csv_to_selenium)
        ↓
    Java Test Files (Ready to Execute)
```

---

## ✍️ Writing Tests

### 1. Create Page Object

```java
package com.enterprise.mercury.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    
    // Locators
    private final By welcomeMessage = By.id("welcome");
    private final By logoutButton = By.id("logout");
    
    public HomePage() {
        super();
        logger.info("HomePage initialized");
    }
    
    @Step("Get welcome message")
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
    
    @Step("Click logout")
    public void clickLogout() {
        click(logoutButton);
    }
}
```

### 2. Create Test Data JSON

```json
{
  "testCase1": {
    "username": "testuser@example.com",
    "password": "Test@123",
    "expectedMessage": "Welcome"
  }
}
```

### 3. Create Test Class

```java
package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.utils.DataReader;
import com.enterprise.mercury.ui.pages.HomePage;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User Management")
@Feature("Home Page")
public class HomeTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify home page after login")
    @Severity(SeverityLevel.CRITICAL)
    public void testHomePage() {
        // Load test data
        JsonNode testData = DataReader.readUITestData("homeData.json");
        String expectedMessage = testData.get("testCase1").get("expectedMessage").asText();
        
        // Test steps
        HomePage homePage = new HomePage();
        String actualMessage = homePage.getWelcomeMessage();
        
        // Assertion
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}
```

---

## 📖 Best Practices

### DO's ✅
- **DO** extend `BasePage` for all Page Objects
- **DO** use private final By locators
- **DO** load test data from JSON files
- **DO** add `@Step` annotations for Allure
- **DO** include logging in methods
- **DO** use explicit waits
- **DO** follow naming conventions
- **DO** write atomic, independent tests
- **DO** use ThreadLocal pattern (already implemented)

### DON'Ts ❌
- **DON'T** use PageFactory (@FindBy)
- **DON'T** hardcode test data in tests
- **DON'T** put test logic in Page Objects
- **DON'T** use Thread.sleep() (use explicit waits)
- **DON'T** create static WebDriver instances
- **DON'T** write dependent tests
- **DON'T** ignore exceptions
- **DON'T** skip assertions

### Naming Conventions
- **Classes**: PascalCase (LoginPage, LoginTest)
- **Methods**: camelCase (enterUsername, clickLoginButton)
- **Variables**: camelCase (usernameField, loginButton)
- **Constants**: UPPER_SNAKE_CASE (DEFAULT_TIMEOUT)

---

## 🔄 CI/CD Integration

### Jenkins
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-org/automation.git'
            }
        }
        stage('Test') {
            steps {
                sh 'cd automation-framework && mvn clean test'
            }
        }
        stage('Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'automation-framework/target/allure-results']]
            }
        }
    }
}
```

### GitHub Actions
```yaml
name: Test Execution
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run Tests
        run: |
          cd automation-framework
          mvn clean test -Dheadless=true
      - name: Generate Report
        run: mvn allure:report
```

---

## 🐛 Troubleshooting

### Issue: WebDriver not found
**Solution**: WebDriverManager handles this automatically. Check internet connection.

### Issue: Tests failing with StaleElementReferenceException
**Solution**: Add explicit waits before element interactions using `WaitUtils`.

### Issue: JSON parsing error
**Solution**: Validate JSON format at [JSONLint](https://jsonlint.com/)

### Issue: Tests not running in parallel
**Solution**: Check `testng.xml` parallel attribute and thread-count.

### Issue: Allure report not generating
**Solution**: Ensure AspectJ is configured in pom.xml and allure-results exist.

---

## 📧 Support & Contact

For questions, issues, or contributions:
- **Email**: automation-team@mercury-insurance.com
- **Slack**: #automation-framework
- **Jira**: Create ticket in AUTOMATION project

---

## 📄 License

Copyright © 2026 Mercury Insurance. All rights reserved.

Internal use only. Not for distribution.

---

## 🙏 Acknowledgments

Built with ❤️ by the Enterprise Automation Team

**Technologies Used:**
- Selenium WebDriver
- TestNG
- RestAssured
- Allure Framework
- Log4j2
- Jackson & Gson
- WebDriverManager
- Maven

---

**Happy Testing! 🚀**
