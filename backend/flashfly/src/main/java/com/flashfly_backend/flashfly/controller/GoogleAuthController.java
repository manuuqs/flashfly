package com.flashfly_backend.flashfly.controller;

import com.flashfly_backend.flashfly.service.GoogleAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth/google")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde tu frontend
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @Autowired
    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    /**
     * Endpoint para verificar el token de Google enviado desde el frontend
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyGoogleToken(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token"); // el token que envía el frontend
            GoogleIdToken.Payload payload = googleAuthService.verify(token);

            if (payload == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token inválido"));
            }

            // Datos básicos del usuario extraídos del payload
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", payload.getEmail());
            userData.put("name", payload.get("name"));
            userData.put("picture", payload.get("picture"));

            return ResponseEntity.ok(userData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}