package com.doziem.market_platform.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class WorkHour {
    private LocalTime openTime;
    private LocalTime closeTime;
}
