package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.enums.NotificationType;
import com.doziem.market_platform.model.staff.Department;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@Data
public class ProductNotification {

    private String productId;
    private String productName;
    private String message;
    private NotificationType type;
    private Integer currentStock;
    private Integer requestedQuantity;
    private ZonedDateTime timestamp;
    private String severity;
    private Department department;;



    // Constructors
    public ProductNotification() {
        this.timestamp = ZonedDateTime.now();
    }

    public ProductNotification(String  productId, String productName, String message,  NotificationType type, Integer currentStock) {
        this.productId = productId;
        this.productName = productName;
        this.message = message;
        this.type = type;
        this.currentStock = currentStock;
        this.timestamp = ZonedDateTime.now();
        this.severity = determineSeverity(type, currentStock);
    }

    private String determineSeverity(NotificationType type, Integer stock) {
        if (type == NotificationType.STOCK_DEPLETED || type == NotificationType.NULL_STOCK) {
            return "CRITICAL";
        } else if (type == NotificationType.INSUFFICIENT_STOCK) {
            return "HIGH";
        } else if (stock != null && stock < 10) {
            return "MEDIUM";
        }
        return "LOW";
    }
}
