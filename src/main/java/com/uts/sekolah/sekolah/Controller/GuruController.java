package com.uts.sekolah.sekolah.Controller;

import java.util.Collections;
import java.util.Optional;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.uts.sekolah.sekolah.Dto.GuruDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.Guru;
import com.uts.sekolah.sekolah.Model.MataPelajaran;
import com.uts.sekolah.sekolah.Repositories.GuruRepository;
import com.uts.sekolah.sekolah.Repositories.MataPelajaranRepository;

@RestController
@RequestMapping("sekolah/guru")
public class GuruController {
    @Autowired
    private GuruRepository guruRepository;
    @Autowired
    private MataPelajaranRepository mataPelajaranRepository;

    @GetMapping("/find-all")
    public Iterable<Guru> findAll() {
        return guruRepository.findAll();
    }

    @GetMapping("/find-all-by-nama-asc")
    public Iterable<Guru> findAllByNamaAsc() {
        return guruRepository.findByOrderByNamaAsc();
    }

    @GetMapping("/find-all-by-nama-desc")
    public Iterable<Guru> findAllByNamaDesc() {
        return guruRepository.findByOrderByNamaDesc();
    }

    @GetMapping("find-by-id/{id}")
    public Object findById(@PathVariable int id) {
        Optional<Guru> iOptional = guruRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional);
    }

    @GetMapping("find-by-nip/{nip}")
    public Object findByNip(@PathVariable String nip) {
        Optional<Guru> nOptional = guruRepository.findGuruByNip(nip);
        if (nOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(nOptional);
    }

    @GetMapping("find-by-email/{email}")
    public Object findByEmail(@PathVariable String email) {
        Optional<Guru> eOptional = guruRepository.findGuruByEmail(email);
        if (eOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(eOptional);
    }

    @GetMapping("find-by-id-mata-pelajaran/{id}")
    public Object findByIdMP(@PathVariable int id){
        List<Guru> iList = guruRepository.findGuruByMataPelajaranId(id);
        if(iList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iList);
    }

    @GetMapping("find-by-nama-mata-pelajaran/{nama}")
    public Object findByNamaMP(@PathVariable String nama){
        List<Guru> nList=guruRepository.findGuruByMataPelajaranNamaMataPelajaran(nama);
        if(nList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(nList);
    }
    @GetMapping("find-by-no-telpon/{noTelp}")
    public Object findByNoTelp(@PathVariable String noTelp) {
        Optional<Guru> nOptional = guruRepository.findGuruByNoTelpon(noTelp);
        if (nOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(nOptional);
    }
    


    @PostMapping("post")
    public Object post(@Valid @RequestBody GuruDto guruDto, Errors errors) {
        Optional<Guru> niOptional = guruRepository.findGuruByNip(guruDto.getNip());
        Optional<Guru> nOptional = guruRepository.findGuruByNama(guruDto.getNama());
        Optional<Guru> notOptional = guruRepository.findGuruByNoTelpon(guruDto.getNoTelpon());
        Optional<Guru> eOptional = guruRepository.findGuruByEmail(guruDto.getEmail());
        Optional<MataPelajaran> iOptional = mataPelajaranRepository.findById(guruDto.getMataPelajaran().getId());
        Optional<MataPelajaran> nmpOptional = mataPelajaranRepository
                .findMataPelajaranByNamaMataPelajaran(guruDto.getMataPelajaran().getNamaMataPelajaran());
        ResponseData<Guru> responseData = new ResponseData<>();
        if (errors.hasErrors() || niOptional.isPresent() || nOptional.isPresent() || notOptional.isPresent()
                || eOptional.isPresent() || iOptional.isEmpty() || nmpOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(niOptional.isPresent() ? "NIP Sudah Terdatftar" : null);
            responseData.getMessage().add(nOptional.isPresent() ? "Nama Sudah Terdatftar" : null);
            responseData.getMessage().add(notOptional.isPresent() ? "No Telpon Sudah Terdatftar" : null);
            responseData.getMessage().add(eOptional.isPresent() ? "Email Sudah Terdaftar" : null);
            responseData.getMessage().add(iOptional.isEmpty() ? "Id Mata Pelajaran Tidak Ditemukan" : null);
            responseData.getMessage().add(nmpOptional.isEmpty() ? "Nama Mata Pelajaran Tidak Ada" : null);
            if (nmpOptional.isPresent() && iOptional.isPresent()) {
                if (iOptional.get().getId() != nmpOptional.get().getId()
                        || iOptional.get().getNamaMataPelajaran().equals(nmpOptional.get().getNamaMataPelajaran())) {
                    responseData.getMessage().add("Mata Pelajaran : Detail Data Tidak Cocok");
                }
            }
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Guru guru = new Guru();
        MataPelajaran mataPelajaran = new MataPelajaran();
        mataPelajaran.setId(guruDto.getMataPelajaran().getId());
        mataPelajaran.setNamaMataPelajaran(guruDto.getMataPelajaran().getNamaMataPelajaran());
        guru.setNip(guruDto.getNip());
        guru.setNama(guruDto.getNama());
        guru.setTempatTanggalLahir(guruDto.getTempatTanggalLahir());
        guru.setAlamat(guruDto.getAlamat());
        guru.setEmail(guruDto.getEmail());
        guru.setNoTelpon(guruDto.getNoTelpon());
        guru.setMataPelajaran(mataPelajaran);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(guruRepository.save(guru));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("put/{id}")
    public Object put(@Valid @RequestBody GuruDto guruDto, @PathVariable int id, Errors errors) {
        Optional<Guru> iOptional = guruRepository.findById(id);
        Optional<MataPelajaran> impOptional = mataPelajaranRepository.findById(guruDto.getMataPelajaran().getId());
        Optional<MataPelajaran> nmpOptional = mataPelajaranRepository
                .findMataPelajaranByNamaMataPelajaran(guruDto.getMataPelajaran().getNamaMataPelajaran());
        ResponseData<Guru> responseData = new ResponseData<>();
        if (errors.hasErrors() || iOptional.isEmpty() || impOptional.isEmpty() || nmpOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(iOptional.isEmpty() ? "Data Tidak Ditemukan" : null);
            responseData.getMessage().add(impOptional.isEmpty() ? "Id Mata Pelajaran Tidak Ditemukan" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        Guru guru = iOptional.get();
        MataPelajaran mataPelajaran = new MataPelajaran();
        mataPelajaran.setId(guruDto.getMataPelajaran().getId());
        mataPelajaran.setNamaMataPelajaran(guruDto.getMataPelajaran().getNamaMataPelajaran());
        guru.setNip(guruDto.getNip());
        guru.setNama(guruDto.getNama());
        guru.setTempatTanggalLahir(guruDto.getTempatTanggalLahir());
        guru.setAlamat(guruDto.getAlamat());
        guru.setEmail(guruDto.getEmail());
        guru.setNoTelpon(guruDto.getNoTelpon());
        guru.setMataPelajaran(mataPelajaran);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(guruRepository.save(guru));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public Object deleteById(@PathVariable int id){
        Optional<Guru> iOptional = guruRepository.findById(id);
        if(iOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        guruRepository.deleteById(iOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Di Hapus");
    }

    @DeleteMapping("/delete-by-nip/{nip}")
    public Object deleteByNip(@PathVariable String nip){
        Optional<Guru> nOptional = guruRepository.findGuruByNip(nip);
        if(nOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        guruRepository.deleteById(nOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

    @DeleteMapping("/delete-by-nama/{nama}")
    public Object deleteByNama(@PathVariable String nama){
        Optional<Guru> nOptional = guruRepository.findGuruByNama(nama);
        if(nOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        guruRepository.deleteById(nOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

    @DeleteMapping("/delete-by-email/{email}")
    public Object deleteByEmail(@PathVariable String email){
        Optional<Guru> eOptional = guruRepository.findGuruByEmail(email);
        if(eOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        guruRepository.deleteById(eOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

    @DeleteMapping("/delete-by-no-telp/{noTelp}")
    public Object deleteByNoTelp(@PathVariable String noTelp){
        Optional<Guru> ntpOptional= guruRepository.findGuruByNoTelpon(noTelp);
        if(ntpOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        guruRepository.deleteById(ntpOptional.get().getId());
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

}
