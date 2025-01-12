package com.project.cadmus_challenge.unit.usecases;

import com.project.cadmus_challenge.core.bases.UseCaseFacade;
import com.project.cadmus_challenge.core.bases.UseCaseManager;
import jakarta.validation.Validator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class UseCaseUnitTests {
    @Mock
    protected UseCaseManager managerMock;

    @Mock
    protected Validator validatorMock;

    @InjectMocks
    protected UseCaseFacade facade;
}
