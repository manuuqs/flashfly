package com.flashfly_backend.flashfly.controller;

import com.flashfly_backend.flashfly.dtos.User;
import com.flashfly_backend.flashfly.service.GoogleAuthService;
import com.flashfly_backend.flashfly.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth/google")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde tu frontend
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;
    private final UserService userService;

    @Autowired
    public GoogleAuthController(GoogleAuthService googleAuthService, UserService userService) {
        this.googleAuthService = googleAuthService;
        this.userService = userService;
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

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            // 🔹 Verificar si el usuario ya existe
            User user = userService.createOrUpdateGoogleUser(
                    payload.getEmail(),
                    payload.get("name").toString(),
                    payload.get("picture").toString()
            );

            // Aquí podrías generar un JWT si quieres
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("picture", user.getPicture());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}