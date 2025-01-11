package com.project.cadmus_challenge.core.bases;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, LazyValidation.class})
public interface UseCaseValidationOrder {
}
