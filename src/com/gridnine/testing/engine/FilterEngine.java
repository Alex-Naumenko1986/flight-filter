package com.gridnine.testing.engine;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class FilterEngine {

    public static List<Flight> filter(List<Flight> flights, List<Filter> filters) {
        if (flights == null) {
            throw new IllegalArgumentException("List of flights should not be null");
        }

        if (filters == null) {
            throw new IllegalArgumentException("List of filters should not be null");
        }

        List<Flight> result = new ArrayList<>(flights);

        for (var filter : filters) {
            result = result.parallelStream().filter(filter).toList();
        }
        return result;
    }

    public static List<Flight> filter(List<Flight> flights, List<Filter> filters, int offset, int size) {
        var result = filter(flights, filters);

        return result.stream().skip(offset).limit(size).toList();
    }
}
