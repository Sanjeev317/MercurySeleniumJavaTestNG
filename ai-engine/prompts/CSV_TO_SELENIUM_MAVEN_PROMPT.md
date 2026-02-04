# CSV Test Case to Selenium Maven Test Automation - Prompt Template

## Instructions for Use
Copy the prompt below and provide your CSV test case. The AI will generate complete test automation files following your exact Maven framework structure.

**IMPORTANT:** Specify which environment configuration to use (qa, uat, or prod).

---

## PROMPT START

You are an expert Selenium WebDriver + Java + TestNG automation architect.

You MUST strictly follow the existing Maven framework structure and patterns.
Do NOT invent new architecture.
Do NOT change folder names or package structure.
Do NOT change conventions.
Do NOT generate explanations unless explicitly asked.

### APPLICATION CONFIGURATION
**Environment:** {ENVIRONMENT}
*Specify either 'qa', 'uat', or 'prod' - will use corresponding base URL from config.properties*

---

### MAVEN PROJECT STRUCTURE
```
enterprise-mercury-Insu-automation-platform/
├── automation-framework/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/enterprise/mercury/
│   │   │   ├── core/
│   │   │   │   ├── config/
│   │   │   │   │   └── ConfigManager.java
│   │   │   │   ├── driver/
│   │   │   │   │   └── DriverFactory.java
│   │   │   │   ├── listeners/
│   │   │   │   │   └── TestListener.java
│   │   │   │   ├── reporting/
│   │   │   │   │   └── AllureConfig.java
│   │   │   │   └── utils/
│   │   │   │       ├── DataReader.java
│   │   │   │       └── WaitUtils.java
│   │   │   ├── api/
│   │   │   │   └── clients/
│   │   │   │       └── ApiClient.java
│   │   │   └── ui/
│   │   │       └── pages/
│   │   │           ├── BasePage.java
│   │   │           └── LoginPage.java (example)
│   │   └── test/java/com/enterprise/mercury/
│   │       ├── api/tests/
│   │       │   └── LoginApiTest.java (example)
│   │       └── ui/tests/
│   │           ├── BaseTest.java
│   │           └── LoginTest.java (example)
├── resources/
│   ├── config.properties
│   ├── log4j2.xml
│   └── testng.xml
└── test-assets/
    ├── manual-testcases/
    │   └── sample_manual_tests.md
    └── test-data/
        ├── api/
        │   └── loginPayload.json
        └── ui/
            └── loginData.json
```

---

### EXISTING ARCHITECTURE RULES

#### 1. Page Objects (src/main/java/com/enterprise/mercury/ui/pages/)
- **MUST** extend `BasePage` class
- Use **private final By** locators (not public properties)
- All interaction methods **MUST** use BasePage protected methods (click, type, getText, isDisplayed, etc.)
- Use **WaitUtils** for explicit waits when needed
- **NO** test data inside page objects
- Accept **JsonNode** or individual parameters
- Use **@Step** annotation from Allure for all public methods
- Include **JavaDoc** comments for all methods
- Use descriptive method names matching user actions
- Include **logger** statements for debugging

**Package:** `com.enterprise.mercury.ui.pages`

**Naming Pattern:** `{FeatureName}Page.java` (e.g., `ClaimCreationPage.java`)

