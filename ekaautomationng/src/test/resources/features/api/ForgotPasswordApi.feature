Feature: Forgot Password API
  Sebagai automation engineer, saya ingin menguji endpoint POST /api/v1/forgot-password langsung lewat REST API.
  Membungkus id.co.juaracoding.restassured.ForgotPasswordApiTest (pertemuan 3, Unit 4) dalam bentuk Gherkin.

  Background:
    Given saya sudah mengambil captcha dari API

  @smoke
  Scenario: Forgot Password API berhasil
    When saya kirim forgot password API dengan email "admin1@simpleapps.test"
    Then response API forgot password memiliki magic link

   @negatif
   Scenario: Forgot Password API gagal karena email tidak terdaftar
    When saya kirim forgot password API dengan email "nonexistent@example.com"
    Then response API forgot password tidak memiliki magic link