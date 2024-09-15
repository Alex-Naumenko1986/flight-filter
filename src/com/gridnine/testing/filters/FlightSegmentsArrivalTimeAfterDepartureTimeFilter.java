package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

public class FlightSegmentsArrivalTimeAfterDepartureTimeFilter implements Filter {
    @Override
    public boolean test(Flight flight) {
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
