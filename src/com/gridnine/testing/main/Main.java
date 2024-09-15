package com.gridnine.testing.main;

import com.gridnine.testing.builder.FilterBuilder;
import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.engine.FilterEngine;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.filters.LessOrEqualTwoHoursOnGroundFilter;
import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static List<Flight> flights;
    private static FilterBuilder filterBuilder;

    public static void main(String[] args) {
        flights = FlightBuilder.createFlights();
        filterBuilder = new FilterBuilder();

        System.out.println("Unfiltered flights:");
        System.out.println(flights);
        System.out.println();

        System.out.println("Filter out flights that have departure before current time:");
        addFilterAndPrint(flight ->
        {
            var firstSegment = flight.getSegments().getFirst();
            return !firstSegment.getDepartureDate().isBefore(LocalDateTime.now());
        });

        System.out.println("Add filter on flights that have segments with arrival date before departure date:");
        addFilterAndPrint(
                flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()))
        );

        System.out.println("Add filter on flights, that have time spent on the ground more than two hours:");
        addFilterAndPrint(new LessOrEqualTwoHoursOnGroundFilter());
    }

    private static void addFilterAndPrint(Filter filter) {
        filterBuilder.addFilter(filter);
        var filteredOutFlights = FilterEngine.filter(flights, filterBuilder.getFilters());
        System.out.println(filteredOutFlights);
        System.out.println();
    }
}
