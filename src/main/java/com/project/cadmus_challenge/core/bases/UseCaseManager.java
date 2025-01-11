package com.project.cadmus_challenge.core.bases;

public interface UseCaseManager {
    void prepare(UseCase<?> usecase);
    void complete(UseCase<?> usecase);
    void destroy(UseCase<?> usecase);
}
