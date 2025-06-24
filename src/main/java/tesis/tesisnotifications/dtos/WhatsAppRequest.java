package tesis.tesisnotifications.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppRequest {
    @NotBlank
    private String to; // Numero de teléfono en formato internacional (+54911...)

    private String messageType; // "text", "template", "media"

    // Para mensajes de texto simples
    private String text;

    // Para mensajes con template
    private String templateName;
    private String templateLanguage; // "es" o "es_AR"
    private List<String> templateParameters;

    // Para mensajes con media
    private String mediaUrl;
    private String mediaType; // "image", "document", "video"
    private String caption;

    // Metadatos
    private String businessPhoneNumberId;
    private Map<String, Object> metadata;
}