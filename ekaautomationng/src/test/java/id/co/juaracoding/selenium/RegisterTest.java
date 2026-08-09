package id.co.juaracoding.selenium;

import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;

public class RegisterTest extends BaseSeleniumTest {

    //@Test(priority = 0)
    public void open_register_page() {
        BukaHalamanRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Setelah membuka halaman register, URL harus mengandung /register. URL aktual: " + driver.getCurrentUrl());
    }

    @Test(priority = 0)
    public void fill_register_form() {
        BukaHalamanRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillUsername(TestConfig.NEW_USER_USERNAME);
        registerPage.fillPassword(TestConfig.NEW_USER_PASSWORD);
        registerPage.fillFullName("Customer New");
        registerPage.fillEmail("customernew@mail.com");
        registerPage.fillPhoneNumber("08123459990");
        registerPage.fillAddress("Jl. Alamat Palsu Fake");
        registerPage.fillBirthDate("1991-01-01");
        registerPage.fillGender("MALE");
        registerPage.fillIdCard("2334567890123456");
        registerPage.fillTaxId("2254567890123456");
        registerPage.fillPostalCode("13345");
        registerPage.fillCaptcha();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Setelah membuka halaman register, URL harus mengandung /register. URL aktual: " + driver.getCurrentUrl());
        Assert.assertTrue(registerPage.isSubmitButtonClickable(), "Tombol submit bisa diklik");
        registerPage.clickSubmit();
        Assert.assertTrue(registerPage.isMagicLinkShown(), "Magic link muncul setelah submit form register");
    }

    @Test(priority = 0)
    public void fill_register_form_wrong_nik() {
        BukaHalamanRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillUsername(TestConfig.NEW_USER_USERNAME);
        registerPage.fillPassword(TestConfig.NEW_USER_PASSWORD);
        registerPage.fillFullName("Customer New");
        registerPage.fillEmail("customernew@mail.com");
        registerPage.fillPhoneNumber("08123459990");
        registerPage.fillAddress("Jl. Alamat Palsu Fake");
        registerPage.fillBirthDate("1991-01-01");
        registerPage.fillGender("MALE");
        registerPage.fillIdCard("23345678901234");
        registerPage.fillTaxId("22545678901234");
        registerPage.fillPostalCode("13345");
        registerPage.fillCaptcha();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "stay di halaman");
        Assert.assertFalse(registerPage.isSubmitButtonClickable(), "Tombol submit harus tidak bisa diklik karena NIK salah.");
    }


        
    
}
