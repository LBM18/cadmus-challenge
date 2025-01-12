package com.project.cadmus_challenge.core.bases;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UseCaseFacade {
    private final IUseCaseManager manager;
    private final Validator validator;

    @Transactional
    public <T> T execute(UseCase<T> usecase) {
        this.manager.prepare(usecase);
        this.validate(usecase);
        try {
            T res = executeAndHandleExceptions(usecase);
            this.manager.complete(usecase);
            return res;
        } finally {
            this.manager.destroy(usecase);
        }
    }

    private <T> T executeAndHandleExceptions(UseCase<T> prepared) {
        try {
            return prepared.execute();
        } catch (Exception ex) {
            throw new UnexpectedUseCaseException(prepared.getClass(), ex);
        }
    }

    protected void validate(Object usecase) {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(usecase, IUseCaseValidationOrder.class);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder("Validation exception: ");
            violations.forEach(violation -> errorMessages.append(violation.getMessage()).append(","));
            errorMessages.setLength(errorMessages.length() - 1);
            throw new UnexpectedUseCaseException(errorMessages.toString());
        }
    }
}