**Pattern:**
```java
package com.enterprise.mercury.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * {Feature} Page Object
 * Contains all locators and methods for {Feature} page interactions
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
public class SamplePage extends BasePage {
    
    // Locators
    private final By fieldLocator = By.id("field-id");
    private final By buttonLocator = By.id("button-id");
    private final By dropdownLocator = By.id("dropdown-id");
    
    /**
     * Constructor
     */
    public SamplePage() {
        super();
        logger.info("SamplePage initialized");
    }
    
    /**
     * Enter field value
     * 
     * @param value Value to enter
     */
    @Step("Enter field value: {value}")
    public void enterField(String value) {
        logger.info("Entering field value: {}", value);
        type(fieldLocator, value);
    }
    
    /**
     * Click button
     */
    @Step("Click button")
    public void clickButton() {
        logger.info("Clicking button");
        click(buttonLocator);
    }
    
    /**
     * Select dropdown option
     * 
     * @param optionText Option text to select
     */
    @Step("Select dropdown option: {optionText}")
    public void selectDropdown(String optionText) {
        logger.info("Selecting dropdown option: {}", optionText);
        selectByVisibleText(dropdownLocator, optionText);
    }
    
    /**
     * Composite method for filling multiple fields
     * 
     * @param field1 First field value
     * @param field2 Second field value
     */
    @Step("Fill form with multiple fields")
    public void fillForm(String field1, String field2) {
        logger.info("Filling form with multiple fields");
        enterField(field1);
        // ... other fields
    }
    
    /**
     * Verify page is displayed
     * 
     * @return true if page is displayed
     */
    public boolean isPageDisplayed() {
        logger.info("Checking if page is displayed");
        return isDisplayed(fieldLocator);
    }
}
```

#### 2. Test Classes (src/test/java/com/enterprise/mercury/ui/tests/)
- **MUST** extend `BaseTest` class
- Use **TestNG** annotations (@Test, @BeforeMethod, @AfterMethod)
- Import page objects from `com.enterprise.mercury.ui.pages`
- Use **DataReader** utility to read test data from JSON
- **MUST** use Allure annotations (@Epic, @Feature, @Story, @Description, @Severity)
- Include **priority** in @Test annotation for execution order
- Use **Assert** class for assertions
- Include **logger** statements for debugging
- Use **try-catch** with Thread.sleep only when necessary (prefer WaitUtils)
- Test methods should be descriptive and follow naming: `test{FeatureAction}`

**Package:** `com.enterprise.mercury.ui.tests`

**Naming Pattern:** `{FeatureName}Test.java` (e.g., `ClaimCreationTest.java`)

**Pattern:**
```java
package com.enterprise.mercury.ui.tests;

import com.enterprise.mercury.core.utils.DataReader;
import com.enterprise.mercury.ui.pages.SamplePage;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {Feature} Test Suite
 * Tests for {Feature} functionality using JSON test data
 * 
 * @author Enterprise Automation Team
 * @version 1.0
 */
@Epic("{Epic Name}")
@Feature("{Feature Name}")
public class SampleTest extends BaseTest {
    
    /**
     * Test description
     */
    @Test(priority = 1, description = "Test description")
    @Severity(SeverityLevel.BLOCKER)
    @Story("{Story Name}")
    @Description("Detailed test description")
    public void testSampleFeature() {
        logger.info("Starting testSampleFeature");
        
        // Read test data from JSON
        JsonNode testData = DataReader.readUITestData("sampleData.json");
        String field1 = testData.get("testCase1").get("field1").asText();
        String field2 = testData.get("testCase1").get("field2").asText();
        String expectedResult = testData.get("testCase1").get("expectedResult").asText();
        
        logger.info("Test data loaded - Field1: {}", field1);
        
        // Create page object
        SamplePage samplePage = new SamplePage();
        
        // Verify page is displayed
        Assert.assertTrue(samplePage.isPageDisplayed(), "Page should be displayed");
        
        // Perform actions
        samplePage.fillForm(field1, field2);
        samplePage.clickButton();
        
        // Wait for result
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify result
        String actualResult = samplePage.getResultText();
        Assert.assertEquals(actualResult, expectedResult, "Result should match expected");
        
        logger.info("testSampleFeature completed successfully");
    }
}
```

#### 3. Test Data (test-assets/test-data/ui/)
- Store under `test-assets/test-data/ui/`
- Use **JSON format** (.json files)
- Structure data with test case keys
- Include descriptive fields for clarity
- Group related data logically
- Use camelCase for property names
- Include expectedResults for assertions

**Naming Pattern:** `{featureName}Data.json` (e.g., `claimCreationData.json`)

**Pattern:**
```json
{
  "testCase1": {
    "field1": "value1",
    "field2": "value2",
    "dropdown": "Option1",
    "expectedResult": "Success",
    "description": "Test case description"
  },
  "testCase2": {
    "field1": "value1",
    "field2": "value2",
    "expectedError": "Error message",
    "description": "Negative test case"
  }
}
```

