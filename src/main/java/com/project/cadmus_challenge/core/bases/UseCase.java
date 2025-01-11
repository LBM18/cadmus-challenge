package com.project.cadmus_challenge.core.bases;

public abstract class UseCase<T> {
    protected abstract T execute() throws Exception;
}
