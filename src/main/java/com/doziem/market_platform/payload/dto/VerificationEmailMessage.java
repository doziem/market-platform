package com.doziem.market_platform.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEmailMessage {
    private String email;
    private String displayName;
    private String verificationLink;
}
