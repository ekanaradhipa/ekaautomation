package id.co.juaracoding.cucumber.web;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends WebBaseSteps {
    
    @When("saya login sebagai {string} dengan password {string}")
    public void sayaLoginSebagaiDenganPassword(String username, String password) {
        new LoginPage(driver).loginAs(username, password);
        System.out.println("LoginSteps.sayaLoginSebagaiDenganPassword() - username: " + username + ", password: " + password);
    }
    
    @When("saya isi form login dengan username {string} dan password {string} tanpa klik submit")
    public void sayaIsiFormLoginTanpaKlikSubmit(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillUsername(username);
        loginPage.fillPassword(password);
        loginPage.fillCaptcha(loginPage.readCaptchaValue());
    }
    
    @Then("tombol login harus dalam keadaan disabled")
    public void tombolLoginHarusDalamKeadaanDisabled() {
        Assert.assertFalse(new LoginPage(driver).isLoginButtonEnabled());
    }
    
    @Then("browser harus pindah ke halaman dashboard")
    public void browserHarusPindahKeHalamanDashboard() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
    }
    
    @And("nama user yang login mengandung {string}")
    public void namaUserYangLoginMengandung(String namaUser) {
        Assert.assertTrue(new DashboardPage(driver).getLoggedInUserName().contains(namaUser));
    }
    
    @Then("toast error harus muncul di halaman login")
    public void toastErrorHarusMuncul() {
        Assert.assertTrue(new LoginPage(driver).isErrorToastShown());
    }
    
    @And("browser tetap berada di halaman login")
    public void browserTetapBeradaDiHalamanLogin() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
