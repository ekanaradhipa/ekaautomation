package id.co.juaracoding.cucumber.web;


import org.testng.Assert;

import id.co.juaracoding.selenium.pages.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterSteps extends WebBaseSteps {
    
    @When("saya isi form register dengan data valid")
    public void sayaIsiFormRegisterDenganDataValid() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillAllForm(
            "testuser01",
            "TestPassword123!",
            "Test User",
            "testuser01@example.com",
            "081234567890",
            "Jalan Test No. 123",
            "1990-01-15",
            "MALE"
        );
        registerPage.fillIdCard("1234567890123456");
        registerPage.fillTaxId("12.345.678.9-012.345");
        registerPage.fillPostalCode("12345");
    }
    
    @And("saya isi captcha dengan benar")
    public void sayaIsiCaptchaDenanBenar() {
        new RegisterPage(driver).fillCaptcha();
    }
    
    @Then("tombol register harus dalam keadaan enabled")
    public void tombolRegisterHarusDalamKeadaanEnabled() {
        Assert.assertTrue(new RegisterPage(driver).isSubmitButtonClickable());
    }
}
