package com.hendralw.myproject.model;

import java.time.OffsetDateTime;

public record TrackingNumberResponse(
        String tracking_number,
        OffsetDateTime created_at
) {}
