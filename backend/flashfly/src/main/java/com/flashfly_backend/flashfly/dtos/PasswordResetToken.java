package com.flashfly_backend.flashfly.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String id;
    private String token;
    @DBRef
    private User user;
    private LocalDateTime expiration;

    private LocalDateTime createdAt = LocalDateTime.now();

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiration = LocalDateTime.now().plusMinutes(30);
    }

}
