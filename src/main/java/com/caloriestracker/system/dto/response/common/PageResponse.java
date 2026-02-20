package com.caloriestracker.system.dto.response.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> content;

    private int page;
    private int size;

    private long totalElements;
    private int totalPages;

    private boolean first;
    private boolean last;
}