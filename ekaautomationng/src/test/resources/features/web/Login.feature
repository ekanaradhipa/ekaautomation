Feature: Login Web
  Sebagai user Simple Apps, saya ingin login lewat halaman Web supaya saya bisa masuk ke Dashboard.
  Membungkus id.co.juaracoding.selenium.LoginTest (pertemuan 2, Unit 3) dalam bentuk Gherkin.
 
  Background:
    Given aplikasi Simple Apps sudah menyala di halaman login
 
  @smoke
  Scenario: Login berhasil dengan akun Admin
    When saya login sebagai "admin1" dengan password "Admin1@123"
    Then browser harus pindah ke halaman dashboard
    And nama user yang login mengandung "Admin Satu"
 
  @negatif
  Scenario Outline: Login gagal karena kredensial salah
    When saya login sebagai "<username>" dengan password "<password>"
    Then toast error harus muncul di halaman login
    And browser tetap berada di halaman login
 
    Examples:
      | username | password                  |
      | admin1   | PasswordSalahBanget123!   |
      | admin1   | SalahLagiInii999!         |
      | admin999 | PasswordSalahLagi321!     |

  @negatif
  Scenario: Tombol login disabled saat username kosong
    When saya isi form login dengan username "" dan password "Admin1@123" tanpa klik submit
    Then tombol login harus dalam keadaan disabled