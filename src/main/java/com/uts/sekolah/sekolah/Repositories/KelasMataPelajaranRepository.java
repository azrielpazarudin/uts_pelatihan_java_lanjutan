package com.uts.sekolah.sekolah.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uts.sekolah.sekolah.Model.KelasMataPelajaran;

public interface KelasMataPelajaranRepository extends CrudRepository<KelasMataPelajaran,Integer> {
    List<KelasMataPelajaran> findKelasMataPelajaranByKelasId(int id);

}
