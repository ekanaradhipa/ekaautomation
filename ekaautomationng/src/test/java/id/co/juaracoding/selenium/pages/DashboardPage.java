package id.co.juaracoding.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By heading = By.tagName("h1");
    private final By navbarUserInfo = By.cssSelector("[data-testid='navbar-user-info']");
    private final By sidebar = By.cssSelector("[data-testid='app-sidebar']");
    private final By logout = By.cssSelector("[data-testid='navbar-logout-button']");
    private final By saldo = By.xpath("//*[@id=\"app\"]/div/div/main/div/div[1]/span[2]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }

    public String getSaldo() {
        return driver.findElement(saldo).getText();
    }

    public String getLoggedInUserName() {
        return driver.findElement(navbarUserInfo).getText();
    }

    public boolean isSidebarVisible() {
        return driver.findElement(sidebar).isDisplayed();
    }

    public void clickLogout() {
        org.openqa.selenium.WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(logout));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
}
