package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.time.Duration;
import java.time.LocalDateTime;

public class LessOrEqualTwoHoursOnGroundFilter implements Filter {
    @Override
    public boolean test(Flight flight) {
        var segments = flight.getSegments();
        int groundTimeMinutesSum = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
            var departureTime = segments.get(i + 1).getDepartureDate();
            long groundTimeMinutes = Duration.between(arrivalTime, departureTime).toMinutes();
            groundTimeMinutesSum += (int) groundTimeMinutes;
        }
        return groundTimeMinutesSum <= 120;
    }
}
