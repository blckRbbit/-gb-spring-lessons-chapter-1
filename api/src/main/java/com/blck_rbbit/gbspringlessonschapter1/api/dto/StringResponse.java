package com.blck_rbbit.gbspringlessonschapter1.api.dto;

public class StringResponse {
    private String value;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public StringResponse(String value) {
        this.value = value;
    }
    
    public StringResponse() {
    }
}