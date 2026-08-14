package id.co.juaracoding.cucumber.web;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ForgotPasswordSteps {

    protected WebDriver driver;

    @When("saya isi form forgot password dengan email valid")
    public void sayaIsiFormForgotPasswordDenganEmailValid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.fillEmail("valid@example.com");
        
        // Implementation for filling the forgot password form with a valid email
    }

    @When("saya isi form forgot password dengan email invalid")
    public void sayaIsiFormForgotPasswordDenganEmailInvalid() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.fillEmail("valid@example.com");
        
        // Implementation for filling the forgot password form with a valid email
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
