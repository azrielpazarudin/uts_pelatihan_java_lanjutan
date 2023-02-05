package com.uts.sekolah.sekolah.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.uts.sekolah.sekolah.Model.MataPelajaran;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuruDto {

    @NotEmpty(message = "Nip Tidak Boleh Kosong")
    @Size(min = 18, max = 18, message = "Nip Mengandung 18 Karakter")
    private String nip;
    @NotEmpty(message = "Nama Dibutuhkan")
    @Size(min = 3, max = 35, message = "Nama Minimal 3 Dan Maksimal 35 Karakter")
    private String nama;
    @NotEmpty(message = "Tempat,Tanggal Lahir Dibutuhkan")
    private String tempatTanggalLahir;
    @NotEmpty(message = "Alamat Dibutuhkan")
    private String alamat;
    @NotEmpty(message = "Nomor Telepon Dibutuhkan")
    @Size(min = 11, max = 15, message = "Nomor Telepon Minimal 11 Dan Maksimal 15 Karakter,Format (0/+62)")
    private String noTelpon;
    @NotEmpty(message = "Email Dibutuhkan")
    @Email(message = "Email Tidak Valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    MataPelajaran mataPelajaran;
}
