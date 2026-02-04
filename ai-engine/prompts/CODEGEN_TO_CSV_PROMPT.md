# Codegen Script to CSV Test Case Converter - Prompt Template

## Instructions for Use
Copy the prompt below and provide your raw codegen script as input. The AI will convert it into a structured CSV test case.

---

## PROMPT START

I need you to convert a raw browser automation codegen script into a human-readable test case in CSV format.

### Input
I will provide a raw automation script (Playwright, Selenium, WebDriverIO, or similar) with programmatic commands.

### Output Format
Create a CSV file with the following structure:
```
Id,Requirement,Title,State,Section,Automation,Precondition,Steps(Step),Steps(Expected Result)
```

### Transformation Rules

#### 1. Remove Syntax Noise
- Strip technical syntax: `await`, `page.`, `.click()`, `.fill()`, selectors (CSS, XPath, role locators)
- Remove: import statements, test wrappers, async/await keywords
- Eliminate: implementation details like `{ exact: true }`, locator strategies

#### 2. Convert to Human-Readable Steps
Transform code into natural language using these patterns:

| Code Pattern | Human-Readable Step | Expected Result |
|-------------|---------------------|-----------------|
| `page.goto('URL')` | "Navigate to [Application/Page Name]" | "[Application/Page] is displayed" |
| `.click()` on button/link | "Click the '[Element Name]' button/link" | "[Element] is clickable/responsive" or "[Next Page] is displayed" |
| `.click()` on input field | "Click on the [Field Name] input field" | "[Field Name] field is active and ready for input" |
| `.fill('value')` | "Enter '[value]' in the [Field Name] field" | "[Field Name] '[value]' is entered successfully" |
| `.selectOption('value')` | "Select '[value]' from the [Field Name] dropdown" | "'[value]' is selected and dropdown closes" |
| `.check()` / `.setChecked(true)` | "Select the '[Option]' checkbox/radio button" | "'[Option]' is selected/checked" |
| `.isVisible()` / expect visible | "Verify '[Element/Text]' is displayed" | "[Element/Text] is visible on the page" |
| `.waitFor()` | "Wait for [Element/Page] to load" | "[Element/Page] is loaded successfully" |
| `.hover()` | "Hover over the '[Element]'" | "[Element] hover state is displayed" |
| `.dblclick()` | "Double-click the '[Element]'" | "[Element] responds to double-click" |
| `.press('Enter')` | "Press Enter key" | "Action is triggered/form is submitted" |

#### 3. Extract and Highlight Values
- Wrap all input values in single quotes: `'A48562611'`, `'AUTO'`, `'John'`
- Extract values from:
  - `.fill('value')` → value entered in field
  - `.selectOption('value')` → value selected from dropdown
  - `.type('value')` → value typed in field
- Preserve exact values as they appear in the code

#### 4. Step Numbering
- Number all steps sequentially starting from 1
- Each action in "Steps(Step)" must have a corresponding numbered result in "Steps(Expected Result)"
- Keep step and result numbers aligned

#### 5. Generate CSV Metadata
- **Id**: Use `C######` as placeholder (user can update later)
- **Requirement**: Use `TBD` or leave empty
- **Title**: Create a descriptive title from the main actions (format: "Verify [Main Action/Feature] with [Key Detail]")
- **State**: Leave empty or use "Ready"
- **Section**: Infer from URL/context or leave empty
- **Automation**: Always set to `Yes`
- **Precondition**: Leave empty unless script shows clear prerequisites

#### 6. Infer Context and Clean Up
- Derive meaningful field names from:
  - `getByLabel('Name')` → "Name field"
  - `getByRole('textbox', { name: 'Policy #' })` → "Policy # field"
  - `getByPlaceholder('Enter email')` → "Email field"
- Identify UI element types: button, dropdown, input field, checkbox, radio button, link, menu item
- Group related actions logically (e.g., multiple clicks on same field can be consolidated if redundant)
- For repeated actions, determine if intentional or can be simplified

