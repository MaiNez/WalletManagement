package com.sergeymalov.walletmanagement.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private  int status;
    private  String message;
    private  LocalDateTime timestamp;
    private  Map<String, Object> details;


    public ErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, Object> details) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

}