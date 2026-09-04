package com.doziem.market_platform.service.email;

import com.doziem.market_platform.payload.dto.ProductNotification;
import com.doziem.market_platform.model.User;

public interface EmailService {

    void sendUrgentAlert(ProductNotification notification);

    void sendVerificationEmail(User user, String verificationLink);

//    void sendHighPriorityAlert(ProductNotification notification);
}
