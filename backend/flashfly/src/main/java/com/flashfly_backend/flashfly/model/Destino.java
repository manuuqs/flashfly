package com.flashfly_backend.flashfly.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destino {

    private Long id;
    private String ciudad;
    private String pais;
    private String codigoIATA;
}
