package com.uts.sekolah.sekolah.Controller;

import java.util.Collections;
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

import com.uts.sekolah.sekolah.Dto.EkskulSiswaDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.Ekskul;
import com.uts.sekolah.sekolah.Model.EkskulSiswa;
import com.uts.sekolah.sekolah.Model.Siswa;
import com.uts.sekolah.sekolah.Repositories.EkskulRepository;
import com.uts.sekolah.sekolah.Repositories.EkskulSiswaRepository;
import com.uts.sekolah.sekolah.Repositories.SiswaRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("sekolah/ekskul-siswa")
public class EkskulSiswaController {
    @Autowired
    EkskulSiswaRepository ekskulSiswaRepository;
    @Autowired
    EkskulRepository ekskulRepository;
    @Autowired
    SiswaRepository siswaRepository;

    @GetMapping("/find-all")
    public Object findAll() {
        return ekskulSiswaRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findById(@PathVariable int id) {
        Optional<EkskulSiswa> iOptional = ekskulSiswaRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional.get());
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody EkskulSiswaDto ekskulSiswaDto, Errors errors) {
        Optional<Ekskul> eOptional = ekskulRepository.findById(ekskulSiswaDto.getIdEskul());
        Optional<Siswa> sOptional = siswaRepository.findById(ekskulSiswaDto.getIdSiswa());
        ResponseData<EkskulSiswa> responseData = new ResponseData<>();
        if (errors.hasErrors() || eOptional.isEmpty() || sOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(eOptional.isEmpty() ? "Ekskul Tidak Ditemukan" : null);
            responseData.getMessage().add(sOptional.isEmpty() ? "Siswa Tidak Ditemukan" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            return ResponseEntity.badRequest().body(responseData);
        }
        EkskulSiswa ekskulSiswa = new EkskulSiswa();
        Ekskul ekskul = eOptional.get();
        Siswa siswa = sOptional.get();
        ekskulSiswa.setEskul(ekskul);
        ekskulSiswa.setSiswa(siswa);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ekskulSiswaRepository.save(ekskulSiswa));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("put/{id}")
    public Object put(@Valid @RequestBody EkskulSiswaDto ekskulSiswaDto,@PathVariable int id,Errors errors){
        Optional<EkskulSiswa> iOptional = ekskulSiswaRepository.findById(id);
        Optional<Ekskul> eOptional = ekskulRepository.findById(ekskulSiswaDto.getIdEskul());
        Optional<Siswa> sOptional = siswaRepository.findById(ekskulSiswaDto.getIdSiswa());
        ResponseData<EkskulSiswa> responseData = new ResponseData<>();
        if (errors.hasErrors() || eOptional.isEmpty() || sOptional.isEmpty()||iOptional.isEmpty()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(eOptional.isEmpty() ? "Ekskul Tidak Ditemukan" : null);
            responseData.getMessage().add(sOptional.isEmpty() ? "Siswa Tidak Ditemukan" : null);
            responseData.getMessage().add(iOptional.isEmpty() ? "Data Tidak Ditemukan" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            return ResponseEntity.badRequest().body(responseData);
        }
        EkskulSiswa ekskulSiswa = iOptional.get();
        Ekskul ekskul = eOptional.get();
        Siswa siswa = sOptional.get();
        ekskulSiswa.setEskul(ekskul);
        ekskulSiswa.setSiswa(siswa);
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ekskulSiswaRepository.save(ekskulSiswa));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("delete-by-id/{id}")
    public Object deleteNyId(@PathVariable int id){
        Optional<EkskulSiswa> iOptional = ekskulSiswaRepository.findById(id);
        if (iOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        ekskulSiswaRepository.deleteById(id);
        return ResponseEntity.ok("Data Berhasil Di Hapus");
    }

}
