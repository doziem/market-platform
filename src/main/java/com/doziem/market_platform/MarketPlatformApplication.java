package com.doziem.market_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class MarketPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlatformApplication.class, args);
	}

}
