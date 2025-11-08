package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UsernameService {

    private final UserRepository userRepository;

    private static final Set<String> RESERVED_USERNAMES = Set.of(
            "admin",  "support", "help"
    );

    private static final Pattern VALID_PATTERN = Pattern.compile("^[A-Za-z0-9_]{4,12}$");

    /**
     * Generates a valid and unique username based on displayName and email.
     */
    public String autoGenerateUsername(String displayName, String email) {
        if (displayName == null || email == null) {
            throw new IllegalArgumentException("Display name and email are required.");
        }

        // Base: combine parts of display name and email
        String base = buildBaseUsername(displayName, email);

        // Clean up and validate
        base = sanitize(base);

        // Ensure uniqueness
        return generateUniqueUsername(base);
    }

    private String buildBaseUsername(String displayName, String email) {
        String namePart = displayName.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String emailPart = email.substring(0, email.indexOf('@')).replaceAll("[^A-Za-z0-9]", "").toLowerCase();

        // Combine intelligently (like X)
        String base = namePart;
        if (base.length() < 4) base += emailPart;
        if (base.length() < 4) base += "user";

        // Trim to max 15
        return base.length() > 12 ? base.substring(0, 12) : base;
    }

    private String sanitize(String username) {
        username = username.replaceAll("[^A-Za-z0-9_]", "");
        if (username.length() < 4) username = username + "_user";
        if (username.length() > 12) username = username.substring(0, 12);
        return username;
    }

    private String generateUniqueUsername(String base) {
        String username = base;
        int counter = 1;
        Random random = new Random();

        while (RESERVED_USERNAMES.contains(username)
                || userRepository.existsByUsernameIgnoreCase(username)
        ) {
            String suffix = String.valueOf(random.nextInt(9999));
            username = base + suffix;
            if (username.length() > 12) {
                username = username.substring(0, 12);
            }
            counter++;
            if (counter > 25) throw new RuntimeException("Unable to generate a unique username");
        }
        return "@"+ username;
    }
}
