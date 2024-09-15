package com.gridnine.testing.builder;

import com.gridnine.testing.filters.Filter;

import java.util.ArrayList;
import java.util.List;

public class FilterBuilder {
    List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void clear() {
        filters = new ArrayList<>();
    }
}
