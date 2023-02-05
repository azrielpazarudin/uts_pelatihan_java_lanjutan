package com.uts.sekolah.sekolah.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="eskul_siswa")
public class EskulSiswa {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name="id_eskul")
    Ekskul eskul;
    @ManyToOne
    @JoinColumn(name="id_siswa")
    Siswa siswa;
    
}
