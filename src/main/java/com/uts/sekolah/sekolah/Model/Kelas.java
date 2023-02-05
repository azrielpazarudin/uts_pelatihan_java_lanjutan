package com.uts.sekolah.sekolah.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Kelas")
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("kelas")
    @Column(name = "kelas", unique = true)
    private String namaKelas;
    @OneToOne
    @JoinColumn(name="id_guru")
    private Guru guru;
}
