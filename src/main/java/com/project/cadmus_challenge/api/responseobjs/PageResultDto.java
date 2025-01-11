package com.project.cadmus_challenge.api.responseobjs;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResultDto<T> extends ResultDto {
    private final List<T> data;
    private final Integer page;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;
    private final Integer number;

    public PageResultDto(Page<T> response) {
        super.success = true;
        super.message = null;
        this.data = response.getContent();
        this.page = response.getNumber();
        this.size = response.getSize();
        this.totalElements = response.getTotalElements();
        this.totalPages = response.getTotalPages();
        this.number = response.getNumber();
    }

    public PageResultDto(Exception ex) {
        super.success = false;
        super.message = ex.getMessage();
        this.data = null;
        this.page = 0;
        this.size = 0;
        this.totalElements = 0L;
        this.totalPages = 0;
        this.number = 0;
    }
}
