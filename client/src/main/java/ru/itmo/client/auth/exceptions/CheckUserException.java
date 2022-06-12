package ru.itmo.client.auth.exceptions;

public class CheckUserException extends Throwable {

    private final ErrorType errorType;


    public CheckUserException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public enum ErrorType {
        EMPTY(),
        FORBIDDEN(),
        UNSAFE();
        private String title;

        public ErrorType setTitle(String title) {
            this.title = title;
            return this;
        }
    }
}