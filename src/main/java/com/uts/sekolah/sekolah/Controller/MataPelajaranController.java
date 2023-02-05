package com.uts.sekolah.sekolah.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.uts.sekolah.sekolah.Dto.MataPelajaranDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.MataPelajaran;
import com.uts.sekolah.sekolah.Repositories.MataPelajaranRepository;

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

@RestController
@RequestMapping("/sekolah/mata-pelajaran")
public class MataPelajaranController {
    @Autowired
    private MataPelajaranRepository mataPelajaranRepository;

    @GetMapping("/find-all")
    public Object findAll() {
        return mataPelajaranRepository.findByOrderByIdAsc();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findByIdMataPelajaran(@PathVariable int id) {

        Optional<MataPelajaran> iOptional = mataPelajaranRepository.findById(id);
        if (!iOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id Tidak Ditemukan");
        }
        return mataPelajaranRepository.findById(id);
    }

    @GetMapping("/find-by-mata-pelajaran/{mataPelajaran}")
    public Optional<MataPelajaran> findByMataPelaran(@PathVariable String mataPelajaran) {
        return mataPelajaranRepository.findMataPelajaranByNamaMataPelajaran(mataPelajaran);
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody MataPelajaranDto mataPelajaranDto, Errors errors) {
        ResponseData<MataPelajaran> responseData = new ResponseData<>();
        MataPelajaran mataPelajaran = new MataPelajaran();
        Optional<MataPelajaran> mOptional = mataPelajaranRepository
                .findMataPelajaranByNamaMataPelajaran(mataPelajaranDto.getNamaMataPelajaran());
        if (errors.hasErrors() || mOptional.isPresent()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            String myMessage = mOptional.isPresent() ? "Mata Pelajaran Sudah Ada" : null;
            responseData.getMessage().add(myMessage);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        mataPelajaran.setNamaMataPelajaran(mataPelajaranDto.getNamaMataPelajaran());
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(mataPelajaranRepository.save(mataPelajaran));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/put/{id}")
    public Object put(@Valid @PathVariable int id,
            @RequestBody MataPelajaranDto mataPelajaranDto, Errors errors) {
        ResponseData<MataPelajaran> responseData = new ResponseData<>();
        Optional<MataPelajaran> iOptional = mataPelajaranRepository.findById(id);

        if (errors.hasErrors() || !iOptional.isPresent()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(iOptional.isEmpty() ? "Data Tidak Ditemukan" : null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            // responseData.getMessage().add(mOptional.isPresent() ? "Nama Mata Pelajaran
            // Sudah Ada" : null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        MataPelajaran mataPelajaran = iOptional.get();

        mataPelajaran.setNamaMataPelajaran(mataPelajaranDto.getNamaMataPelajaran());
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(mataPelajaranRepository.save(mataPelajaran));
        return ResponseEntity.ok(mataPelajaranDto.getNamaMataPelajaran());
    }

    @DeleteMapping("/delete-by-id/{id}")
    public Object deleteById(@PathVariable int id) {
        Optional<MataPelajaran> iOptional = mataPelajaranRepository.findById(id);
        if (!iOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id Tidak Ditemukan");
        }
        mataPelajaranRepository.deleteById(id);
        return ResponseEntity.ok("Data Berhasil Di Hapus");
    }

    @DeleteMapping("/delete-by-name/{name}")
    public Object deleteByName(@PathVariable String name) {
        Optional<MataPelajaran> mOptional = mataPelajaranRepository.findMataPelajaranByNamaMataPelajaran(name);
        if (!mOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        mataPelajaranRepository.deleteMataPelajaranByNamaMataPelajaran(name);
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }

}
