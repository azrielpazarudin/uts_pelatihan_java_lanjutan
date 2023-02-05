package com.uts.sekolah.sekolah.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uts.sekolah.sekolah.Dto.KelasDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.Kelas;
import com.uts.sekolah.sekolah.Repositories.KelasRepository;

@RestController
@RequestMapping("sekolah/kelas")
public class KelasController {
    @Autowired
    KelasRepository kelasRepository;

    @GetMapping("/find-all")
    public Iterable<Kelas> findAll() {
        return kelasRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findById(@PathVariable int id) {
        Optional<Kelas> iOptional = kelasRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional);
    }

    @GetMapping("/find-by-nama/{nama}")
    public Object findByNama(@PathVariable String nama) {
        Optional<Kelas> nOptional = kelasRepository.findKelasByNamaKelas(nama);
        if (nOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(nOptional);
    }

    @GetMapping("/find-all-by-nama-asc")
    public List<Kelas> findAllByNamaAsc() {
        return kelasRepository.findByOrderByNamaKelasAsc();
    }

    @GetMapping("/find-all-by-nama-desc")
    public List<Kelas> findAllByNameDesc() {
        return kelasRepository.findByOrderByNamaKelasDesc();
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody KelasDto kelasDto, Errors errors) {
        ResponseData<Kelas> responseData = new ResponseData<>();
        Optional<Kelas> nOptional = kelasRepository.findKelasByNamaKelas(kelasDto.getNamaKelas());
        if (errors.hasErrors() || nOptional.isPresent()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(nOptional.isPresent() ? "Nama Kelas Sudah Ada" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Kelas kelas = new Kelas();
        kelas.setNamaKelas(kelasDto.getNamaKelas());
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(kelasRepository.save(kelas));
        return ResponseEntity.ok(responseData);

    }

    @PutMapping("/put/{id}")
    public Object put(@Valid @RequestBody KelasDto kelasDto, Errors errors, @PathVariable int id) {
        ResponseData<Kelas> responseData = new ResponseData<>();
        Optional<Kelas> iOptional = kelasRepository.findById(id);
        Optional<Kelas> nOptional = kelasRepository.findKelasByNamaKelas(kelasDto.getNamaKelas());
        if (iOptional.isEmpty() || errors.hasErrors()||nOptional.isPresent()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(nOptional.isPresent()?"Kelas Sudah Ada":null);
            responseData.getMessage().add(iOptional.isEmpty() ? "Id Tidak Ditemukan" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);

        }
        Kelas kelas = iOptional.get();
        kelas.setNamaKelas(kelasDto.getNamaKelas());
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(kelasRepository.save(kelas));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public Object deleteById(@PathVariable int id){
        Optional<Kelas> iOptional = kelasRepository.findById(id);
        if(iOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        kelasRepository.deleteById(id);
        return ResponseEntity.ok("Berhasil Menghapus Data");
    }

    @DeleteMapping("/delete-by-nama/{nama}")
    public Object deleteByNama(@PathVariable String nama){
        Optional<Kelas> nOptional = kelasRepository.findKelasByNamaKelas(nama);
        if(nOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        kelasRepository.deleteById(nOptional.get().getId());
        return ResponseEntity.ok("Berhasil Menghapus Data");
    }

}
