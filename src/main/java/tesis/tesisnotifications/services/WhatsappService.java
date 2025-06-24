package tesis.tesisnotifications.services;

import org.springframework.stereotype.Service;
import tesis.tesisnotifications.dtos.WhatsAppRequest;

import java.math.BigDecimal;

@Service
public interface WhatsappService {
    void sendMessage(WhatsAppRequest request);
    void sendTemplateMessage(String to, String templateName, Object... parameters);
    void sendWelcomeMessage(String phoneNumber, String userName);
    void sendPurchaseConfirmation(String phoneNumber, String orderCode, BigDecimal total);
    void sendPaymentApproved(String phoneNumber, String orderCode);
    void sendPromotionalMessage(String phoneNumber, String promotionTitle, String discount);
    void sendBulkPromotionalMessage(String templateName, Object... parameters);
}