#### 4. BasePage Methods Available (DO NOT RECREATE)
The following methods are already available in BasePage and MUST be used:

**Element Interaction:**
- `click(By locator)` - Click with wait (includes automatic retry for stale elements - up to 3 attempts with 500ms delay)
- `type(By locator, String text)` - Type with clear
- `typeWithoutClear(By locator, String text)` - Append text
- `clear(By locator)` - Clear field
- `submit(By locator)` - Submit form
- `getText(By locator)` - Get element text
- `getAttribute(By locator, String attribute)` - Get attribute value
- `isDisplayed(By locator)` - Check visibility
- `isEnabled(By locator)` - Check enabled state
- `isSelected(By locator)` - Check selected state

**Note:** The `click()` method automatically handles `StaleElementReferenceException` by re-locating and retrying the element. This is crucial for Guidewire applications where DOM updates after field interactions.

**Dropdown Operations:**
- `selectByVisibleText(By locator, String text)` - Select by text (includes debug logging of all options)
- `selectByValue(By locator, String value)` - Select by value
- `selectByIndex(By locator, int index)` - Select by index
- `getSelectedOptionText(By locator)` - Get selected text
- `getAllDropdownOptions(By locator)` - Get all options

**NOTE:** The `selectByVisibleText()` method now includes automatic debugging:
- Logs all available options with their text and values
- Tries exact match first
- Falls back to partial match if exact match fails
- This helps identify correct option text for test data

**Actions:**
- `hoverOverElement(By locator)` - Mouse hover
- `doubleClick(By locator)` - Double click
- `rightClick(By locator)` - Context click
- `dragAndDrop(By source, By target)` - Drag and drop

**JavaScript:**
- `clickUsingJS(By locator)` - JS click
- `scrollToElement(By locator)` - Scroll to view
- `scrollToBottom()` - Scroll to bottom
- `scrollToTop()` - Scroll to top

**Browser Operations:**
- `getCurrentUrl()` - Get current URL
- `getPageTitle()` - Get page title
- `navigateBack()` - Go back
- `navigateForward()` - Go forward
- `refreshPage()` - Refresh
- `switchToFrame(By frameLocator)` - Switch frame
- `switchToDefaultContent()` - Exit frame
- `acceptAlert()` - Accept alert
- `dismissAlert()` - Dismiss alert
- `getAlertText()` - Get alert text

**Wait Operations (use WaitUtils):**
- `WaitUtils.waitForElementVisible(driver, locator)`
- `WaitUtils.waitForElementClickable(driver, locator)`
- `WaitUtils.waitForElementPresent(driver, locator)`
- `WaitUtils.waitForTextInElement(driver, locator, text)`
- `WaitUtils.waitForUrlContains(driver, urlPart)`

#### 5. BaseTest Methods Available
The BaseTest class provides:
- **@BeforeMethod setUp()** - Initializes driver and navigates to base URL
- **@AfterMethod tearDown()** - Quits driver
- **driver** - WebDriver instance
- **config** - ConfigManager instance
- **logger** - Logger instance

**No need to add navigation in tests** - BaseTest already handles it!

#### 6. Existing Utilities (DO NOT RECREATE)
- `ConfigManager` - Configuration management from config.properties
- `DataReader` - Reading JSON test data
- `WaitUtils` - Explicit wait utilities
- `DriverFactory` - WebDriver management (ThreadLocal)
- `TestListener` - TestNG listener for reporting
- `AllureConfig` - Allure reporting configuration

---

### SELECTOR STRATEGY
Use in this priority order:
1. `id` attribute: `By.id("elementId")`
2. `name` attribute: `By.name("fieldName")`
3. `data-test` or `data-testid`: `By.cssSelector("[data-test='testId']")`
4. CSS classes (if stable): `By.cssSelector(".unique-class")`
5. Link text: `By.linkText("Link Text")`
6. Partial link text: `By.partialLinkText("Partial")`
7. XPath (last resort): `By.xpath("//tag[@attribute='value']")`

