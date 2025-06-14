package tesis.tesisnotifications.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @NotBlank
    private String to;

    @NotBlank
    private String subject;

    private String template; // nombre del template
    private Map<String, Object> variables; // variables para el template
    private String content; // contenido directo (opcional)
    private String type; // WELCOME, PURCHASE_CONFIRMATION, PAYMENT_APPROVED, etc.
}