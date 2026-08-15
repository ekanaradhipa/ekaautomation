package id.co.juaracoding.cucumber.api;

import org.apiguardian.api.API;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.testng.Assert;

import id.co.juaracoding.restassured.BaseRestAssuredTest;
import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;

public class ForgotApiSteps extends BaseRestAssuredTest {

    //private Response lastResponse;
    private String[] captcha;
    private String magicLink = "";
    private String token = "";

    
    @When ("saya kirim forgot password API dengan email {string}")
    public void kirimForgotPasswordAPIValid(String email) {

         captcha = ambilCaptcha();

        Response response = specDasar().body(String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                 TestConfig.CUSTOMER_EMAIL,captcha[1], captcha[0])).post("/api/v1/forgot-password");
        magicLink = response.jsonPath().getString("data.magic_link");
        System.out.println("email:" + email);
        System.out.println("MAGIC LINK " + magicLink);
        if(magicLink != null){
        token = magicLink.replace("http://localhost:8080/reset-password?token=", "");
    }
        System.out.println("TOKEN " + token);

    }
    @Then ("response API forgot password memiliki magic link")
    public void verifyMagicLink() {
        Assert.assertNotNull(magicLink, "Magic link tidak boleh null");
        Assert.assertFalse(magicLink.isEmpty(), "Magic link tidak boleh kosong");
        Assert.assertNotNull(token, "Token tidak boleh null");
        Assert.assertFalse(token.isEmpty(), "Token tidak boleh kosong");
    }

    
    @Then ("response API forgot password tidak memiliki magic link")
    public void verifyMagicLinkInvalid() {
        Assert.assertNull(magicLink, "Magic link harus null untuk email yang tidak terdaftar");
        // Implementasi verifikasi magic link dalam response API
    }

}
