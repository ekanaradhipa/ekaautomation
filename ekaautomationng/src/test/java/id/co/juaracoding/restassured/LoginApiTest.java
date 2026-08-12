package id.co.juaracoding.restassured;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


public class LoginApiTest extends BaseRestAssuredTest {

   JSONObject json = new JSONObject();

    @BeforeClass
    public void setup() {
        //json = new JSONObject();
        RestAssured.baseURI = BaseRestAssuredTest.BASE_URL;
    }

    @Test
    public void get_captcha() {
        Response response = given().header("X-API-KEY", BaseRestAssuredTest.X_API_KEY)
                .header("Content-Type", "application/json").when()
                .request("GET", "/api/v1/captcha").then()
                .assertThat().statusCode(200).extract().response();
        String hash = response.jsonPath().getString("data.captcha_hash");
        String value = response.jsonPath().getString("data.captcha_value");
        Assert.assertEquals(response.jsonPath().getString("status"), "SUCCESS");
        Assert.assertEquals(response.jsonPath().getString("message"), "Captcha berhasil dibuat");
        Assert.assertNotNull(hash);
        Assert.assertNotNull(response.jsonPath().getString("timestamp"));
        Assert.assertEquals(response.jsonPath().getString("path"), "/api/v1/captcha");
        Assert.assertNotNull(value);
        Assert.assertEquals(response.jsonPath().getInt("code"), 200);
    }

    @Test
    public void should_return_200_and_token_when_login_valid() {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaHelper.encrypt(TestConfig.ADMIN_PASSWORD);
        json.clear();
        Response response = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.ADMIN_USERNAME, ciphertext, captcha[1], captcha[0])).post("/api/v1/login");
        //System.out.println("Ini Login");

        //System.out.println(json.toString());
        //Response response = specDasar().body(json.toJSONString()).post("/api/v1/login");

        /* 
        response.then()
                .statusCode(200)
                .body("status", org.hamcrest.Matchers.equalTo("SUCCESS"))
                .body("message", org.hamcrest.Matchers.equalTo("Login berhasil"))
                .body("data.token", org.hamcrest.Matchers.notNullValue())
                .body("data.user.username", org.hamcrest.Matchers.equalTo(TestConfig.ADMIN_USERNAME))
                .body("data.user.full_name", org.hamcrest.Matchers.equalTo(TestConfig.ADMIN_LOGIN_NAME))
                .body("data.user.role", org.hamcrest.Matchers.equalTo("ADMIN"))
                .header("Content-Type", "application/json");
                */

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("status"), "SUCCESS");
    }

    @Test
    public void should_return_401_API_ECMXS40107_when_password_salah() {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaHelper.encrypt("PasswordSalah@1");

        Response response = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.ADMIN_USERNAME, ciphertext, captcha[1], captcha[0])).post("/api/v1/login");

       Assert.assertEquals(response.getStatusCode(), 401);
       Assert.assertEquals(response.jsonPath().getString("status"), "ERROR");
     
    }
/* 
    @Test
    public void should_return_401_API_ECMXS40107_when_captcha_salah() {
        String[] captcha = ambilCaptcha();
        String ciphertext = RsaHelper.encrypt("Admin1@123");

        Response response = specDasar().body(String.format(
                "{\"username\":\"admin1\",\"password\":\"%s\",\"captcha_answer\":\"salahterus\",\"captcha_hash\":\"%s\"}",
                ciphertext, captcha[0])).post("/api/v1/login");

        response.then()
                .statusCode(401)
                .body("message", org.hamcrest.Matchers.equalTo("Username, password, atau captcha salah"))
                .body("status", org.hamcrest.Matchers.equalTo("ERROR"))
                .body("error_code", org.hamcrest.Matchers.equalTo("API-ECMXS40107"));
    } 

    @Test
    public void should_return_401_API_ECMXS40105_when_x_api_key_kosong() {
        Response response = given()
                .contentType("application/json")
                .body("{\"username\":\"admin1\",\"password\":\"x\",\"captcha_answer\":\"x\",\"captcha_hash\":\"x\"}")
                .post(BASE_URL + "/api/v1/login");

        System.out.println(response.asPrettyString());
        response.then()
                .statusCode(401)
                .body("status", org.hamcrest.Matchers.equalTo("ERROR"))
                .body("message", org.hamcrest.Matchers.equalTo("Format lisensi tidak sesuai"))
                .body("path", org.hamcrest.Matchers.equalTo("/api/v1/login"))
                .body("timestamp", org.hamcrest.Matchers.notNullValue())
                .body("error_code", org.hamcrest.Matchers.equalTo("API-ECMXS40105"));
    }*/
}
