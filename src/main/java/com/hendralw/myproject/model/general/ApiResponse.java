package com.hendralw.myproject.model.general;

public record ApiResponse<T>(
        boolean success,
        T data,
        ApiErrorResponse error
) {}

