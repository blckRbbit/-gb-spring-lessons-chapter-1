package com.blck_rbbit.gbspringlessonschapter1.exceptions;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class AppError {
    private int statusCode;
    private List<String> messages;
    
    public AppError() {
    }
    
    public AppError(int statusCode, List<String> messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }
    
    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.messages = (List.of(message));
    }
    
    public AppError(int statusCode, String... messages) {
        this.statusCode = statusCode;
        this.messages = (Arrays.asList(messages));
    }
    

    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public List<String> getMessages() {
        return messages;
    }
    
    public void setMessages(List<String> message) {
        this.messages = message;
    }
    
}
