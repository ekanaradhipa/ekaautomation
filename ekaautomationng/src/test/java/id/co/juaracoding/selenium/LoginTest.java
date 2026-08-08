package id.co.juaracoding.selenium;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;

public class LoginTest extends BaseSeleniumTest {


    @Test(priority = 0)
    public void should_redirect_to_dashboard_when_login_valid_admin() {
        bukaHalamanLogin();
        String usernameAdmin = TestConfig.ADMIN_USERNAME;
        String passwordAdmin = TestConfig.ADMIN_PASSWORD;
        String expectedAdminName = TestConfig.ADMIN_LOGIN_NAME;
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(usernameAdmin, passwordAdmin);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains(expectedAdminName),
                "Nama user yang login harus tampil di navbar");
    }

    @Test(priority = 0)
    public void should_redirect_to_dashboard_when_login_valid_customer() {
        bukaHalamanLogin();
        String usernameCustomer = TestConfig.CUSTOMER_USERNAME;
        String passwordCustomer = TestConfig.CUSTOMER_PASSWORD;
        String expectedUserName = TestConfig.CUSTOMER_LOGIN_NAME;
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(usernameCustomer, passwordCustomer);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains(expectedUserName), "Nama user yang login harus tampil di navbar");
    }

    @Test(priority = 1)
    public void should_show_error_toast_when_password_and_captcha_invalid() {
        String usernameCustomer = TestConfig.CUSTOMER_USERNAME;
        bukaHalamanLogin();
        BaseSeleniumTest.delay();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUsername(usernameCustomer);
        loginPage.fillPassword("SalahPassword123");
        loginPage.fillCaptcha("salahcaptcha");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorToastShown(),
                "Harus menampilkan toast error ketika password dan captcha salah");
    }

    @Test(priority = 2)
    public void should_show_error_toast_when_captcha_invalid() {
        String usernameCustomer = TestConfig.CUSTOMER_USERNAME;
        String passwordCustomer = TestConfig.CUSTOMER_PASSWORD;
        bukaHalamanLogin();
        BaseSeleniumTest.delay();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUsername(usernameCustomer);
        loginPage.fillPassword(passwordCustomer);
        loginPage.fillCaptcha("salahcaptcha");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorToastShown(),
                "Harus menampilkan toast error ketika captcha salah");
    }
    
}