**For dynamic content:**
- Use CSS selectors with attribute contains: `By.cssSelector("[id*='partial']")`
- Use XPath with contains: `By.xpath("//tag[contains(@attr, 'value')]")`

**GUIDEWIRE-SPECIFIC PATTERNS:**

For Guidewire ClaimCenter/PolicyCenter applications, use these patterns:

**Dropdowns (select elements nested in divs):**
```java
// Guidewire dropdowns are <select> elements nested inside divs
// Target the select element directly using XPath descendant
By dropdown = By.xpath("//*[@id='ComponentId']//select");

// Example:
By policyTypeDropdown = By.xpath("//*[@id='FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-ClaimLossType']//select");
```

**Input Fields (using name attribute):**
```java
// Guidewire input fields use long descriptive name attributes
By inputField = By.name("ComponentName-ScreenName-PanelName-fieldName");

// Example:
By policyNumberField = By.name("FNOLWizard-FNOLWizard_FindPolicyScreen-FNOLWizardFindPolicyPanelSet-policyNumber");
```

**Guidewire Naming Convention:**
`ComponentName-SubComponent-FieldName` or `WizardName-ScreenName-PanelSetName-fieldName`

**IMPORTANT for Dropdown Values:**
- Test data MUST use **visible text** (not value attribute) for `selectByVisibleText()`
- Check actual dropdown options to get exact text including capitalization
- Example: Use "Auto" not "AUTO", "Alabama Personal Auto" not "ALA"
- If value is "ALA" but visible text is "Alabama Personal Auto", use the full text in JSON

---

### INPUT FORMAT
Manual test steps in CSV format with columns:
```
Id, Requirement, Title, State, Section, Automation, Precondition, Steps(Step), Steps(Expected Result)
```

**CSV Format Rules:**
- Each step is numbered in "Steps(Step)" column
- Each expected result is numbered in "Steps(Expected Result)" column
- Steps include: Navigate, Click, Select, Enter, etc.
- Expected results describe the outcome

---

### TASK EXECUTION STAGES

#### STAGE 1 – GENERATE TEST DATA FILE
1. Parse manual steps from "Steps(Step)" column
2. Extract all unique hardcoded input values:
   - Values entered in fields: "Enter '[value]' in the [Field] field"
   - Values selected from dropdowns: "Select '[value]' from the [Field] dropdown"
   - Radio button selections: "Select '[option]' radio button"
   - Checkbox selections: "Check/Uncheck [checkbox]"
   - Date/Time inputs: "Enter '[date/time]' in the [Field] field"
   - Any hardcoded input data
3. Extract expected results from "Steps(Expected Result)" column
4. Create structured JSON test data file
5. Use logical grouping for multiple test scenarios
6. Derive meaningful property names from field names (camelCase)

**File Path:**
```
test-assets/test-data/ui/{derivedName}Data.json
```

**Naming Rules:**
- Derive from CSV Title column (remove special chars, use camelCase)
- Example: "Verify New Claim Creation with Policy Search" → `claimCreationData.json`

**JSON Structure:**
```json
{
  "validScenario": {
    "policyType": "AUTO",
    "type": "ALA",
    "policyNumber": "A48562611",
    "firstName": "Fr Test",
    "lastName": "La Test",
    "date": "07/31/2000",
    "time": "10:30",
    "expectedResult": "Policy search is executed and results are displayed",
    "description": "Happy path with valid data"
  }
}
```

#### STAGE 2 – GENERATE PAGE OBJECT
1. Create page class name derived from manual steps context or Title
2. **MUST** extend `BasePage` class
3. Create **private final By** locators for all elements
4. Create methods matching user actions from CSV steps
5. Use **BasePage protected methods** for interactions (click, type, etc.)
6. **NO** hardcoded data allowed in methods
7. Use **@Step** annotation for all public methods
8. Include **JavaDoc** comments for all methods
9. Include **logger.info()** statements
10. Group related actions into composite methods when appropriate
11. Add verification methods (is{Page}Displayed, get{Element}Text)

**File Path:**
```
automation-framework/src/main/java/com/enterprise/mercury/ui/pages/{FeatureName}Page.java
```

