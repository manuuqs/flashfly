package com.flashfly_backend.flashfly.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightDestination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmadeusService {

    private final Amadeus amadeus;

    public AmadeusService(
            @Value("${amadeus.api.key}") String apiKey,
            @Value("${amadeus.api.secret}") String apiSecret
    ) {
        this.amadeus = Amadeus.builder(
                apiKey, apiSecret)
                .build();
    }

    public FlightDestination[] getFlightDestinations(String origin, int maxPrice) throws Exception {
        return amadeus.shopping.flightDestinations.get(
                Params.with("origin", origin)
                        .and("maxPrice", maxPrice)
        );
    }


}
