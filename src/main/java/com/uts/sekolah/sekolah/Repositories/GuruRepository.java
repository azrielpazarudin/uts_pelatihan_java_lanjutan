package com.uts.sekolah.sekolah.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.uts.sekolah.sekolah.Model.Guru;

public interface GuruRepository extends CrudRepository<Guru, Integer> {
    Optional<Guru> findGuruByNip(String nip);

    Optional<Guru> findGuruByNama(String nama);

    Optional<Guru> findGuruByEmail(String email);

    Optional<Guru> findGuruByNoTelpon(String noTelpon);

    List<Guru> findGuruByMataPelajaranId(int id);

    List<Guru> findGuruByMataPelajaranNamaMataPelajaran(String name);

    List<Guru> findByOrderByNamaAsc();

    List<Guru> findByOrderByNamaDesc();

}
