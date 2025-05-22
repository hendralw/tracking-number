package com.hendralw.myproject.controller;

import com.hendralw.myproject.model.TrackingNumberResponse;
import com.hendralw.myproject.model.general.ApiResponse;
import com.hendralw.myproject.service.TrackingNumberService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class TrackingNumberController {
    private final TrackingNumberService service;

    public TrackingNumberController(TrackingNumberService service) {
        this.service = service;
    }

    @GetMapping("/next-tracking-number")
    public ResponseEntity<ApiResponse<TrackingNumberResponse>> getNextTrackingNumber(
            @RequestParam("origin_country_id") String originCountryId,
            @RequestParam("destination_country_id") String destinationCountryId,
            @RequestParam("weight") Double weight,
            @RequestParam("created_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAt,
            @RequestParam("customer_id") UUID customerId,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_slug") String customerSlug
    ) {
        TrackingNumberResponse response = service.generateTrackingNumber(
                originCountryId,
                destinationCountryId,
                weight,
                createdAt,
                customerId,
                customerName,
                customerSlug
        );

        ApiResponse<TrackingNumberResponse> apiResponse = new ApiResponse<>(true, response, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/tracking-numbers")
    public ResponseEntity<ApiResponse<List<TrackingNumberResponse>>> getAllTrackingNumbers() {
        List<TrackingNumberResponse> list = service.getAllTrackingNumbers();
        return ResponseEntity.ok(new ApiResponse<>(true, list, null));
    }
}
