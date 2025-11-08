package com.flashfly_backend.flashfly.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightDestination;
import org.springframework.stereotype.Service;

@Service
public class AmadeusService {

    private final Amadeus amadeus;

    public AmadeusService() {
        this.amadeus = Amadeus.builder(
                "YOUR_API_KEY", "YOUR_API_SECRET")
                .build();
    }

    public FlightDestination[] getFlightDestinations(String origin, int maxPrice) throws Exception {
        return amadeus.shopping.flightDestinations.get(
                Params.with("origin", origin)
                        .and("maxPrice", maxPrice)
        );
    }


}
