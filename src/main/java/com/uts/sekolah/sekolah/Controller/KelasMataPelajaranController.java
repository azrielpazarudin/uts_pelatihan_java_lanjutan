package com.uts.sekolah.sekolah.Controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.uts.sekolah.sekolah.Dto.KelasMataPelajaranDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.Kelas;
import com.uts.sekolah.sekolah.Model.KelasMataPelajaran;
import com.uts.sekolah.sekolah.Model.MataPelajaran;
import com.uts.sekolah.sekolah.Repositories.KelasMataPelajaranRepository;
import com.uts.sekolah.sekolah.Repositories.KelasRepository;
import com.uts.sekolah.sekolah.Repositories.MataPelajaranRepository;

@RestController
@RequestMapping("sekolah/kelas-mata-pelajaran")
public class KelasMataPelajaranController {
    @Autowired
    KelasMataPelajaranRepository kelasMataPelajaranRepository;
    @Autowired
    KelasRepository kelasRepository;
    @Autowired
    MataPelajaranRepository mataPelajaranRepository;

    @GetMapping("/find-all")
    public Iterable<KelasMataPelajaran> findAll() {
        return kelasMataPelajaranRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findById(@PathVariable int id) {

        Optional<KelasMataPelajaran> iOptional = kelasMataPelajaranRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional.get());
    }

    @GetMapping("/find-all-by-id-kelas/{id}")
    public Object findAllByIdKelas(@PathVariable int Id){
        return kelasMataPelajaranRepository.findKelasMataPelajaranByKelasId(Id);
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody KelasMataPelajaranDto kelasMataPelajaranDto, Errors errors) {
        ResponseData<KelasMataPelajaran> responseData = new ResponseData<>();
        Optional<Kelas> kOptional = kelasRepository.findById(kelasMataPelajaranDto.getIdKelas());
        Optional<MataPelajaran> mOptional = mataPelajaranRepository
                .findById(kelasMataPelajaranDto.getIdMataPelajaran());
        if (errors.hasErrors() || kOptional.isEmpty() || mOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(kOptional.isEmpty() ? "Data Kelas Tidak Ditemukan" : null);
            responseData.getMessage().add(mOptional.isEmpty() ? "Data Mata Pelajaran Tidak Ditemukan" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        KelasMataPelajaran kelasMataPelajaran = new KelasMataPelajaran();
        Kelas kelas = kOptional.get();
        MataPelajaran mataPelajaran = mOptional.get();
        kelasMataPelajaran.setKelas(kelas);
        kelasMataPelajaran.setMataPelajaran(mataPelajaran);
        responseData.getMessage().add("Succes");
        responseData.setStatus(false);
        responseData.setPayload(kelasMataPelajaranRepository.save(kelasMataPelajaran));
        return ResponseEntity.ok(responseData);

    }

    @PutMapping("/put/{id}")
    public Object put(@Valid @PathVariable int id, @RequestBody KelasMataPelajaranDto kelasMataPelajaranDto,
            Errors errors) {
        ResponseData<KelasMataPelajaran> responseData = new ResponseData<>();
        Optional<KelasMataPelajaran> iOptional = kelasMataPelajaranRepository.findById(id);
        Optional<Kelas> kOptional = kelasRepository.findById(kelasMataPelajaranDto.getIdKelas());
        Optional<MataPelajaran> mOptional = mataPelajaranRepository
                .findById(kelasMataPelajaranDto.getIdMataPelajaran());
        if (errors.hasErrors()||iOptional.isEmpty() || kOptional.isEmpty() || mOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(iOptional.isEmpty() ? "Data Tidak Ditemukan" : null);
            responseData.getMessage().add(kOptional.isEmpty() ? "Data Kelas Tidak Ditemukan" : null);
            responseData.getMessage().add(mOptional.isEmpty() ? "Data Mata Pelajaran Tidak Ditemukan" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        KelasMataPelajaran kelasMataPelajaran = iOptional.get();
        Kelas kelas = kOptional.get();
        MataPelajaran mataPelajaran = mOptional.get();
        kelasMataPelajaran.setKelas(kelas);
        kelasMataPelajaran.setMataPelajaran(mataPelajaran);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(kelasMataPelajaranRepository.save(kelasMataPelajaran));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("delete-by-id/{id}")
    public Object deleteById(@PathVariable int id){
        Optional<KelasMataPelajaran> iOptional = kelasMataPelajaranRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Data Tidak Ditemukan");
        }
        kelasMataPelajaranRepository.existsById(id);
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

}
