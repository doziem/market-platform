package com.doziem.market_platform.payload.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WorkHourDto {
    private LocalTime openTime;

    private LocalTime closeTime;
}
