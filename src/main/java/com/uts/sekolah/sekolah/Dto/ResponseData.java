package com.uts.sekolah.sekolah.Dto;

import java.util.ArrayList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private ArrayList<String> message = new ArrayList<>();
    private boolean status;
    private T payload;

}
