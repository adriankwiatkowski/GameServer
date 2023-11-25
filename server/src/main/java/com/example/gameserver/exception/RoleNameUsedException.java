package com.example.gameserver.exception;

public class RoleNameUsedException extends RuntimeException {

    public RoleNameUsedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RoleNameUsedException(String msg) {
        super(msg);
    }
}