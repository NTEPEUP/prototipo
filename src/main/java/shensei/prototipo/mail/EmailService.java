package shensei.prototipo.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${mailtrap.api.token}")
    private String apiToken;

    @Value("${mailtrap.api.url}")
    private String apiUrl;

    @Value("${app.mail.from}")
    private String from;

    private final RestTemplate restTemplate = new RestTemplate();

    // 📩 CORREO SIMPLE
    public void sendSimpleMessage(String to, String subject, String text) {
        sendEmail(to, subject, text, false);
    }

    // 📩 CORREO HTML
    public void sendHtmlMessage(String to, String subject, String html) {
        sendEmail(to, subject, html, true);
    }

    // 🔥 MÉTODO CENTRAL
    private void sendEmail(String to, String subject, String content, boolean isHtml) {

        Map<String, Object> request = new HashMap<>();

        // FROM
        request.put("from", Map.of(
                "email", from,
                "name", "Shensei Prototipo"
        ));

        // TO
        request.put("to", new Object[]{
                Map.of("email", to)
        });

        request.put("subject", subject);

        if (isHtml) {
            request.put("html", content);
        } else {
            request.put("text", content);
        }

        // HEADERS
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        // REQUEST
        ResponseEntity<String> response =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error enviando correo: " + response.getBody());
        }
    }
}