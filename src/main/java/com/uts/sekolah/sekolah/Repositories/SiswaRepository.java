package com.uts.sekolah.sekolah.Repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.uts.sekolah.sekolah.Model.Siswa;

public interface SiswaRepository extends CrudRepository<Siswa, Integer> {
    Optional<Siswa> findSiswaByNisn(String nisn);

    Optional<Siswa> findSiswaByNama(String nama);

    Optional<Siswa> findSiswaByNoTelpon(String noTelp);

    Optional<Siswa> findSiswaByEmail(String email);

    List<Siswa> findByOrderByNamaAsc();

    List<Siswa> findByOrderByNamaDesc();

    List<Siswa> findSiswaByKelasId(int id);

    List<Siswa> findSiswaByKelasNamaKelas(String namaKelas);

}
