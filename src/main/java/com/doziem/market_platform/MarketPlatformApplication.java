package com.doziem.market_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@PropertySource("classpath:env.properties")
@EnableJpaRepositories(basePackages = "com.doziem.market_platform.repository")
@EntityScan(basePackages = "com.doziem.market_platform.model")
@EnableRetry
@EnableAsync
public class MarketPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlatformApplication.class, args);
	}

}
