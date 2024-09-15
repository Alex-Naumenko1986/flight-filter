package com.gridnine.testing;

import com.gridnine.testing.builder.FilterBuilder;
import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.engine.FilterEngine;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.filters.FlightDepartureAfterCurrentTimeFilter;
import com.gridnine.testing.filters.FlightSegmentsArrivalTimeAfterDepartureTimeFilter;
import com.gridnine.testing.filters.LessOrEqualTwoHoursOnGroundFilter;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FilterEngineTest {
    private List<Flight> flights;
    private final FilterBuilder filterBuilder = new FilterBuilder();

    @BeforeEach
    public void beforeEach() {
        flights = FlightBuilder.createFlights();
        filterBuilder.clear();
    }

    @Test
    public void filteringWithNoFiltersShouldReturnOriginalFlightList() {
        List<Filter> filters = new ArrayList<>();

        List<Flight> result = FilterEngine.filter(flights, filters);

        Assertions.assertEquals(flights, result, "Filter result after filtering with" +
                " no filters is incorrect");
    }

    @Test
    public void filteringWithNullFlightsShouldThrowException() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> FilterEngine.filter(null, List.of()), "Exception has not been thrown");

        Assertions.assertEquals("List of flights should not be null", exception.getMessage(),
                "Exception messages are not equal");
    }

    @Test
    public void filteringWithNullFiltersShouldThrowException() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> FilterEngine.filter(List.of(), null), "Exception has not been thrown");

        Assertions.assertEquals("List of filters should not be null", exception.getMessage(),
                "Exception messages are not equal");
    }

    @Test
    public void filteringWithOneFilterShouldReturnCorrectResult() {
        filterBuilder.addFilter(new FlightDepartureAfterCurrentTimeFilter());

        List<Flight> result = FilterEngine.filter(flights, filterBuilder.getFilters());

        List<Flight> expected = List.of(flights.get(0), flights.get(1), flights.get(3), flights.get(4), flights.get(5));

        Assertions.assertEquals(expected, result, "Result list is not equal to expected list");
    }

    @Test
    public void filteringWithTwoFiltersShouldReturnCorrectResult() {
        filterBuilder.addFilter(new FlightDepartureAfterCurrentTimeFilter());
        filterBuilder.addFilter(new FlightSegmentsArrivalTimeAfterDepartureTimeFilter());

        List<Flight> result = FilterEngine.filter(flights, filterBuilder.getFilters());

        List<Flight> expected = List.of(flights.get(0), flights.get(1), flights.get(4), flights.get(5));

        Assertions.assertEquals(expected, result, "Result list is not equal to expected list");
    }

    @Test
    public void filteringWithThreeFiltersShouldReturnCorrectResult() {
        filterBuilder.addFilter(new FlightDepartureAfterCurrentTimeFilter());
        filterBuilder.addFilter(new FlightSegmentsArrivalTimeAfterDepartureTimeFilter());
        filterBuilder.addFilter(new LessOrEqualTwoHoursOnGroundFilter());

        List<Flight> result = FilterEngine.filter(flights, filterBuilder.getFilters());

        List<Flight> expected = List.of(flights.get(0), flights.get(1));

        Assertions.assertEquals(expected, result, "Result list is not equal to expected list");
    }

    @Test
    public void filteringWithOffsetShouldReturnCorrectResult() {
        filterBuilder.addFilter(new FlightDepartureAfterCurrentTimeFilter());

        List<Flight> result = FilterEngine.filter(flights, filterBuilder.getFilters(), 2, 10);

        List<Flight> expected = List.of(flights.get(3), flights.get(4), flights.get(5));

        Assertions.assertEquals(expected, result, "Result list is not equal to expected list");
    }

    @Test
    public void filteringWithSizeShouldReturnCorrectResult() {
        filterBuilder.addFilter(new FlightDepartureAfterCurrentTimeFilter());

        List<Flight> result = FilterEngine.filter(flights, filterBuilder.getFilters(), 0, 3);

        List<Flight> expected = List.of(flights.get(0), flights.get(1), flights.get(3));

        Assertions.assertEquals(expected, result, "Result list is not equal to expected list");
    }
}
