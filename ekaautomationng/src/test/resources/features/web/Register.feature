Feature: Register Web
  Sebagai user Simple Apps, saya ingin register lewat halaman Web supaya saya bisa masuk ke Dashboard.
  Membungkus id.co.juaracoding.selenium.RegisterTest (pertemuan 2, Unit 3) dalam bentuk Gherkin.
 
  Background:
    Given aplikasi Simple Apps sudah menyala di halaman register
 
  @smoke
  Scenario: Register berhasil dengan data valid
    When saya isi form register dengan data valid
    And saya isi captcha register dengan benar
    Then tombol register harus dalam keadaan enabled
    
    
     
  