package id.co.juaracoding.restassured;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import id.co.juaracoding.restassured.util.RsaHelper;
import io.restassured.response.Response;


public class RegisterApiTest extends BaseRestAssuredTest {

 String username = "";
    String token = "";
    final String password = "Resta123@1";
    JSONObject json = new JSONObject();

    private String angkaUnik() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Test(priority = 0)
    public void coba() {
        Date date = new Date(1785933028536L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(date));
    }

    @Test(priority = 10)
    public void should_return_200_and_magic_link_when_register_valid() {

        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();
        // 1785933028536
        username = "resta" + unik.substring(unik.length() - 8);
        String emailPlain = "resta" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpPlain = "300" + unik;
        String npwpPlain = "310" + unik;
        String birthDatePlain = "2000-05-10";
        System.out.println("username --> " + username);
        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt(password) + "\","
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

        Response response = specDasar().body(body).post("/api/v1/register");
        String magicLink = response.jsonPath().getString("data.magic_link");
        token = magicLink.replace("http://localhost:8080/activate?token=", "");
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.magic_link", notNullValue());
    }

    @Test(priority = 20)
    public void try_login_without_regis_verification() {
        if (token.equals("")) {
            Assert.assertEquals(1, 2, "try_login_without_regis_verification --> Error : Token Kosong");
        }
        String[] captcha = ambilCaptcha();
        Response response = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                username, RsaHelper.encrypt(password), captcha[1], captcha[0])).post("/api/v1/login");
        // System.out.println("Body : " + response.asPrettyString());
        response.then()
                .statusCode(401)
                .body("status", equalTo("ERROR"))
                .body("error_code", equalTo("API-ECMXS40104"))
                .body("message", equalTo("Akun belum diaktivasi, silakan cek email aktivasi"));
    }

    @Test(priority = 30)
    public void try_verification_magic_link() {
        if (token.equals("")) {
            Assert.assertEquals(1, 2, "try_verification_magic_link --> Error : Token Kosong");
        }
        Response response = specDasar().body(String.format(
                "{\"token\":\"%s\"}",
                token)).post("/api/v1/activate");
        System.out.println("Body : " + response.asPrettyString());
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("timestamp", notNullValue())
                .body("message", equalTo("Akun berhasil diaktivasi, silakan login"));
    }

    @Test(priority = 40)
    public void try_login_after_regis_verification() {
        if (token.equals("")) {
            Assert.assertEquals(1, 2, "try_login_after_regis_verification --> Error : Token Kosong");
        }
        String[] captcha = ambilCaptcha();
        Response response = specDasar().body(String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                username, RsaHelper.encrypt(password), captcha[1], captcha[0])).post("/api/v1/login");
        System.out.println("Body : " + response.asPrettyString());
        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("message", equalTo("Login berhasil"));
    }

    @Test
    public void should_return_400_errors_when_id_card_number_bukan_16_digit() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();

        String username = "restb" + unik.substring(unik.length() - 8);
        String emailPlain = "restb" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpTidakSah = RsaHelper.encrypt("123456789012345"); // 15 digit — SENGAJA salah
        String npwpPlain = "320" + unik;
        String birthDatePlain = "2000-05-10";

        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Resta123@1") + "\","
                + "\"full_name\":\"Peserta RestAssured\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jalan Merdeka No 10\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + ktpTidakSah + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"40123\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar().body(body).post("/api/v1/register");
        System.out.println("Response : " + response.asPrettyString());
        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("error_code", equalTo("API-ECM010001")) // kode PAYUNG top-level (REGISTER_UMBRELLA)
                .body("errors.field", hasItem("id_card_number"))
                .body("errors.find { it.field == 'id_card_number' }.error_code", equalTo("API-ECM010009"));
    }

    @Test
    public void should_return_400_errors_when_tax_id_number_bukan_16_digit() {
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();

        String username = "restb" + unik.substring(unik.length() - 8);
        String emailPlain = "restb" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpTidakSah = RsaHelper.encrypt("123456789012345"); // 15 digit — SENGAJA salah
        String npwpPlain = "320" + unik;
        String birthDatePlain = "2000-05-10";

        String body = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt("Resta123@1") + "\","
                + "\"full_name\":\"Peserta RestAssured\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"Jalan Merdeka No 10\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"tax_id_number\":\"" + ktpTidakSah + "\","
                + "\"postal_code\":\"40123\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar().body(body).post("/api/v1/register");
        System.out.println("Response : " + response.asPrettyString());
        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("error_code", equalTo("API-ECM010001")) // kode PAYUNG top-level (REGISTER_UMBRELLA)
                .body("errors.field", hasItem("tax_id_number"))
                .body("errors.find { it.field == 'tax_id_number' }.error_code", equalTo("API-ECM010010"));
    }

    @Test
    public void should_return_400_errors_when_mail_format_invalid() {
        setJsonRegis();
        json.put("email", "invalid_email");
        Response response = specDasar().body(json.toJSONString()).post("/api/v1/register");
        System.out.println("Response : " + response.asPrettyString());
        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("error_code", equalTo("API-ECMXR40003")) // kode PAYUNG top-level (REGISTER_UMBRELLA)
                .body("message", equalTo("Data terenkripsi tidak dapat dibaca"));
    }

    @Test
    public void should_return_400_errors_when_tax_id_number_not_encrypted() {
        setJsonRegis();
        json.put("tax_id_number", "01928309182030912830");
        Response response = specDasar().body(json.toJSONString()).post("/api/v1/register");
        System.out.println("Response : " + response.asPrettyString());
        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("error_code", equalTo("API-ECMXR40003")) // kode PAYUNG top-level (REGISTER_UMBRELLA)
                .body("message", equalTo("Data terenkripsi tidak dapat dibaca"));
    }

    private void setJsonRegis() {
        json.clear();
        String unik = angkaUnik();
        String[] captcha = ambilCaptcha();

        String username = "restb" + unik.substring(unik.length() - 8);
        String emailPlain = "restb" + unik + "@example.com";
        String phonePlain = "08" + unik.substring(unik.length() - 9);
        String ktpTidakSah = RsaHelper.encrypt("123456789012345"); // 15 digit — SENGAJA salah
        String npwpPlain = "320" + unik;
        String birthDatePlain = "2000-05-10";

        json.put("username", username);
        json.put("password", RsaHelper.encrypt(password));
        json.put("full_name", "Peserta RestAssured");
        json.put("email", RsaHelper.encrypt(emailPlain));
        json.put("phone_number", RsaHelper.encrypt(phonePlain));
        json.put("address", "Jalan Merdeka No 10");
        json.put("birth_date", RsaHelper.encrypt(birthDatePlain));
        json.put("gender", "OTHER");
        json.put("last_education", "S1");
        json.put("blood_type", "O");
        json.put("id_card_number", RsaHelper.encrypt(npwpPlain));
        json.put("tax_id_number", RsaHelper.encrypt(npwpPlain));
        json.put("postal_code", "40123");
        json.put("captcha_answer", captcha[1]);
        json.put("captcha_hash", captcha[0]);
    }

    
}
