package com.tech.Module4.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
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
