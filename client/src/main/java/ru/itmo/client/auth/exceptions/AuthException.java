package ru.itmo.client.auth.exceptions;

public class AuthException extends Exception{

    private ErrorType errorType;

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public AuthException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public enum ErrorType {
        UNKNOWN(),
        WRONG(),
        ERROR(),
        EXIST();
        private String title;

        public ErrorType setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getTitle() {
            return this.title;
        }
    }
}
