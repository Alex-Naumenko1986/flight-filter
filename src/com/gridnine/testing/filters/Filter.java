package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public interface Filter extends Predicate<Flight> {
}
