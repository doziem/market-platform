package com.doziem.market_platform.configuration;

import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
public class JwtConstant {
    static String JWT_SECRET="njdkfhuhbvfhuglerjufhbfdsjcvygeukhrfvhbadsktyckfaevfcewygdewxlmlkdcNVKRSJTNGFSAWQYGQREGTSJQfABXVCXVKSFVSDNLKJBADCVJNDCASFV";
    static String JWT_HEADER="Authorization";
}
