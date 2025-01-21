package org.capitole.inditex.adapter.in.controller.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private String path;
}