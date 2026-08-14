Feature: Forgot Password Web
  Sebagai user Simple Apps, saya ingin reset password lewat halaman Web supaya saya bisa masuk ke Dashboard.
  Membungkus id.co.juaracoding.selenium.ForgotPasswordTest (pertemuan 2, Unit 3) dalam bentuk Gherkin.
  
  Background:
    Given aplikasi Simple Apps sudah menyala di halaman forgot password

    @smoke
    Scenario: Reset password berhasil dengan email valid
      When saya isi form forgot password dengan email valid
      And saya isi captcha dengan benar
      And saya klik tombol reset password
      Then magic link valid

    @negatif
    Scenario: Reset password gagal dengan email invalid
      When saya isi form forgot password dengan email invalid
      And saya isi captcha dengan benar
      And saya klik tombol reset password
      Then magic link invalid
