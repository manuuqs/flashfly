package com.flashfly_backend.flashfly.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DestinoService {

    @Autowired
    private RestTemplate restTemplate;

    public String obtenerVuelos(String origen, String destino) {
        String url = "https://api-vuelos.com/vuelos?origen=" + origen + "&destino=" + destino;
        // Aquí deberías mapear la respuesta a un DTO, este es solo un ejemplo simple
        return restTemplate.getForObject(url, String.class);
    }


}