**Package:** `com.enterprise.mercury.ui.pages`

**Class Naming:**
- Derive from CSV Title or feature context
- Example: "Verify New Claim Creation" → `ClaimCreationPage.java`
- Use PascalCase: `ClaimCreationPage`, `PolicySearchPage`

**Method Grouping Rules:**
- One method per distinct user action
- Group consecutive field inputs into composite methods
- Separate navigation, form filling, and button clicks
- Example: `fillPolicyInfo()`, `fillPersonalInfo()`, `clickSearch()`

**Locator Naming:**
- Use descriptive names matching UI elements
- Examples: `policyTypeDropdown`, `firstNameField`, `searchButton`

#### STAGE 3 – GENERATE TEST CLASS
1. Create test class name derived from feature
2. **MUST** extend `BaseTest` class
3. Import page object from `com.enterprise.mercury.ui.pages`
4. Import `DataReader` from `com.enterprise.mercury.core.utils`
5. Use **JsonNode** to read test data
6. Use **Allure annotations** (@Epic, @Feature, @Story, @Description, @Severity)
7. Use **TestNG @Test** annotation with priority and description
8. Follow AAA pattern: Arrange (data), Act (page actions), Assert (verify)
9. Include **logger** statements
10. Use **Assert** class for verification
11. Handle waits appropriately (prefer WaitUtils over Thread.sleep)
12. **DO NOT** add navigation in @BeforeMethod - BaseTest handles it

**File Path:**
```
automation-framework/src/test/java/com/enterprise/mercury/ui/tests/{FeatureName}Test.java
```

**Package:** `com.enterprise.mercury.ui.tests`

**Class Naming:**
- Match page object name with "Test" suffix
- Example: `ClaimCreationPage` → `ClaimCreationTest`

**Test Method Naming:**
- Use `test` prefix followed by feature and scenario
- Examples: `testCreateClaimWithValidData()`, `testPolicySearchWithInvalidData()`
- Be descriptive and specific

**Allure Annotations:**
- @Epic: High-level feature area (e.g., "Claim Management")
- @Feature: Specific feature (e.g., "Claim Creation")
- @Story: User story (e.g., "Create New Claim")
- @Severity: BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL
- @Description: Detailed test description

**Test Priority:**
- priority = 1: Critical happy path
- priority = 2: Important validations
- priority = 3: Edge cases and negative tests

#### STAGE 4 – GENERATE TESTNG XML (OPTIONAL)
Only generate if user requests a custom TestNG suite XML.

**File Path:**
```
resources/{featureName}Suite.xml
```

**Pattern:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Feature Test Suite" parallel="methods" thread-count="3">
    <test name="Feature Tests">
        <classes>
            <class name="com.enterprise.mercury.ui.tests.FeatureTest"/>
        </classes>
    </test>
</suite>
```

---

### VALIDATION CHECKLIST

Before outputting, verify:
- ✅ Page object extends BasePage
- ✅ All locators are private final By
- ✅ All page methods use BasePage protected methods (click, type, etc.)
- ✅ @Step annotations on all public page methods
- ✅ JavaDoc comments on all methods
- ✅ Logger statements included
- ✅ No hardcoded data in page objects
- ✅ Test class extends BaseTest
- ✅ Test data uses JSON format
- ✅ DataReader.readUITestData() used in tests
- ✅ Allure annotations on test class and methods
- ✅ TestNG @Test annotation with priority and description
- ✅ Proper package declarations
- ✅ Correct import statements
- ✅ WaitUtils used for explicit waits
- ✅ No duplicate utility methods
- ✅ Proper naming conventions
- ✅ No navigation in test @BeforeMethod (BaseTest handles it)
- ✅ No explanatory text in output

---

### STRICT OUTPUT FORMAT

Return ONLY the generated file contents in this exact format:

```
--- test-assets/test-data/ui/{derivedName}Data.json ---
[complete file content with no truncation]

--- automation-framework/src/main/java/com/enterprise/mercury/ui/pages/{FeatureName}Page.java ---
[complete file content with no truncation]

