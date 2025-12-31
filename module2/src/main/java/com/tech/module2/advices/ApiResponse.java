package com.tech.module2.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private T data;
    private ApiError error;

    @JsonFormat(pattern = "hh-mm-ss a dd-MM-yyyy")
    private LocalDateTime timeStamp;

    public ApiResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();  //call default constructor
        this.data = data;
    }

    public ApiResponse(ApiError error){
        this();  //call default constructor
        this.error = error;
    }
}
