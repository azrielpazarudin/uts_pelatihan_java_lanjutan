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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.uts.sekolah.sekolah.Dto.EkskulDto;
import com.uts.sekolah.sekolah.Dto.ResponseData;
import com.uts.sekolah.sekolah.Model.Ekskul;
import com.uts.sekolah.sekolah.Repositories.EkskulRepository;


@RestController
@RequestMapping("sekolah/ekskul")
public class EkskulController {
    @Autowired
    private EkskulRepository ekskulRepository;
    
    @GetMapping("/find-all")
    public Iterable<Ekskul> findAll(){
        return ekskulRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Object findById(@PathVariable int id){
        Optional<Ekskul> iOptional = ekskulRepository.findById(id);
        if(iOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        return ResponseEntity.ok(iOptional);
        
    }

    @PostMapping("/post")
    public Object post(@Valid @RequestBody EkskulDto ekskulDto, Errors errors){
        ResponseData<Ekskul> responseData = new ResponseData<>();
       //Optional<Ekskul> nOptional = ekskulRepository.findEkskulByNamaEkskul(ekskulDto.getNamaEkskul());
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            //responseData.getMessage().add(nOptional.isPresent()?"Data sudah terdaftar":null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
            Ekskul ekskul = new Ekskul();
            ekskul.setNamaEkskul(ekskulDto.getNamaEkskul());
            responseData.getMessage().add("Succes");
            responseData.setStatus(true);
            responseData.setPayload(ekskulRepository.save(ekskul));
            return ResponseEntity.ok(responseData);

    }
    @PutMapping("/put/{id}")
    public Object put(@Valid@PathVariable int id,@RequestBody EkskulDto ekskulDto,Errors errors){
        ResponseData<Ekskul> responseData = new ResponseData<>();
        Optional<Ekskul> iOptional = ekskulRepository.findById(id);
        if(errors.hasErrors()||iOptional.isEmpty()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.getMessage().add(iOptional.isEmpty()?"Data Tidak Ditemukan":null);
            responseData.getMessage().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
        }
        Ekskul ekskul = iOptional.get();
        ekskul.setNamaEkskul(ekskulDto.getNamaEkskul());
        responseData.getMessage().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ekskulRepository.save(ekskul));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public Object deleteById(@PathVariable int id){
        Optional<Ekskul> iOptional = ekskulRepository.findById(id);
        if(iOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Tidak Ditemukan");
        }
        ekskulRepository.deleteById(id);
        return ResponseEntity.ok("Data Berhasil Dihapus");
    }



}