--- automation-framework/src/test/java/com/enterprise/mercury/ui/tests/{FeatureName}Test.java ---
[complete file content with no truncation]

--- resources/{featureName}Suite.xml (optional) ---
[complete file content with no truncation]
```

**Requirements:**
- Return ONLY generated files
- NO explanations before or after
- NO markdown code blocks (```java or ```json)
- NO commentary
- NO "here's what I generated" text
- Use exact file path format shown above
- Include complete file content (do not truncate)
- Separate files with the `---` delimiter
- Use proper package declarations
- Include all necessary imports

---

### CONFIGURATION REFERENCE

**ConfigManager Usage:**
Tests automatically get base URL from config.properties based on environment setting.

**config.properties structure:**
```properties
environment=qa
base.url.qa=https://qa.mercury-insurance.com
base.url.uat=https://uat.mercury-insurance.com
base.url.prod=https://mercury-insurance.com
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20
```

**DataReader Usage:**
```java
JsonNode testData = DataReader.readUITestData("fileName.json");
String value = testData.get("testCase1").get("field").asText();
```

**BaseTest Lifecycle:**
- @BeforeMethod setUp(): Initializes driver, loads config, navigates to base URL
- @AfterMethod tearDown(): Quits driver

---

## PROMPT END

---

## Usage Example

**Step 1:** Copy the prompt above (from "PROMPT START" to "PROMPT END")

**Step 2:** Provide your CSV test case:

```
[Paste the prompt here]

**SPECIFY ENVIRONMENT:**
Environment: qa

INPUT CSV:

[Paste your CSV content or attach CSV file]
```

**Step 3:** The AI will generate all required files in the exact format specified.

**Step 4:** Review output and create files in your project:
- Copy JSON content to test-assets/test-data/ui/
- Copy Page Object to automation-framework/src/main/java/com/enterprise/mercury/ui/pages/
- Copy Test class to automation-framework/src/test/java/com/enterprise/mercury/ui/tests/
- Verify package declarations and imports
- Run Maven compile: `mvn clean compile test-compile`
- Run tests: `mvn test -Dtest={TestClassName}`

---

## Execution Commands

### Compile Project
```bash
cd automation-framework
mvn clean compile test-compile
```

### Run Specific Test
```bash
mvn test -Dtest=ClaimCreationTest
```

### Run Test with TestNG XML
```bash
mvn test -DsuiteXmlFile=../resources/claimCreationSuite.xml
```

### Run All Tests
```bash
mvn test -DsuiteXmlFile=../resources/testng.xml
```

### Generate Allure Report
```bash
mvn allure:report
mvn allure:serve
```

---

## Advanced Usage

### Multiple Test Scenarios
Generate multiple test methods for different scenarios:
```
Generate 3 test scenarios:
1. Happy path with valid data (priority=1, BLOCKER)
2. Validation with invalid data (priority=2, CRITICAL)
3. Boundary conditions (priority=3, NORMAL)
```

### Custom TestNG XML
```
Also generate a TestNG suite XML file for this feature with parallel execution.
```

### Page Object Hierarchy
```
If multiple pages are involved, create separate page objects for each page and use composition.
```

### Data-Driven Testing
```
Generate multiple test data entries in JSON for data-driven testing with @DataProvider.
```

---

## Integration Tips

### After Generation:

1. **Verify Imports**: Check all import statements are correct
2. **Update Selectors**: Replace placeholder selectors with actual element locators from your application
3. **Compile Project**: Run `mvn clean compile test-compile`
4. **Check Errors**: Fix any compilation errors in IDE
5. **Run Tests**: Execute `mvn test -Dtest={TestClassName}`
6. **Review Logs**: Check logs/ directory for execution logs - dropdown options are logged automatically
7. **Update Test Data**: Adjust JSON test data with correct visible text values from logs
8. **Add More Assertions**: Add additional verifications if needed

### For Guidewire Applications:

1. **Inspect Elements**: Use browser DevTools to inspect actual elements
2. **Dropdown Pattern**: Look for `<select>` elements nested in divs with long IDs
3. **Use //select descendant**: Target nested select elements with `//select` in XPath
4. **Input Fields**: Use `name` attribute - Guidewire uses descriptive names
5. **Check Logs**: Run test once to see all dropdown options logged
6. **Update JSON**: Use exact visible text (e.g., "Alabama Personal Auto" not "ALA")
7. **Verify Capitalization**: Match exact text including capitals (e.g., "Auto" not "AUTO")

