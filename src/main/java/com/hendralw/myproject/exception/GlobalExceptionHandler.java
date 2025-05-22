package com.hendralw.myproject.exception;

import com.hendralw.myproject.model.general.ApiErrorResponse;
import com.hendralw.myproject.model.general.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOtherExceptions(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );
        ApiResponse<Void> apiResponse = new ApiResponse<>(false, null, error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(DuplicateTrackingNumberException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateTrackingNumber(
            DuplicateTrackingNumberException ex,
            HttpServletRequest request) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );
        ApiResponse<Void> apiResponse = new ApiResponse<>(false, null, error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
}
