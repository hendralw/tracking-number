package com.hendralw.myproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tracking_numbers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "trackingNumber")
})
@Getter
@Setter
public class TrackingNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 16)
    private String trackingNumber;

    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private UUID customerId;

    private String originCountryId;
    private String destinationCountryId;
    private String customerSlug;
    private String customerName;
    private Double weight;
}
