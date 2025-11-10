package com.flashfly_backend.flashfly.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashfly_backend.flashfly.service.AmadeusService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    private final AmadeusService amadeusService;
    private final Gson gson = new Gson();

    public FlightController(AmadeusService amadeusService) {
        this.amadeusService = amadeusService;
    }


    @GetMapping("/destinations")
    @ResponseBody
    public List<Map<String, Object>> getFlightDestinations(
            @RequestParam String origin,
            @RequestParam int maxPrice
    ) throws Exception {
        FlightDestination[] results = amadeusService.getFlightDestinations(origin, maxPrice);
        List<Map<String, Object>> list = new ArrayList<>();
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

        for (FlightDestination fd : results) {
            // Convertir JsonObject interno a Map
            Map<String, Object> map = gson.fromJson(fd.getResponse().getResult(), mapType);
            list.add(map);
        }
        return list;
    }
    @GetMapping("/offers")
    public List<Map<String, Object>> getFlightOffers(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureDate,
            @RequestParam String returnDate,
            @RequestParam int adults,
            @RequestParam int max
    ) throws ResponseException {
        FlightOfferSearch[] results = amadeusService.searchFlightOffers(origin, destination, departureDate, returnDate, adults, max);
        List<Map<String, Object>> list = new ArrayList<>();
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

        for (FlightOfferSearch fd : results) {
            // Convertir JsonObject interno a Map
            Map<String, Object> map = gson.fromJson(fd.getResponse().getResult(), mapType);
            list.add(map);
        }
        return list;
    }
}
