package com.uts.sekolah.sekolah.Repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.uts.sekolah.sekolah.Model.MataPelajaran;

@Transactional
public interface MataPelajaranRepository extends CrudRepository<MataPelajaran, Integer> {
     Optional<MataPelajaran> findMataPelajaranByNamaMataPelajaran(String NamaMataPelajaran);

     void deleteMataPelajaranByNamaMataPelajaran(String name);

     List<MataPelajaran> findByOrderByIdAsc();

}