### Customizing Generated Code:

- Update locators to match actual application elements
- Adjust waits/timeouts in config.properties if needed
- Add more test scenarios in test class
- Modify test data for different environments
- Add custom verification methods in page objects
- Enhance error handling with try-catch blocks

### Best Practices:

- Keep page objects focused on single pages
- Use composite methods for multi-step actions
- Follow Single Responsibility Principle
- Use descriptive method and variable names
- Add JavaDoc comments for clarity
- Use logger statements for debugging
- Keep tests independent and atomic
- Use Allure annotations for better reporting
- Maintain test data separately in JSON files
- Use WaitUtils for explicit waits (avoid Thread.sleep)

---

## Troubleshooting

**Issue:** Compilation errors with imports
**Solution:** Verify Maven dependencies in pom.xml and run `mvn clean install`

**Issue:** Locators not working
**Solution:** Inspect actual application elements and update locators in page object

**Issue:** Tests timing out
**Solution:** Increase timeouts in config.properties (explicit.wait, page.load.timeout)

**Issue:** BaseTest navigation not working
**Solution:** Check base.url.{environment} in config.properties

**Issue:** JSON file not found
**Solution:** Verify file path in test-assets/test-data/ui/ and check DataReader path

**Issue:** WebDriver not initializing
**Solution:** Check browser setting in config.properties and ensure WebDriverManager is working

**Issue:** Allure report not generating
**Solution:** Run `mvn clean test` first, then `mvn allure:report`

**Issue (Guidewire):** Dropdown selection fails with "Element should have been 'select' but was 'div'"
**Solution:** Use `By.xpath("//*[@id='ComponentId']//select")` to target nested select element, not the wrapper div

**Issue (Guidewire):** Cannot locate option with text
**Solution:** Check test logs for available options. Use exact visible text including capitalization. Example: "Auto" not "AUTO", "Alabama Personal Auto" not "ALA"

**Issue (Guidewire):** Input field not found
**Solution:** Use `By.name()` with full Guidewire name attribute like `"WizardName-ScreenName-PanelSet-fieldName"`

**Issue (Guidewire):** StaleElementReferenceException when clicking buttons after form input
**Solution:** No action needed - BasePage.click() automatically retries up to 3 times with 500ms delay. You'll see "Stale element on attempt 1, retrying..." in logs, which is normal. This happens because Guidewire updates DOM after field interactions (e.g., clicking AM/PM button after entering time).

**Issue (Guidewire):** Element not clickable after dropdown selection
**Solution:** Field may appear dynamically. Wait for element visibility or add small wait after dropdown selection

---

## File Naming Conventions

| CSV Title | Test Data File | Page Object | Test Class |
|-----------|---------------|-------------|-----------|
| "Verify New Claim Creation with Policy Search" | `claimCreationData.json` | `ClaimCreationPage.java` | `ClaimCreationTest.java` |
| "Home Insurance Quote Flow" | `homeInsuranceData.json` | `HomeInsurancePage.java` | `HomeInsuranceTest.java` |
| "User Login Authentication" | `userLoginData.json` | `UserLoginPage.java` | `UserLoginTest.java` |
| "Policy Search and Validation" | `policySearchData.json` | `PolicySearchPage.java` | `PolicySearchTest.java` |

**Rules:**
- Remove special characters and "Verify/Test" prefixes
- Use PascalCase for Java classes
- Use camelCase for JSON files
- Keep names descriptive but concise
- Match page object and test class names

---

## Example CSV Input

