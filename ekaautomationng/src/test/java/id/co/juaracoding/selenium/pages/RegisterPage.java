package id.co.juaracoding.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import id.co.juaracoding.util.MethodPage;


public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MethodPage methodPage;

    private final By usernameInput = By.cssSelector("[data-testid='register-username-input']");
    private final By passwordInput = By.cssSelector("[data-testid='register-password-input'] input");
    private final By fullNameInput = By.cssSelector("[data-testid='register-full-name-input']");
    private final By emailInput = By.cssSelector("[data-testid='register-email-input']");
    private final By phoneNumberInput = By.cssSelector("[data-testid='register-phone-number-input']");
    private final By addressInput = By.cssSelector("[data-testid='register-address-input']");
    private final By birthDateInput = By.cssSelector("[data-testid='register-birth-date-input']");
    private final By genderInput = By.cssSelector("[data-testid='register-gender-input']");
    private final By submitButton = By.cssSelector("[data-testid='register-submit-button']");
    private final By magicLink = By.cssSelector("[data-testid='check-email-magic-link']");

    private final By idCardInput = By.cssSelector("[data-testid='register-id-card-number-input']");
    private final By taxIdInput = By.cssSelector("[data-testid='register-tax-id-number-input']");
    private final By postalCodeInput = By.cssSelector("[data-testid='register-postal-code-input']");
    private final By captchaValueHint = By.cssSelector("[data-testid='register-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='register-captcha-input']");
    
  

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.methodPage = new MethodPage();
    }

    public void fillUsername(String username) {
        methodPage.typeText(usernameInput, username, wait);
    }

    public void fillPassword(String password) {
        methodPage.typeText(passwordInput, password, wait);
    }

    public void fillFullName(String fullName) {
        methodPage.typeText(fullNameInput, fullName, wait);
    }

    public void fillEmail(String email) {
        methodPage.typeText(emailInput, email, wait);
    }

    public void fillPhoneNumber(String phoneNumber) {
        methodPage.typeText(phoneNumberInput, phoneNumber, wait);
    }

    public void fillAddress(String address) {
        methodPage.typeText(addressInput, address, wait);
    }

    public void fillBirthDate(String birthDate) {
        WebElement el = findInputField(birthDateInput);
        wait.until(ExpectedConditions.visibilityOf(el));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(birthDate);
        el.sendKeys(Keys.ENTER);
        el.sendKeys(Keys.TAB);
    }

    private WebElement findInputField(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        if (el.getTagName().equalsIgnoreCase("input") || el.getTagName().equalsIgnoreCase("textarea")) {
            return el;
        }
        try {
            return el.findElement(By.tagName("input"));
        } catch (Exception e) {
            return el;
        }
    }

    public void fillIdCard(String idCard) {
        methodPage.typeText(idCardInput, idCard, wait);
    }
    public void fillTaxId(String taxId) {
        methodPage.typeText(taxIdInput, taxId, wait);
    }
    public void fillPostalCode(String postalCode) {
        methodPage.typeText(postalCodeInput, postalCode, wait);
    }
    

    public void fillGender(String gender) {
        if (gender == null || gender.isEmpty()) return;
        String optionLabel = gender;
        if ("MALE".equalsIgnoreCase(gender)) optionLabel = "Pria";
        else if ("FEMALE".equalsIgnoreCase(gender)) optionLabel = "Wanita";
        else if ("OTHER".equalsIgnoreCase(gender)) optionLabel = "Lainnya";

        WebElement selectEl = wait.until(ExpectedConditions.elementToBeClickable(genderInput));
        selectEl.click();
        By optionLocator = By.xpath("//*[contains(@class, 'p-select-option') and contains(., '" + optionLabel + "')] | //li[contains(., '" + optionLabel + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

     public String readCaptchaValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint)).getText();
    }


    public void fillCaptcha() {
        String captcha = readCaptchaValue();
        methodPage.typeText(captchaInput, captcha, wait);
    }

    public void fillAllForm(String username, String password, String fullName, String email, String phoneNumber, String address, String birthDate, String gender) {
        fillUsername(username);
        fillPassword(password);
        fillFullName(fullName);
        fillEmail(email);  
        fillPhoneNumber(phoneNumber);
        fillAddress(address);
        fillBirthDate(birthDate);
        fillGender(gender);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isSubmitButtonClickable() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            return btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickMagicLink() {
        wait.until(ExpectedConditions.elementToBeClickable(magicLink)).click();
    }

    public boolean isMagicLinkShown() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(magicLink));
        return link.isDisplayed();
    }

}