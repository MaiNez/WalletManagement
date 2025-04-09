package com.sergeymalov.walletmanagement.exception;


public class NoWalletsFoundException extends RuntimeException {
    public NoWalletsFoundException(String message) {
        super(message);
    }
}
