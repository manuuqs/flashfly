package com.flashfly_backend.flashfly.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmadeusService {

    private final Amadeus amadeus;

    public AmadeusService(
            @Value("${amadeus.api.key}") String apiKey,
            @Value("${amadeus.api.secret}") String apiSecret
    ) {
        System.out.println("API KEY: " + apiKey);
        System.out.println("API SECRET: " + apiSecret);
        this.amadeus = Amadeus.builder(
                apiKey, apiSecret)
                .build();
    }

    public FlightDestination[] getFlightDestinations(String origin, int maxPrice) throws Exception {
        try {
            return amadeus.shopping.flightDestinations.get(
                Params.with("origin", origin).and("maxPrice", maxPrice)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FlightOfferSearch[] searchFlightOffers(String origin, String destination, String departureDate, String returnDate, int adults, int max) throws ResponseException, ResponseException {
        try {
            return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", origin)
                      .and("destinationLocationCode", destination)
                      .and("departureDate", departureDate)
                      .and("returnDate", returnDate)
                      .and("adults", adults)
                      .and("max", max)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
