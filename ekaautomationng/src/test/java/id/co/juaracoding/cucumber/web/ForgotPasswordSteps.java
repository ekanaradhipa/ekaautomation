package id.co.juaracoding.cucumber.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.selenium.pages.LicenseGatePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import id.co.juaracoding.util.TestConfig;

public class ForgotPasswordSteps {

    private WebDriver driver;

     @Given("aplikasi Simple Apps sudah menyala di halaman forgot password")
    public void aplikasiSudahMenyalaDiHalamanForgot() {
         this.driver = WebBaseSteps.getDriver();
        driver.get(TestConfig.BASE_URL + "/forgot-password");
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(TestConfig.LICENSE_KEY);
            driver.get(TestConfig.BASE_URL + "/forgot-password");
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/forgot-password"));
    }

    @When("saya isi form forgot password dengan email valid")
    public void sayaIsiFormForgotPasswordDenganEmailValid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.fillEmail(TestConfig.CUSTOMER_EMAIL);
        
    }

    @When("saya isi form forgot password dengan email invalid")
    public void sayaIsiFormForgotPasswordDenganEmailInvalid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.fillEmail("invalid@example.com");
        
       
    }

    @And ("saya isi captcha dengan benar")
    public void sayaIsiCaptchaDenganBenar() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.fillCaptcha();
    }

    @And ("saya klik tombol reset password")
    public void sayaKlikTombolResetPassword() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickSubmit();
    }
      
    @Then ("magic link valid")
    public void magicLinkValid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        Assert.assertTrue(forgotPasswordPage.isMagicLinkShown(), "Magic link is not valid");
        // Implementation for checking if the magic link is valid

    }

     @Then ("magic link invalid")
    public void magicLinkInvalid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        Assert.assertFalse(forgotPasswordPage.isMagicLinkShown(), "Magic link is valid");
        // Implementation for checking if the magic link is invalid

    }

}
