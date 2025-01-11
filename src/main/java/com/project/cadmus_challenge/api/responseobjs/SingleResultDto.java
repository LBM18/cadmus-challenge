package com.project.cadmus_challenge.api.responseobjs;

import lombok.Getter;

@Getter
public class SingleResultDto<T> extends ResultDto {
    private final T data;

    public SingleResultDto(T response) {
        super.success = true;
        super.message = null;
        this.data = response;
    }

    public SingleResultDto(Exception ex) {
        super.success = false;
        super.message = ex.getMessage();
        this.data = null;
    }
}
