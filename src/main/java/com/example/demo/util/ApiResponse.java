package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    @JsonCreator
    public ApiResponse(@JsonProperty("data") T data, @JsonProperty("status") int status) {
        this.data = data;
        this.status = status;
    }

}
