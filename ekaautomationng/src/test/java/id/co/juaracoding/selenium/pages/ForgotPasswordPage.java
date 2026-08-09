package id.co.juaracoding.selenium.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.xpath(
            "//input[@data-testid='forgot-email-input'] | //input[@id='forgot-email'] | " +
                    "//label[contains(.,'Email')]/following::input[1]");
    private final By captchaValueHint = By.cssSelector("[data-testid='forgot-password-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='forgot-password-captcha-input']");

    private final By submitButtonXpath = By.xpath(
            "//button[.//span[normalize-space()='Kirim']] | //button[normalize-space()='Kirim'] | " +
                    "//button[@data-testid='forgot-submit-button']");

    private final By magicLinkXpath = By.xpath(
            "//*[@data-testid='check-email-magic-link'] | " +
                    "//a[contains(@class,'magic-link')] | " +
                    "//a[contains(.,'Buka link') or contains(.,'Mode Testing') or contains(.,'magic')]");

   
    private final By errorToastIcon = By.cssSelector("[data-testid='toast-icon-error']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void typeText(By locator, String value) {
        if (value == null)
            return;
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(value);
    }

    public void fillCaptcha() {
        String captcha = readCaptchaValue();
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaInput));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(captcha);
    }

    public String readCaptchaValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint)).getText();
    }

    public void fillEmail(String email) {
        typeText(emailInput, email);
    }

    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButtonXpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();
    }

    public void clickMagicLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(magicLinkXpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        link.click();
    }

    public boolean isErrorToastShown() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastIcon)) != null;
    }

    public boolean isMagicLinkShown() {
        List<WebElement> links = driver.findElements(magicLinkXpath);
        return !links.isEmpty() && links.get(0).isDisplayed();
    }

    }