#### 7. CSV Formatting
- Wrap multi-line cells (Steps and Expected Results) in double quotes
- Use line breaks within quoted cells for each numbered step
- Ensure proper CSV escaping for special characters
- Format: `"1. Step one.\n2. Step two.\n3. Step three."`

### Example Transformation

**Input Script:**
```javascript
import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://example.com/claims');
  await page.getByRole('button', { name: 'New Claim' }).click();
  await page.getByLabel('Policy Type').selectOption('AUTO');
  await page.getByRole('textbox', { name: 'Policy #' }).fill('A48562611');
  await page.getByRole('textbox', { name: 'First Name' }).fill('John');
  await page.getByRole('button', { name: 'Search' }).click();
});
```

**Output CSV:**
```csv
Id,Requirement,Title,State,Section,Automation,Precondition,Steps(Step),Steps(Expected Result)
C######,TBD,"Verify New Claim Creation with Policy Search",,Claims,Yes,,"1. Navigate to Claims application.
2. Click the 'New Claim' button.
3. Select 'AUTO' from the Policy Type dropdown.
4. Enter 'A48562611' in the Policy # field.
5. Enter 'John' in the First Name field.
6. Click the 'Search' button.","1. Claims application is displayed.
2. New Claim page is displayed.
3. 'AUTO' is selected and dropdown closes.
4. Policy # 'A48562611' is entered successfully.
5. First Name 'John' is entered successfully.
6. Policy search is executed and results are displayed."
```

### Special Cases

**Assertions/Verifications:**
- `expect(element).toBeVisible()` → "Verify [element] is displayed"
- `expect(element).toHaveText('text')` → "Verify [element] contains text '[text]'"
- `expect(page).toHaveURL('url')` → "Verify user is on [page name] page"

**Navigation:**
- URL changes → Indicate page transition in expected result
- `page.goBack()` → "Navigate back to previous page"
- `page.reload()` → "Refresh the current page"

**Waits and Timing:**
- `waitForSelector()` → Include as verification step if important
- Implicit waits → Usually omit unless critical for test understanding

**File Uploads:**
- `setInputFiles()` → "Upload file '[filename]' to [field name]"

**Dropdowns and Selects:**
- Always specify "Select '[value]' from the [Field Name] dropdown"
- Expected: "'[value]' is selected and dropdown closes"

### Quality Checklist
Before finalizing, ensure:
- ✅ All technical jargon removed
- ✅ Steps read like manual test instructions
- ✅ All input values are extracted and quoted
- ✅ Step numbers match between actions and results
- ✅ Title accurately describes the test scenario
- ✅ CSV is properly formatted with escaped quotes
- ✅ Each step is atomic and testable
- ✅ Professional, consistent language throughout

### Output Instructions
1. Analyze the entire script
2. Generate the CSV content with proper formatting
3. Save to file: `test-assets/manual-testcases/[descriptive-name].csv`
4. Use kebab-case for filename based on test title

---

## PROMPT END

## Usage Example

**Step 1:** Copy the prompt above (from "PROMPT START" to "PROMPT END")

**Step 2:** Add your codegen script after the prompt:

```
[Paste the prompt here]

Here is my codegen script:

[Paste your Playwright/Selenium/WebDriverIO script here]

Please create the CSV file at: test/testdetails/[your-test-name].csv
```

**Step 3:** The AI will generate the properly formatted CSV file in your project structure.

## Tips
- Be specific about the filename you want
- If the test is complex, you can ask the AI to split it into multiple test cases
- You can provide additional context about the application being tested for better titles and section names
- Review the generated steps to ensure they match your testing intent
- Update the `Id` and `Requirement` fields after generation if you have specific IDs

## Customization
You can modify the prompt to:
- Change the CSV column structure
- Add or remove columns (e.g., Priority, Tags, Test Data)
- Adjust the language style (more formal/informal)
- Include specific naming conventions for your organization
- Add validation rules for certain fields
