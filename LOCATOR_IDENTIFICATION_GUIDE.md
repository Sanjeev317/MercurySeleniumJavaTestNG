## 🎯 Instructions to Update ClaimCreationPage Locators

Based on the element identification, your application loaded successfully at:
- **URL**: `https://cc-uat2-gwcpdev.acsc.beta3-andromeda.guidewire.net/ClaimCenter.do`
- **Page**: Desktop Activities (Landing page)

### ✅ What We Found:
- ✅ **Find Claim Search**: `name='TabBar-ClaimTab-ClaimTab_FindClaim'`
- ✅ **Claims Label**: Text says "Claims (26)"
- ✅ The page uses Guidewire's naming convention with patterns like `ComponentName-SubComponent-FieldName`

### ❌ What's Missing:
The page is showing the **Desktop Activities** screen, not the "New Claim" creation form. 

To proceed, you need to:

## 📋 Steps to Find Correct Locators:

### 1. Manually Navigate to Create New Claim:
Open your browser and go to:
```
https://cc-uat2-gwcpdev.acsc.beta3-andromeda.guidewire.net/service/BypassSSO?username=GtUiAdjuster&password=gw
```

Then:
1. Look for a "Claim" tab or menu
2. Click on it to expand
3. Look for "New Claim" or "FNOL" (First Notice of Loss) option
4. Click to open the claim creation form

### 2. Inspect Each Element:
For each element in your CSV test steps, do this:

**Right-click the element → Inspect → Look for:**
- `id` attribute (most reliable)
- `name` attribute (Guidewire uses patterns like `FNOLWizard-PolicySearch-PolicyNumber`)
- `class` attribute with `gw-action` for buttons
- XPath with text content

### 3. Document the Locators:
Create a list like this:

```
Step 1 - Click Claim Tab:
  Element: <div id="TabBar-ClaimTab" ...>
  Locator: By.id("TabBar-ClaimTab")

Step 2 - Click New Claim:
  Element: <div id="TabBar-ClaimTab-ClaimTab_FNOLWizard" ...>
  Locator: By.id("TabBar-ClaimTab-ClaimTab_FNOLWizard")

Step 3 - Select Policy Type:
  Element: <select name="FNOLWizard-PolicySearch-PolicyType" ...>
  Locator: By.name("FNOLWizard-PolicySearch-PolicyType")
```

### 4. Update ClaimCreationPage.java:
Replace the locators in the file with your actual findings.

## 🔧 Common Guidewire Patterns:

**Tabs/Menus:**
```java
By.id("TabBar-ClaimTab")
By.id("TabBar-ClaimTab-ClaimTab_FNOLWizard")
```

**Input Fields:**
```java
By.name("FNOLWizard-LOBWizardStepGroup-NewLossDetailsScreen_FormLeft-PolicyNumber")
By.xpath("//input[contains(@name,'PolicyNumber')]")
```

**Dropdowns:**
```java
By.name("FNOLWizard-LOBWizardStepGroup-NewLossDetailsScreen_FormLeft-LossType")
By.xpath("//select[contains(@name,'LossType')]")
```

**Buttons (Guidewire uses divs for buttons):**
```java
By.xpath("//div[contains(@id,'Search') and contains(@class,'gw-action')]")
By.xpath("//div[contains(@id,'Next')]//div[contains(@class,'gw-label')]")
```

**Dates:**
```java
By.name("FNOLWizard-LOBWizardStepGroup-NewLossDetailsScreen-LossDate")
By.xpath("//input[contains(@name,'LossDate')]")
```

## 🚀 Alternative: Use the Find Claim Feature

Your app has a "Find Claim" search. You could:
1. Use the search field: `name='TabBar-ClaimTab-ClaimTab_FindClaim'`
2. Search for an existing claim
3. Then modify it

This might be simpler than navigating the full "New Claim" wizard.

## 📝 Quick Test:

Run this to see the Claim tab options:
```bash
cd automation-framework
mvn test -Dtest=ElementIdentifierTest
```

Then check the logs for elements containing "Claim" or "FNOL".

---

**Need Help?** Share a screenshot of the "New Claim" form and I can help identify the exact locators!
