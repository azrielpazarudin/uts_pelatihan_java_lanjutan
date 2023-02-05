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
public class EkskulDto {
    @NotEmpty
    @Size(min=3,max=30)
    @JsonProperty("ekskul")
    private String namaEkskul;
}
