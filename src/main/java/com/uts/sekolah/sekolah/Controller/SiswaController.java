package com.uts.sekolah.sekolah.Controller;

import javax.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import com.uts.sekolah.sekolah.Model.Kelas;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Dto.SiswaDto;
import com.uts.sekolah.sekolah.Model.Siswa;
import com.uts.sekolah.sekolah.Repositories.KelasRepository;
import com.uts.sekolah.sekolah.Repositories.SiswaRepository;

@RestController
@RequestMapping("sekolah/siswa")
public class SiswaController {
    @Autowired
    SiswaRepository siswaRepository;
    @Autowired
    KelasRepository kelasRepository;

    @GetMapping("/find-all")
    public Iterable<Siswa> findAll() {
        return siswaRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findById(@PathVariable int id) {
        Optional<Siswa> iOptional = siswaRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional);
    }

    @GetMapping("/find-by-nama/{nama}")
    public Object findByNama(@PathVariable String nama) {
        Optional<Siswa> nOptional = siswaRepository.findSiswaByNama(nama);
        if (nOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");

        }
        return ResponseEntity.ok(nOptional);
    }

    @GetMapping("/find-by-email/{email}")
    public Object findByEmail(@PathVariable String email) {
        Optional<Siswa> eOptional = siswaRepository.findSiswaByEmail(email);
        if (eOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(eOptional);
    }

    @GetMapping("/find-by-nisn{nisn}")
    public Object findByNisn(@PathVariable String nisn) {
        Optional<Siswa> niOptional = siswaRepository.findSiswaByNisn(nisn);
        if (niOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(niOptional);

    }

    @GetMapping("/find-all-by-id-kelas/{id}")
    public Object findAllByIdKelas(@PathVariable int id) {
        List<Siswa> iList = siswaRepository.findSiswaByKelasId(id);
        if (iList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iList);
    }

    @GetMapping("/find-all-by-nama-kelas/{nama}")
    public Object findAllByNamaKelas(@PathVariable String nama) {
        List<Siswa> nList = siswaRepository.findSiswaByKelasNamaKelas(nama);
        if (nList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(nList);
    }

    @GetMapping("/find-all-by-nama-asc")
    public List<Siswa> findAllByNamaAsc(){
        return siswaRepository.findByOrderByNamaAsc();
    }

    @GetMapping("/find-all-by-nama-desc")
    public List<Siswa> findAllByNamaDesc(){
        return siswaRepository.findByOrderByNamaDesc();
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody SiswaDto siswaDto, Errors errors) {
        ResponseData<Siswa> responseData = new ResponseData<>();
        Optional<Siswa> niOptional = siswaRepository.findSiswaByNisn(siswaDto.getNisn());
        Optional<Siswa> nOptional = siswaRepository.findSiswaByNama(siswaDto.getNama());
        Optional<Siswa> eOptional = siswaRepository.findSiswaByEmail(siswaDto.getEmail());
        Optional<Siswa> noOptional = siswaRepository.findSiswaByNoTelpon(siswaDto.getNoTelpon());
        Optional<Kelas> kOptional = kelasRepository.findById(siswaDto.getIdKelas());

        if (errors.hasErrors()||noOptional.isPresent()||kOptional.isEmpty()||niOptional.isPresent()||nOptional.isPresent()||eOptional.isPresent()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(kOptional.isEmpty()?"Data Kelas Tidak Ditemukan":null);
            responseData.getMessage().add(niOptional.isPresent()?"Nisn Sudah Terdaftar":null);
            responseData.getMessage().add(nOptional.isPresent()?"Nama Sudah Terdaftar":null);
            responseData.getMessage().add(eOptional.isPresent()?"Email Sudah Terdaftar":null);
            responseData.getMessage().add(noOptional.isPresent()?"No Telpon Sudah Terdaftar":null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Siswa siswa = new Siswa();
        Kelas kelas = kOptional.get();
        siswa.setNisn(siswaDto.getNisn());
        siswa.setNama(siswaDto.getNama());
        siswa.setTempatTinggalLahir(siswaDto.getTempatTinggalLahir());
        siswa.setGender(siswaDto.getGender());
        siswa.setAlamat(siswaDto.getAlamat());
        siswa.setNoTelpon(siswaDto.getNoTelpon());
        siswa.setEmail(siswaDto.getEmail());
        siswa.setNamaOrangTuaWali(siswaDto.getNamaOrangTuaWali());
        siswa.setStatus(siswaDto.getStatus());
        siswa.setNoTelponOrangTuaWali(siswaDto.getNoTelponOrangTuaWali());
        siswa.setKelas(kelas);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(siswaRepository.save(siswa));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/put/{id}")
    public Object put(@Valid@PathVariable int id,@RequestBody SiswaDto siswaDto,Errors errors){
        ResponseData<Siswa> responseData = new ResponseData<>();
        Optional<Siswa> iOptional = siswaRepository.findById(id);
        // Optional<Siswa> niOptional = siswaRepository.findSiswaByNisn(siswaDto.getNisn());
        // Optional<Siswa> nOptional = siswaRepository.findSiswaByNama(siswaDto.getNama());
        // Optional<Siswa> eOptional = siswaRepository.findSiswaByEmail(siswaDto.getEmail());
        // Optional<Siswa> noOptional = siswaRepository.findSiswaByNoTelpon(siswaDto.getNoTelpon());
        Optional<Kelas> kOptional = kelasRepository.findById(siswaDto.getIdKelas());
        if (errors.hasErrors()||iOptional.isEmpty()||kOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(kOptional.isEmpty()?"Data Kelas Tidak Ditemukan":null);
            // responseData.getMessage().add(niOptional.isPresent()?"Nisn Sudah Terdaftar":null);
            // responseData.getMessage().add(nOptional.isPresent()?"Nama Sudah Terdaftar":null);
            // responseData.getMessage().add(eOptional.isPresent()?"Email Sudah Terdaftar":null);
            // responseData.getMessage().add(noOptional.isPresent()?"No Telpon Sudah Terdaftar":null);
            responseData.getMessage().add(iOptional.isEmpty()?"Data Tidak Ditemukan":null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Siswa siswa = iOptional.get();
        Kelas kelas = kOptional.get();
        siswa.setNisn(siswaDto.getNisn());
        siswa.setNama(siswaDto.getNama());
        siswa.setTempatTinggalLahir(siswaDto.getTempatTinggalLahir());
        siswa.setGender(siswaDto.getGender());
        siswa.setAlamat(siswaDto.getAlamat());
        siswa.setNoTelpon(siswaDto.getNoTelpon());
        siswa.setEmail(siswaDto.getEmail());
        siswa.setNamaOrangTuaWali(siswaDto.getNamaOrangTuaWali());
        siswa.setStatus(siswaDto.getStatus());
        siswa.setNoTelponOrangTuaWali(siswaDto.getNoTelponOrangTuaWali());
        siswa.setKelas(kelas);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(siswaRepository.save(siswa));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public Object deleteById(@PathVariable int id){
        Optional<Siswa> iOptional = siswaRepository.findById(id);
        if(iOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        siswaRepository.deleteById(id);
        return ResponseEntity.ok("Data Berhasil Terhapus");
    }
    @DeleteMapping("/delete-by-nip/{nip}")
    public Object deleteByNip(@PathVariable String nip){
        Optional<Siswa> niOptional = siswaRepository.findSiswaByNisn(nip);
        if(niOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
    
        }
        siswaRepository.deleteById(niOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Di Hapus");
    }

    @DeleteMapping("/delete-by-nama/{nama}")
    public Object deleteByNama(@PathVariable String nama){
        Optional<Siswa> nOptional= siswaRepository.findSiswaByNama(nama);
        if(nOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        siswaRepository.deleteById(nOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Di Hapus");
    }

}