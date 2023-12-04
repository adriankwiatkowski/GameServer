package com.example.gameserver.exception;

public class GameReviewDuplicateException extends RuntimeException {

    public GameReviewDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public GameReviewDuplicateException(String msg) {
        super(msg);
    }
}