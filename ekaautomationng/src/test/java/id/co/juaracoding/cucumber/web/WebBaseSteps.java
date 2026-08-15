package id.co.juaracoding.cucumber.web;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import id.co.juaracoding.selenium.pages.LicenseGatePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class WebBaseSteps {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";

    protected static WebDriver driver;

    @Before
    public void bukaBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900");
        options.addArguments("--disable-popup-blocking"); // Mematikan pemblokir popup bawaan Chrome
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @After
    public void tutupBrowser() {
        if (driver != null)
            driver.quit();
    }


    @Given("Saya berada di halaman lupa password Web Simple Apps")
    public void sayaBeradaDiHalamanLupaPasswordWebSimpleApps() {
        driver.get(BASE_URL + "/forgot-password");
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(LICENSE_KEY);
            driver.get(BASE_URL + "/forgot-password"); // gerbang Lisensi selalu mendarat di /login (setup §8.2)
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/forgot-password"));
    }

   public static WebDriver getDriver() {
        return driver;
    }
    
}
