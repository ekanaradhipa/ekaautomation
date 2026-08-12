package id.co.juaracoding.restassured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;

public class ForgetPasswordApiTest extends BaseRestAssuredTest {

    String magicLink = "";
    String token = "";

    @Test(priority = 0)
    public void should_return_200_and_magic_link_when_email_terdaftar() {
        String[] captcha = ambilCaptcha();

        Response response = specDasar().body(String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                 TestConfig.CUSTOMER_EMAIL,captcha[1], captcha[0])).post("/api/v1/forgot-password");
        magicLink = response.jsonPath().getString("data.magic_link");
        System.out.println("MAGIC LINK " + magicLink);
        token = magicLink.replace("http://localhost:8080/reset-password?token=", "");
        System.out.println("TOKEN " + token);
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.magic_link", notNullValue());
    }

    @Test(priority = 5)
    public void ganti_password() {
        if (token.equals("")) {
            Assert.assertEquals(1, 2);
        }
        //String[] captcha = ambilCaptcha();
        String password = TestConfig.CUSTOMER_PASSWORD;
        Response response = specDasar().body(String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"confirm_password\":\"%s\",\"token\":\"%s\"}",
                TestConfig.CUSTOMER_EMAIL, password, password, token)).post("/api/v1/reset-password");
        //System.out.println("Body : " + response.asPrettyString());
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("message", equalTo("Kata sandi berhasil diubah, silakan login"));
    }

    @Test(priority = 10) // 0001Login.feature , 0005ForgotPassword.feature
    public void should_return_200_juga_when_email_tidak_terdaftar() {
        String[] captcha = ambilCaptcha();

        Response response = specDasar().body(String.format(
                "{\"email\":\"ekanaradi@mail.com\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                captcha[1],
                captcha[0])).post("/api/v1/forgot-password");
      
        //System.out.println("Response : " + response.asPrettyString());
                response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.magic_link", nullValue());
    }
    
}
