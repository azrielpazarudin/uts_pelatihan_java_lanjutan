package com.uts.sekolah.sekolah.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KelasDto {
    @JsonProperty("kelas")
    @Size(min = 10, max = 12)
    @NotEmpty(message = "Nama Kelas Tidak Boleh Kosong")
    private String namaKelas;
}
