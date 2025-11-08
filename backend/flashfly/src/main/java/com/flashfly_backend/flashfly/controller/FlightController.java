package com.flashfly_backend.flashfly.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import com.flashfly_backend.flashfly.service.AmadeusService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    private final AmadeusService amadeusService;

    public FlightController(AmadeusService amadeusService) {
        this.amadeusService = amadeusService;
    }

    @GetMapping("/destinations")
    public FlightDestination[] getFlightDestinations(@RequestParam String origin, @RequestParam int maxPrice) throws Exception {
        return amadeusService.getFlightDestinations(origin, maxPrice);
    }

    @GetMapping("/offers")
    public FlightOfferSearch[] getFlightOffers(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureDate,
            @RequestParam String returnDate,
            @RequestParam int adults,
            @RequestParam int max
    ) throws ResponseException {
        return amadeusService.searchFlightOffers(origin, destination, departureDate, returnDate, adults, max);
    }
}
