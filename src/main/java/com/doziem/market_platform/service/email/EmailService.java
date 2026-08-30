package com.doziem.market_platform.service.email;

import com.doziem.market_platform.payload.dto.ProductNotification;

public interface EmailService {

    void sendUrgentAlert(ProductNotification notification);

//    void sendHighPriorityAlert(ProductNotification notification);
}
