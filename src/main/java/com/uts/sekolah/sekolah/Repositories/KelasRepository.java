package com.uts.sekolah.sekolah.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.uts.sekolah.sekolah.Model.Kelas;

public interface KelasRepository extends CrudRepository<Kelas, Integer> {
    Optional<Kelas> findKelasByNamaKelas(String namaKelas);

    List<Kelas> findByOrderByNamaKelasAsc();

    List<Kelas> findByOrderByNamaKelasDesc();
}
