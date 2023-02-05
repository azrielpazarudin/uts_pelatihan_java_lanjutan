package com.uts.sekolah.sekolah.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guru")
public class Guru implements Serializable {
    static final long seriealVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "nip", unique = true, length = 18)
    private String nip;
    @Column(name = "nama", unique = true, length = 30)
    private String nama;
    @Column(name = "tempat_tanggal_lahir")
    private String tempatTanggalLahir;
    @Column(name = "alamat", columnDefinition = "TEXT")
    private String alamat;
    @Column(name = "no_telpon", unique = true, length = 15)
    private String noTelpon;
    @Column(name = "email", unique = true, length = 30)
    private String email;
    @OneToOne
    @JoinColumn(name = "id_mata_pelajaran")
    MataPelajaran mataPelajaran;

}
