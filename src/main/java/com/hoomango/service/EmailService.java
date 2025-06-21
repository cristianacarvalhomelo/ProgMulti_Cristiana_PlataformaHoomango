package com.hoomango.service;

import jakarta.ejb.Stateless;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Stateless
public class EmailService {

    private static final String SENDGRID_API_KEY = "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";

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        try {
            URL url = new URL("https://api.sendgrid.com/v3/mail/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + SENDGRID_API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = """
            {
              "personalizations": [
                {
                  "to": [
                    { "email": "%s" }
                  ],
                  "subject": "%s"
                }
              ],
              "from": {
                "email": "cristianammelo1999@gmail.com"
              },
              "content": [
                {
                  "type": "text/plain",
                  "value": "%s"
                }
              ]
            }
            """.formatted(destinatario, assunto, mensagem);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 202) {
                System.out.println("✅ E-mail enviado com sucesso!");
            } else {
                System.out.println("❌ Erro ao enviar e-mail. Código: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
