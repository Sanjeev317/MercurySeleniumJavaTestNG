# Sample Manual Test Cases for AI Conversion

## Test Case 1: Login with Valid Credentials

**Test Case ID:** TC-LOGIN-001  
**Title:** Verify successful login with valid credentials  
**Priority:** High  
**Module:** Login  
**Tags:** smoke, regression, login, positive

**Preconditions:**
- User account exists in the system
- Application is accessible
- User is not already logged in

**Test Steps:**
1. Navigate to the login page at https://mercury-insurance.com/login
2. Enter valid username in the username field: "testuser@mercury.com"
3. Enter valid password in the password field: "Test@1234"
4. Click on the "Login" button
5. Wait for the dashboard page to load

**Expected Results:**
1. Login page should be displayed with username and password fields
2. Username should be entered successfully in the field
3. Password should be masked (displayed as dots) and entered successfully
4. User should be redirected to the dashboard page
5. Dashboard page should display welcome message with username

**Test Data:**
- Username: testuser@mercury.com
- Password: Test@1234
- Expected Page Title: Dashboard - Mercury Insurance
- Expected Welcome Message: Welcome, Test User

---

## Test Case 2: Login with Invalid Credentials

**Test Case ID:** TC-LOGIN-002  
**Title:** Verify login fails with invalid credentials  
**Priority:** High  
**Module:** Login  
**Tags:** regression, login, negative

**Preconditions:**
- Application is accessible
- User is on login page

**Test Steps:**
1. Navigate to the login page
2. Enter invalid username: "invalid@mercury.com"
3. Enter invalid password: "WrongPassword123"
4. Click on the "Login" button
5. Verify error message is displayed

**Expected Results:**
1. Login page is displayed
2. Invalid username is entered in the field
3. Invalid password is entered in the field
4. Login attempt is made
5. Error message "Invalid username or password" should be displayed in red color

**Test Data:**
- Username: invalid@mercury.com
- Password: WrongPassword123
- Expected Error: Invalid username or password

---

## Test Case 3: Login with Empty Fields

**Test Case ID:** TC-LOGIN-003  
**Title:** Verify validation message when login fields are empty  
**Priority:** Medium  
**Module:** Login  
**Tags:** regression, login, negative, validation

**Preconditions:**
- Application is accessible

**Test Steps:**
1. Navigate to the login page
2. Leave username field empty
3. Leave password field empty
4. Click on the "Login" button
5. Verify validation messages

**Expected Results:**
1. Login page is displayed
2. Username field remains empty
3. Password field remains empty
4. Login button is clicked
5. Validation messages appear: "Username is required" and "Password is required"

**Test Data:**
- Username: [empty]
- Password: [empty]
- Expected Validation 1: Username is required
- Expected Validation 2: Password is required

---

## Test Case 4: Create New Insurance Quote

**Test Case ID:** TC-QUOTE-001  
**Title:** Verify creation of new auto insurance quote  
**Priority:** High  
**Module:** Quote Management  
**Tags:** smoke, regression, quote, positive

**Preconditions:**
- User is logged in
- User has permission to create quotes

**Test Steps:**
1. Navigate to the dashboard page
2. Click on "New Quote" button
3. Select "Auto Insurance" from policy type dropdown
4. Enter vehicle year: "2020"
5. Enter vehicle make: "Toyota"
6. Enter vehicle model: "Camry"
7. Enter driver's first name: "John"
8. Enter driver's last name: "Doe"
9. Enter driver's date of birth: "01/15/1985"
10. Enter zip code: "90210"
11. Click "Calculate Premium" button
12. Wait for premium calculation
13. Verify quote is generated successfully

**Expected Results:**
1. Dashboard page is displayed
2. New quote page is loaded
3. Auto Insurance is selected
4. Vehicle year 2020 is entered
5. Toyota is entered as make
6. Camry is entered as model
7. First name John is entered
8. Last name Doe is entered
9. DOB 01/15/1985 is entered
10. Zip code 90210 is entered
11. Premium calculation is initiated
12. Premium calculation completes within 5 seconds
13. Quote summary page displays with premium amount, quote number, and coverage details

**Test Data:**
```json
{
  "policyType": "Auto Insurance",
  "vehicle": {
    "year": "2020",
    "make": "Toyota",
    "model": "Camry"
  },
  "driver": {
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "01/15/1985"
  },
  "zipCode": "90210"
}
```

---

## Test Case 5: Update Customer Profile

**Test Case ID:** TC-PROFILE-001  
**Title:** Verify customer can update their profile information  
**Priority:** Medium  
**Module:** Profile Management  
**Tags:** regression, profile, positive

**Preconditions:**
- User is logged in
- User has existing profile

**Test Steps:**
1. Navigate to Profile page from dashboard
2. Click "Edit Profile" button
3. Update first name to "Jane"
4. Update phone number to "(555) 123-4567"
5. Update address to "123 Main Street"
6. Update city to "Los Angeles"
7. Select state "California" from dropdown
8. Click "Save Changes" button
9. Verify success message is displayed

**Expected Results:**
1. Profile page is displayed with existing information
2. Form fields become editable
3. First name updated to Jane
4. Phone number updated to (555) 123-4567
5. Address updated to 123 Main Street
6. City updated to Los Angeles
7. California is selected from state dropdown
8. Changes are saved to database
9. Success message "Profile updated successfully" is displayed

**Test Data:**
- First Name: Jane
- Phone: (555) 123-4567
- Address: 123 Main Street
- City: Los Angeles
- State: California
- Expected Message: Profile updated successfully

---

## Instructions for AI Conversion

### To Convert These Manual Test Cases:

1. **Use the Manual to CSV Prompt**
   - Location: `ai-engine/prompts/manual_to_csv_prompt.md`
   - Copy the prompt and provide these manual test cases
   - AI will generate structured CSV file

2. **Expected CSV Output Format:**
   ```csv
   TestCaseID,TestCaseName,Priority,Module,Preconditions,TestSteps,ExpectedResults,TestData,Tags
   ```

3. **Use CSV to Selenium Prompt**
   - Location: `ai-engine/prompts/csv_to_selenium_prompt.md`
   - Provide the generated CSV
   - AI will generate:
     - Page Object classes
     - Test Data JSON files
     - TestNG test classes

4. **Generated Files Location:**
   - Page Objects: `automation-framework/ui/pages/`
   - Test Data: `test-assets/test-data/ui/`
   - Test Classes: `automation-framework/ui/tests/`

### Example Conversion Flow:

```
Manual Test Cases (this file)
        ↓
[AI with manual_to_csv_prompt.md]
        ↓
CSV File (structured data)
        ↓
[AI with csv_to_selenium_prompt.md]
        ↓
Generated Java Files:
  - LoginPage.java
  - QuotePage.java
  - ProfilePage.java
  - loginData.json
  - quoteData.json
  - profileData.json
  - LoginTest.java
  - QuoteTest.java
  - ProfileTest.java
```

### Validation Checklist:

After AI generation, verify:
- ✅ Page Objects extend BasePage
- ✅ Locators are private final By objects
- ✅ No hardcoded data in tests
- ✅ Test data in JSON files
- ✅ Tests extend BaseTest
- ✅ Proper TestNG annotations
- ✅ Allure annotations for reporting
- ✅ Code compiles without errors

---

**Note:** These are sample test cases. Update the locators, URLs, and test data according to your actual application before execution.
