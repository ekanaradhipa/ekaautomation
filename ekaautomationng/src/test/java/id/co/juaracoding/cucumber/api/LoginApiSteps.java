package id.co.juaracoding.cucumber.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import id.co.juaracoding.restassured.BaseRestAssuredTest;
import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;

public class LoginApiSteps extends BaseRestAssuredTest {
    
    private String[] captchaData;
    private Response lastResponse;

    @Given("saya sudah mengambil captcha dari API")
    public void ambilCaptchaAwal() {
        captchaData = ambilCaptcha();
        Assert.assertNotNull(captchaData, "Captcha hash tidak boleh null");
        Assert.assertNotNull(captchaData[1], "Captcha value tidak boleh null");
    }

    @When("saya kirim login API dengan username {string} dan password {string}")
    public void kirimLoginAPI(String username, String password) {
        String ciphertext = RsaHelper.encrypt(password);
        
        lastResponse = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                username, ciphertext, captchaData[1], captchaData[0])).post("/api/v1/login");
    }

    @Then("response API login memiliki status code {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(lastResponse.getStatusCode(), expectedStatusCode,
                "Status code tidak sesuai. Response: " + lastResponse.asString());
    }

    @Then("response API login memiliki token yang tidak kosong")
    public void verifyTokenNotEmpty() {
        String token = lastResponse.jsonPath().getString("data.token");
        Assert.assertNotNull(token, "Token tidak boleh null");
        Assert.assertFalse(token.isEmpty(), "Token tidak boleh kosong");
    }

    @Then("response API login memiliki error_code {string}")
    public void verifyErrorCode(String expectedErrorCode) {
        String actualErrorCode = lastResponse.jsonPath().getString("data.error_code");
        Assert.assertEquals(actualErrorCode, expectedErrorCode,
                "Error code tidak sesuai. Response: " + lastResponse.asString());
    }
}
