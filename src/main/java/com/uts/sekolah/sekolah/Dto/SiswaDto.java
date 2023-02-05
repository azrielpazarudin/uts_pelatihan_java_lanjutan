package com.uts.sekolah.sekolah.Dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uts.sekolah.sekolah.Enum.Gender;
import com.uts.sekolah.sekolah.Enum.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiswaDto {
    @NotEmpty(message = "Nisn Tidak Boleh Kosong")
    @Size(min = 10, max = 10)
    private String nisn;
    @NotEmpty(message = "Nama Tidak Boleh Kosong")
    @Size(min=3,max=50)
    private String nama;
    @NotEmpty(message = "Tempat Tanggal Lahir Tidak Boleh Kosong")
    private String tempatTinggalLahir;
    @JsonProperty("jenisKelamin")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty(message="Alamat Tidak Boleh Kosong")
    private String alamat;
    @NotEmpty(message = "Nomor Telepon Tidak Boleh Kosong")
    @Size(min = 11, max = 15, message = "Nomor Telepon Minimal 11 Dan Maksimal 15 Karakter,Format (0/+62)")
    private String noTelpon;
    @NotEmpty(message = "Email Dibutuhkan")
    @Email(message = "Email Tidak Valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    @NotEmpty(message = "Nama Ortang Tua /Wali Dibutuhkan")
    private String namaOrangTuaWali;
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotEmpty(message = "Nomor Telepon Tidak Boleh Kosong")
    @Size(min = 11, max = 15, message = "Nomor Telepon Minimal 11 Dan Maksimal 15 Karakter,Format (0/+62)")
    private String noTelponOrangTuaWali;
    private int idKelas;
}



