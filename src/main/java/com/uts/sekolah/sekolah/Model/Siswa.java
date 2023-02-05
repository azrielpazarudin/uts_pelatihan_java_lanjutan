package com.uts.sekolah.sekolah.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uts.sekolah.sekolah.Enum.Gender;
import com.uts.sekolah.sekolah.Enum.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Siswa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nisn",unique = true,length = 10)
    private String nisn;
    @Column(name="nama",unique = true ,length=50)
    private String nama;
    @Column (name="tempat_tanggal_lahir",length = 75)
    private String tempatTinggalLahir;
    @Enumerated(EnumType.STRING)
    @Column(name="jenis_kelamin",length=1)
    @JsonProperty("jenisKelamin")
    private Gender gender;
    @Column(name="alamat",columnDefinition = "TEXT")
    private String alamat;
    @Column(name = "no_telpon", unique = true, length = 15)
    private String noTelpon;
    @Column(name = "email", unique = true, length = 30)
    private String email;
    @Column(name="nama_orang_tua_wali",length = 50)
    private String namaOrangTuaWali;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @Column(name = "no_telpon_orang_tua_wali",length = 15)
    private String noTelponOrangTuaWali;
    @ManyToOne()
    @JoinColumn(name="id_kelas")
    private Kelas kelas;
}


