package com.project.cadmus_challenge.core.bases;

public interface IUseCaseManager {
    void prepare(UseCase<?> usecase);
    void complete(UseCase<?> usecase);
    void destroy(UseCase<?> usecase);
}
