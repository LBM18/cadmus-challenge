package com.project.cadmus_challenge.core.bases;

import java.io.Serial;

public class UnexpectedUseCaseException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public UnexpectedUseCaseException(String message) {
        super(message);
    }

    protected UnexpectedUseCaseException(Class<?> useCaseType, Throwable cause) {
        super("Error executing use case " + useCaseType.getSimpleName() + ": " + cause.getLocalizedMessage(), cause);
    }
}
