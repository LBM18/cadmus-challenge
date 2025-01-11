package com.project.cadmus_challenge.api.responseobjs;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResultDto<T> extends ResultDto {
    private final List<T> data;

    public ListResultDto(List<T> response) {
        super.success = true;
        super.message = null;
        this.data = response;
    }

    public ListResultDto(Exception ex) {
        super.success = false;
        super.message = ex.getMessage();
        this.data = null;
    }
}
