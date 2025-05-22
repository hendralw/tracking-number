package com.hendralw.myproject.service;

import com.hendralw.myproject.entity.TrackingNumberEntity;
import com.hendralw.myproject.exception.DuplicateTrackingNumberException;
import com.hendralw.myproject.model.TrackingNumberResponse;
import com.hendralw.myproject.repository.TrackingNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class TrackingNumberService {
    private static final int MAX_LENGTH = 16;
    private static final int MAX_RETRIES = 5;

    private final TrackingNumberRepository repository;

    public TrackingNumberService(TrackingNumberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TrackingNumberResponse generateTrackingNumber(
            String originCountryId,
            String destinationCountryId,
            Double weight,
            OffsetDateTime createdAt,
            UUID customerId,
            String customerName,
            String customerSlug
    ) {
        String trackingNumber = generateFormattedTrackingNumber(
                originCountryId,
                destinationCountryId,
                createdAt,
                customerId,
                customerSlug
        );

        boolean exists = repository.findByTrackingNumber(trackingNumber).isPresent();
        if (exists) {
            throw new DuplicateTrackingNumberException("Duplicate tracking number: " + trackingNumber);
        }

        TrackingNumberEntity entity = new TrackingNumberEntity();
        entity.setTrackingNumber(trackingNumber);
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setOriginCountryId(originCountryId.toUpperCase(Locale.ROOT));
        entity.setDestinationCountryId(destinationCountryId.toUpperCase(Locale.ROOT));
        entity.setCustomerId(customerId);
        entity.setCustomerName(customerName);
        entity.setCustomerSlug(customerSlug);
        entity.setWeight(weight);

        repository.save(entity);
        return new TrackingNumberResponse(trackingNumber, entity.getCreatedAt());
    }

    private String generateFormattedTrackingNumber(String origin, String destination, OffsetDateTime createdAt, UUID customerId, String customerSlug) {
        String input = origin + destination + createdAt.toString() + customerId.toString() + customerSlug;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String base36 = toBase36(hash);
            String result = (origin + destination + base36).toUpperCase(Locale.ROOT);
            return result.substring(0, Math.min(MAX_LENGTH, result.length()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    private String toBase36(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length && sb.length() < (MAX_LENGTH - 4); i++) {
            int v = Byte.toUnsignedInt(bytes[i]);
            sb.append(Integer.toString(v % 36, 36));
        }
        return sb.toString().toUpperCase(Locale.ROOT);
    }

    public List<TrackingNumberResponse> getAllTrackingNumbers() {
        return repository.findAll().stream()
                .map(entity -> new TrackingNumberResponse(
                        entity.getTrackingNumber(),
                        entity.getCreatedAt()
                ))
                .toList();
    }
}