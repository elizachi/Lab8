package ru.itmo.client.app.exceptions;

public class CheckHumanException extends Exception {
    private final ErrorType errorType;

    public CheckHumanException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public enum ErrorType {
        EMPTY(),
        TYPE(),
        FORBIDDEN();
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

