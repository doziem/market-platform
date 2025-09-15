package com.doziem.market_platform.configuration;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstant {
    @Value("${jwt.secret}")
    public static  String JWT_SECRET;

    @Value("${jwt.header}")
    public static String JWT_HEADER;
}
