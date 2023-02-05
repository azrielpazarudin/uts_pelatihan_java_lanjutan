package com.uts.sekolah.sekolah.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MataPelajaranDto {
    @NotEmpty(message = "Nama Mata Pelajaran Tidak Boleh Kosong")
    @JsonProperty("mataPelajaran")
    @Size(min=2,max=35)
    private String namaMataPelajaran;
}
