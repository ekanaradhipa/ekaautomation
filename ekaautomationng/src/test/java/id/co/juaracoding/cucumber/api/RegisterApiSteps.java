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

public class RegisterApiSteps extends BaseRestAssuredTest {


    private String[] captcha;
    private String magicLink = "";
    private String token = "";
    private Response response;

     private String angkaUnik() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    @When("saya isi form register dengan data valid")
    public void saya_isi_form_register_dengan_data_valid() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();
        String username = "userbaru" + unik.substring(unik.length() - 8);
        String emailPlain = "userbaru" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpPlain = "300" + unik;
        String npwpPlain = "310" + unik;
        String birthDatePlain = "2000-05-10";
        System.out.println("username --> " + username);
        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Password123#") + "\","
                + "\"full_name\":\"Peserta RestAssured\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jalan Merdeka No 10\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"OTHER\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(ktpPlain) + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"40123\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        this.response = specDasar().body(body).post("/api/v1/register");
         this.magicLink = this.response.jsonPath().getString("data.magic_link");
    }

    @When("saya isi captcha register dengan benar")
    public void saya_isi_captcha_register_dengan_benar() {
        // Implementasi untuk mengisi captcha
    }

    @Then("response valid")
    public void response_valid() {
        // Implementasi untuk memverifikasi response valid
        Assert.assertNotNull(magicLink, "Magic link tidak boleh null");
        Assert.assertFalse(magicLink.isEmpty(), "Magic link tidak boleh kosong");
    }

    @When("saya isi form register dengan data invalid")
    public void saya_isi_form_register_dengan_data_invalid() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();
        String username = "userbaru" + unik.substring(unik.length() - 8);
        String emailPlain = "userbaru" ; //email salah
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpPlain = "300" + unik;
        String npwpPlain = "310" + unik;
        String birthDatePlain = "2000-05-10";
        System.out.println("username --> " + username);
        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Password123#") + "\","
                + "\"full_name\":\"Peserta RestAssured\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jalan Merdeka No 10\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"OTHER\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(ktpPlain) + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"40123\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        this.response = specDasar().body(body).post("/api/v1/register");
         this.magicLink = this.response.jsonPath().getString("data.magic_link");
    }

    @Then("response invalid")
    public void response_invalid() {
        // Implementasi untuk memverifikasi response invalid
        Assert.assertNull(magicLink, "Magic link harus null untuk data register yang invalid");
    }

}
