package id.co.juaracoding.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.cssSelector("[data-testid='login-username-input']");
    private final By passwordInput = By.cssSelector("[data-testid='login-password-input'] input");
    private final By captchaValueHint = By.cssSelector("[data-testid='login-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='login-captcha-input']");
    private final By loginButton = By.cssSelector("[data-testid='login-submit-button']");
    private final By errorToastIcon = By.cssSelector("[data-testid='toast-icon-error']");
    private final By loginLabel = By.tagName("h1");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String readCaptchaValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint)).getText();
    }

    public void fillUsername(String username) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(username);
    }

    public void fillPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(password);
    }

    public void fillCaptcha(String captcha) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaInput));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(captcha);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void loginAs(String username, String password) {
        id.co.juaracoding.selenium.BaseSeleniumTest.delay(); // wait for Vue hydration
        fillUsername(username);
        fillPassword(password);
        fillCaptcha(readCaptchaValue());
        clickLogin();
    }

    public boolean isErrorToastShown() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastIcon)) != null;
    }
}