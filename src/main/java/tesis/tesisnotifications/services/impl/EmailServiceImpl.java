package tesis.tesisnotifications.services.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tesis.tesisnotifications.dtos.EmailRequest;
import tesis.tesisnotifications.entities.NotificationLogEntity;
import tesis.tesisnotifications.repositories.NotificationLogRepository;
import tesis.tesisnotifications.services.EmailService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private NotificationLogRepository logRepository;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.from-name}")
    private String fromName;

    @Override
    public void sendEmail(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, fromName);
            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());

            String content;
            if (request.getTemplate() != null) {
                Context context = new Context();
                if (request.getVariables() != null) {
                    context.setVariables(request.getVariables());
                }
                content = templateEngine.process(request.getTemplate(), context);
            } else {
                content = request.getContent();
            }

            helper.setText(content, true);

            mailSender.send(message);

            // Log de la notificación
            logNotification(request.getTo(), request.getSubject(), "SENT", null);

            log.info("Email enviado exitosamente a: {}", request.getTo());

        } catch (Exception e) {
            log.error("Error enviando email a {}: {}", request.getTo(), e.getMessage());
            logNotification(request.getTo(), request.getSubject(), "FAILED", e.getMessage());
            throw new RuntimeException("Error enviando email", e);
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String userName) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", userName);
        variables.put("year", LocalDateTime.now().getYear());

        EmailRequest request = new EmailRequest();
        request.setTo(to);
        request.setSubject("¡Bienvenido a Mi Tienda Online!");
        request.setTemplate("welcome-user");
        request.setVariables(variables);
        request.setType("WELCOME");

        sendEmail(request);
    }

    @Override
    public void sendPurchaseConfirmation(String to, String orderCode, BigDecimal total) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderCode", orderCode);
        variables.put("total", total);
        variables.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        EmailRequest request = new EmailRequest();
        request.setTo(to);
        request.setSubject("Confirmación de compra - Orden " + orderCode);
        request.setTemplate("purchase-confirmation");
        request.setVariables(variables);
        request.setType("PURCHASE_CONFIRMATION");

        sendEmail(request);
    }

    @Override
    public void sendPaymentApproved(String to, String orderCode) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderCode", orderCode);
        variables.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        EmailRequest request = new EmailRequest();
        request.setTo(to);
        request.setSubject("¡Pago aprobado! - Orden " + orderCode);
        request.setTemplate("payment-approved");
        request.setVariables(variables);
        request.setType("PAYMENT_APPROVED");

        sendEmail(request);
    }

    @Override
    public void sendPaymentRejected(String to, String orderCode, String reason) {

    }

    private void logNotification(String to, String subject, String status, String error) {
        NotificationLogEntity log = new NotificationLogEntity();
        log.setRecipient(to);
        log.setSubject(subject);
        log.setStatus(status);
        log.setErrorMessage(error);
        log.setSentAt(LocalDateTime.now());
        logRepository.save(log);
    }
}