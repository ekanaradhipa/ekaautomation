Feature: Register Web
  Sebagai user Simple Apps, saya ingin register lewat halaman Web supaya saya bisa masuk ke Dashboard.
  Membungkus id.co.juaracoding.selenium.RegisterTest (pertemuan 2, Unit 3) dalam bentuk Gherkin.
 
  Background:
    Given saya sudah mengambil captcha dari API
 
  @smoke
  Scenario: Register Api berhasil dengan data valid
    When saya isi form register dengan data valid
    Then response valid

  @negatif
   Scenario: Register gagal dengan data invalid
    When saya isi form register dengan data invalid
    Then response invalid
    
    
     