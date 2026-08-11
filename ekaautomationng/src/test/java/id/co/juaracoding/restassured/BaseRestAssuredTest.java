package id.co.juaracoding.restassured;

import org.testng.annotations.BeforeClass;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class BaseRestAssuredTest {

    public static final String BASE_URL = TestConfig.BASE_URL;
    public static final String X_API_KEY = TestConfig.LICENSE_KEY;

    @BeforeClass
    public void setUpBaseUri() {
        RestAssured.baseURI = BASE_URL;
    }

    protected RequestSpecification specDasar() {
        return given()
                .header("X-API-KEY", X_API_KEY)
                .contentType("application/json");
    }

    protected RequestSpecification specDenganToken(String token) {
        return specDasar().header("Authorization", "Bearer " + token);
    }

    protected String[] ambilCaptcha() {
        Response response = specDasar().get("/api/v1/captcha");
        String hash = response.jsonPath().getString("data.captcha_hash");
        String value = response.jsonPath().getString("data.captcha_value");
        return new String[] { hash, value };
    }

    protected String loginDapatkanToken(String username, String plainPassword) {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaHelper.encrypt(plainPassword);

        Response response = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                username, ciphertext, captcha[1], captcha[0])).post("/api/v1/login");

        String token = response.jsonPath().getString("data.token");
        if (token == null) {
            throw new IllegalStateException("Login gagal untuk " + username + " — response: " + response.asString());
        }
        return token;
    }
    
}

