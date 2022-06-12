package ru.itmo.client.auth.exceptions;

public class AuthException extends Exception{

    private ErrorType errorType;

    public AuthException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public enum ErrorType {
        UNKNOWN(),
        WRONG(),
        ERROR();
        private String title;

        public ErrorType setTitle(String title) {
            this.title = title;
            return this;
        }
    }
}
