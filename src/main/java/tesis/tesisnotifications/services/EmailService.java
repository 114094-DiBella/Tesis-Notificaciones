package tesis.tesisnotifications.services;

import org.springframework.stereotype.Service;
import tesis.tesisnotifications.dtos.EmailRequest;

import java.math.BigDecimal;

@Service
public interface EmailService {
    void sendEmail(EmailRequest request);
    void sendWelcomeEmail(String to, String userName);
    void sendPurchaseConfirmation(String to, String orderCode, BigDecimal total);
    void sendPaymentApproved(String to, String orderCode);
    void sendPaymentRejected(String to, String orderCode, String reason);
}