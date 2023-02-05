package com.uts.sekolah.sekolah.Dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EkskulSiswaDto {
    @Size(min=1,max=1)
    private int idEskul;
    @Size(min=1,max=1)
    private int idSiswa;
}
