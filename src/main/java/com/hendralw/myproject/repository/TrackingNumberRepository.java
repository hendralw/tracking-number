package com.hendralw.myproject.repository;

import com.hendralw.myproject.entity.TrackingNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumberEntity, Long> {
    Optional<TrackingNumberEntity> findByTrackingNumber(String trackingNumber);
}
