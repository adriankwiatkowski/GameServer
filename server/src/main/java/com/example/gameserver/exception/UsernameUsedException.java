package com.example.gameserver.exception;

public class UsernameUsedException extends RuntimeException {

    public UsernameUsedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameUsedException(String msg) {
        super(msg);
    }
}