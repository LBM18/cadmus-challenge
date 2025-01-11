package com.project.cadmus_challenge.core.bases;

public class BusinessException extends UnexpectedUseCaseException {
    public BusinessException(String message) {
        super("Business exception: " + message);
    }
}
