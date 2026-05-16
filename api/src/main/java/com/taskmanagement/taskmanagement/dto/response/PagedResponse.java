package com.taskmanagement.taskmanagement.dto.response;

import lombok.*;
import java.util.List;

@Getter @Builder @AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}