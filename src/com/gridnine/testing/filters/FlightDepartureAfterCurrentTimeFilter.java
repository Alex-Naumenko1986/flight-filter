package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;

public class FlightDepartureAfterCurrentTimeFilter implements Filter {
    @Override
    public boolean test(Flight flight) {
        var firstSegment = flight.getSegments().getFirst();
        return !firstSegment.getDepartureDate().isBefore(LocalDateTime.now());
    }
}