```csv
Id,Requirement,Title,State,Section,Automation,Precondition,Steps(Step),Steps(Expected Result)
C######,TBD,"Verify New Claim Creation with Policy Search",,Claim Management,Yes,,"1. Navigate to ClaimCenter application.
2. Click on the Claim Tab expand button.
3. Click on the 'New Claim' menu item.
4. Select 'AUTO' from the Policy Type dropdown.
5. Select 'ALA' from the Type dropdown.
6. Click on the Policy # input field.
7. Enter 'A48562611' in the Policy # field.
8. Click on the First Name input field.
9. Enter 'Fr Test' in the First Name field.
10. Click on the Last Name input field.
11. Enter 'La Test' in the Last Name field.
12. Click on the date input field.
13. Enter '07/31/2000' in the date field.
14. Click on the time input field.
15. Enter '10:30' in the time field.
16. Click the 'Search' button.","1. ClaimCenter application is displayed.
2. Claim Tab menu is expanded.
3. New Claim page is displayed.
4. 'AUTO' is selected and dropdown closes.
5. 'ALA' is selected and dropdown closes.
6. Policy # field is active and ready for input.
7. Policy # 'A48562611' is entered successfully.
8. First Name field is active and ready for input.
9. First Name 'Fr Test' is entered successfully.
10. Last Name field is active and ready for input.
11. Last Name 'La Test' is entered successfully.
12. Date field is active and ready for input.
13. Date '07/31/2000' is entered and accepted.
14. Time field is active and ready for input.
15. Time '10:30' is entered and formatted.
16. Policy search is executed and results are displayed."
```

---

## Maven Dependencies Reference

The framework includes these dependencies (already configured in pom.xml):
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- WebDriverManager 5.7.0
- Allure TestNG 2.25.0
- Log4j2 2.22.1
- Jackson 2.16.1
- RestAssured 5.4.0
- Commons IO 2.15.1

**No need to add dependencies** - they are already available!

---

## Project Configuration Files

### pom.xml
Contains all Maven dependencies, plugins, and build configuration.
**Location:** `automation-framework/pom.xml`

### config.properties
Contains environment URLs, browser settings, timeouts, credentials.
**Location:** `resources/config.properties`

### log4j2.xml
Contains logging configuration (console and file appenders).
**Location:** `resources/log4j2.xml`

### testng.xml
Contains TestNG suite configuration with parallel execution settings.
**Location:** `resources/testng.xml`

---

## Additional Notes

### Page Object Best Practices:
1. One page object per logical page/component
2. Encapsulate element locators as private
3. Expose only methods, not elements
4. Use meaningful method names matching user actions
5. Return page objects for method chaining (optional)
6. Add verification methods (isDisplayed, getText)
7. **Use BasePage methods** - Don't re-implement click(), type(), etc. - they include built-in retry logic for stale elements

### Test Class Best Practices:
1. One test class per page object
2. Multiple test methods for different scenarios
3. Use priority for execution order
4. Add descriptive @Description annotations
5. Use appropriate @Severity levels
6. Keep tests independent
7. Use meaningful assertions with messages
8. Add logger statements for debugging
9. **Expect stale element warnings** - BasePage.click() will log warnings and auto-retry when DOM updates occur

### Test Data Best Practices:
1. One JSON file per feature
2. Multiple test scenarios in same file
3. Include descriptive field names
4. Add "description" property for clarity
5. Include expectedResults for assertions
6. Keep data environment-agnostic

---

## Support and Maintenance

**Generated Code Review:**
- Check package declarations match folder structure
- Verify all imports are available
- Ensure locators are placeholder comments (need actual values)
- Confirm BasePage methods are used correctly
- Validate JSON structure is valid

**Code Quality:**
- Follow Java naming conventions
- Add comprehensive JavaDoc comments
- Use logger for debugging
- Include Allure @Step annotations
- Handle exceptions appropriately
- Write descriptive assertion messages

**Testing:**
- Compile before running: `mvn clean compile test-compile`
- Run single test first to verify
- Check logs for errors
- Update selectors based on actual application
- Add more test scenarios as needed
- Run with different test data sets

---

This prompt template is specifically designed for the **Enterprise Mercury Insurance Automation Platform** Maven project structure. Follow the guidelines strictly to ensure generated code integrates seamlessly with the existing framework.
