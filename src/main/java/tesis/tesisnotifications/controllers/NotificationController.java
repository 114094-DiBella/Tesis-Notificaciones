package tesis.tesisnotifications.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tesis.tesisnotifications.dtos.EmailRequest;
import tesis.tesisnotifications.services.EmailService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Email enviado exitosamente"
            ));
        } catch (Exception e) {
            log.error("Error enviando email: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "error", "Error enviando email: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/welcome")
    public ResponseEntity<?> sendWelcomeEmail(@RequestParam String email,
                                              @RequestParam String userName) {
        try {
            emailService.sendWelcomeEmail(email, userName);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PostMapping("/purchase-confirmation")
    public ResponseEntity<?> sendPurchaseConfirmation(@RequestParam String email,
                                                      @RequestParam String orderCode,
                                                      @RequestParam BigDecimal total) {
        try {
            emailService.sendPurchaseConfirmation(email, orderCode, total);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PostMapping("/payment-approved")
    public ResponseEntity<?> sendPaymentApproved(@RequestParam String email,
                                                 @RequestParam String orderCode) {
        try {
            emailService.sendPaymentApproved(email, orderCode);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
