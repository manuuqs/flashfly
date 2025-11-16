package com.flashfly_backend.flashfly.service;

import com.flashfly_backend.flashfly.dtos.User;
import com.flashfly_backend.flashfly.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthService googleAuthService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       GoogleAuthService googleAuthService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.googleAuthService = googleAuthService;
    }

    // 🔹 Registro tradicional
    public User registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthProvider("LOCAL");

        return userRepository.save(user);
    }

    // 🔹 Login tradicional
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que el usuario no sea de Google
        if ("GOOGLE".equals(user.getAuthProvider())) {
            throw new RuntimeException("Este email está registrado con Google. Usa Google Sign-In.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    // 🔹 Autenticación con Google (usa tu GoogleAuthService existente)
    public User createOrUpdateGoogleUser(String token) throws Exception {
        GoogleIdToken.Payload payload = googleAuthService.verify(token);

        if (payload == null) {
            throw new RuntimeException("Token de Google inválido");
        }

        String email = payload.getEmail();
        String name = payload.get("name").toString();
        String picture = payload.get("picture").toString();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(name);
            existingUser.setPicture(picture);
            return userRepository.save(existingUser);
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPicture(picture);
            newUser.setAuthProvider("GOOGLE");
            return userRepository.save(newUser);
        }
    }


    // 🔹 Verificar si email existe
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



}
