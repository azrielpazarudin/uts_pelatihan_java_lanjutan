package com.uts.sekolah.sekolah.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.uts.sekolah.sekolah.Model.Ekskul;

public interface EkskulRepository extends CrudRepository<Ekskul,Integer>{
    Optional<Ekskul> findEkskulByNamaEkskul(String nama);
}
