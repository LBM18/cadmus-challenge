package com.project.cadmus_challenge.api.responseobjs;

import lombok.Getter;

@Getter
public abstract class ResultDto {
    protected Boolean success;
    protected String message;
}
