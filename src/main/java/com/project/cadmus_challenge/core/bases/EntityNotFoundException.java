package com.project.cadmus_challenge.core.bases;

public class EntityNotFoundException extends UnexpectedUseCaseException {
    public EntityNotFoundException(String message) {
        super("Entity not found exception: " + message);
    }
}
