package com.flashfly_backend.flashfly.controller;

import com.flashfly_backend.flashfly.dtos.User;
import com.flashfly_backend.flashfly.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde tu frontend
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔹 Registro tradicional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String password = request.get("password");

            User user = authService.registerUser(name, email, password);

            return ResponseEntity.ok(createUserResponse(user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 Login tradicional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            User user = authService.loginUser(email, password);

            return ResponseEntity.ok(createUserResponse(user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 Login con Google
    @PostMapping("/google")
    public ResponseEntity<?> googleAuth(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            User user = authService.createOrUpdateGoogleUser(token);

            return ResponseEntity.ok(createUserResponse(user));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.forgotPassword(body.get("email")));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        authService.resetPassword(body.get("token"), body.get("password"));
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    // 🔹 Método auxiliar para crear respuesta
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("picture", user.getPicture());
        response.put("authProvider", user.getAuthProvider());
        return response;
    }
}
