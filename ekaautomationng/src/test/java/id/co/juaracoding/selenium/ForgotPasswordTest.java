package id.co.juaracoding.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.util.TestConfig;

public class ForgotPasswordTest extends BaseSeleniumTest {

    @Test(priority = 0)
    public void open_forgot_password_page() {
        bukaHalaman("/forgot-password");
        Assert.assertTrue(driver.getCurrentUrl().contains("/forgot-password"), "Setelah membuka halaman forgot password, URL harus mengandung /forgot-password. URL aktual: " + driver.getCurrentUrl());
    }

    @Test(priority = 1)
    public void submit_forgot_password_form_with_valid_email() {
        bukaHalaman("/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
            forgotPasswordPage.fillEmail(TestConfig.CUSTOMER_EMAIL);
            forgotPasswordPage.fillCaptcha();
            forgotPasswordPage.clickSubmit();
            forgotPasswordPage.clickMagicLink();
        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Password Baru"), "Masuk Page Forgot Password");

    }

    @Test(priority = 2)
    public void submit_forgot_password_form_with_invalid_email() {
        bukaHalaman("/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
            forgotPasswordPage.fillEmail("notexist@gmail.com");
            forgotPasswordPage.fillCaptcha();
            forgotPasswordPage.clickSubmit();
            //forgotPasswordPage.isMagicLinkShown();

        Assert.assertFalse(forgotPasswordPage.isMagicLinkShown(), "Magic link gaada");
        Assert.assertFalse(driver.findElement(By.tagName("h1")).getText().contains("Password Baru"), "Masuk Page Forgot Password");

    }
        
}
