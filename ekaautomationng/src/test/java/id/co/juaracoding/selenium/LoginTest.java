package id.co.juaracoding.selenium;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LoginPage;

public class LoginTest extends BaseSeleniumTest {


    @Test(priority = 0)
    public void should_redirect_to_dashboard_when_login_valid_admin() {
        bukaHalamanLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("admin1", "Admin1@123");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains("Admin Satu"),
                "Nama user yang login harus tampil di navbar");
    }

    @Test(priority = 0)
    public void should_redirect_to_dashboard_when_login_valid_customer() {
        bukaHalamanLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("ekanaradhipa6", "Password123#");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains("Ekanaradhipa Djacaria Enam"), "Nama user yang login harus tampil di navbar");
    }

    @Test(priority = 1)
    public void should_show_error_toast_when_password_and_captcha_invalid() {
        bukaHalamanLogin();
        BaseSeleniumTest.delay();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUsername("admin1");
        loginPage.fillPassword("SalahPassword123");
        loginPage.fillCaptcha("salahcaptcha");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorToastShown(),
                "Harus menampilkan toast error ketika password dan captcha salah");
    }

    @Test(priority = 2)
    public void should_show_error_toast_when_captcha_invalid() {
        bukaHalamanLogin();
        BaseSeleniumTest.delay();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUsername("admin1");
        loginPage.fillPassword("Admin1@123");
        loginPage.fillCaptcha("salahcaptcha");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorToastShown(),
                "Harus menampilkan toast error ketika captcha salah");
    }
    
}
