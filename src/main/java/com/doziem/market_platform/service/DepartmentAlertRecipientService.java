package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.ProductNotification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentAlertRecipientService {

    public List<String> resolveRecipients(ProductNotification notification) {

        return switch (notification.getDepartment().getDepartmentName()) {
            case "WAREHOUSE" -> List.of("warehouse@marketplatform.com");
            case "PROCUREMENT" -> List.of("procurement@marketplatform.com");
            case "HUMAN RESOURCES" -> List.of("humanresources@marketplatform.com");
            case "FINANCE" -> List.of("finance@marketplatform.com");
            default -> List.of("admin@marketplatform.com");
        };
    }
}
