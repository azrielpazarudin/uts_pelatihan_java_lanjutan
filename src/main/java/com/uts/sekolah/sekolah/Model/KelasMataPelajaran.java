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
@AllArgsConstructor
@NoArgsConstructor
@Table(name="kelas_mata_pelajaran")
public class KelasMataPelajaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="id_kelas")
    private  Kelas kelas;
    @ManyToOne
    @JoinColumn(name="id_mata_pelajaran")
    private MataPelajaran mataPelajaran;
}
