package com.example.wajibpajak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse<T> {
    private String message;
    private T data;
}