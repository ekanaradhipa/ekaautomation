package id.co.juaracoding.cucumber.web;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import id.co.juaracoding.selenium.pages.LicenseGatePage;
import id.co.juaracoding.selenium.pages.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import id.co.juaracoding.util.TestConfig;

public class RegisterSteps {

    private WebDriver driver;

     @Given("aplikasi Simple Apps sudah menyala di halaman register")
    public void aplikasiSudahMenyalaDiHalamanRegister() {
        this.driver = WebBaseSteps.getDriver();
        driver.get(TestConfig.BASE_URL + "/register");
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(TestConfig.LICENSE_KEY);
            driver.get(TestConfig.BASE_URL + "/register");
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/register"));
    }

    
    @When("saya isi form register dengan data valid")
    public void sayaIsiFormRegisterDenganDataValid() {
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
    }

    @When("saya isi form register dengan data invalid")
    public void sayaIsiFormRegisterDenganDataInvalid() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillUsername(TestConfig.NEW_USER_USERNAME);
        registerPage.fillPassword(TestConfig.NEW_USER_PASSWORD);
        registerPage.fillFullName("Customer New");
        registerPage.fillEmail("customernew@mail.com");
        registerPage.fillPhoneNumber("08123459990");
        registerPage.fillAddress("Jl. Alamat Palsu Fake");
        registerPage.fillBirthDate("1991-01-01");
        registerPage.fillGender("MALE");
       // registerPage.fillIdCard("2334567890123456");
       // registerPage.fillTaxId("2254567890123456");
        registerPage.fillPostalCode("13345");
    }
    
    @And("saya isi captcha register dengan benar")
    public void sayaIsiCaptchaRegisterDenganBenar() {
        new RegisterPage(driver).fillCaptcha();
    }
    
    @Then("tombol register harus dalam keadaan enabled")
    public void tombolRegisterHarusDalamKeadaanEnabled() {
        Assert.assertTrue(new RegisterPage(driver).isSubmitButtonClickable());
    }
    
    @Then("tombol register harus dalam keadaan disabled")
    public void tombolRegisterHarusDalamKeadaanDisabled() {
        Assert.assertFalse(new RegisterPage(driver).isSubmitButtonClickable());
    }
}
