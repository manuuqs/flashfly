package com.flashfly_backend.flashfly.controller;

import com.flashfly_backend.flashfly.dtos.LoginRequest;
import com.flashfly_backend.flashfly.dtos.RegisterRequest;
import com.flashfly_backend.flashfly.dtos.User;
import com.flashfly_backend.flashfly.repository.UserRepository;
import com.flashfly_backend.flashfly.service.AuthService;
import com.flashfly_backend.flashfly.service.JwtService;
import jakarta.validation.Valid;
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
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }


    // 🔹 Registro tradicional
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.registerUser(request.getName(), request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", createUserResponse(user)
        ));
    }



    // 🔹 Login tradicional
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.loginUser(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user); // lo añadiremos en el paso 2
        return ResponseEntity.ok(Map.of("token", token, "user", createUserResponse(user)));
    }


    // 🔹 Login con Google
    @PostMapping("/google")
    public ResponseEntity<?> googleAuth(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            User user = authService.createOrUpdateGoogleUser(token);

            String jwt = jwtService.generateToken(user);
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "user", createUserResponse(user)
            ));


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
