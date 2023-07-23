package com.gridnine.testing.service;

import com.gridnine.testing.model.flight.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> filterDeparted(List<Flight> flights);

    List<Flight> filterArrivalBeforeDeparture(List<Flight> flights);

    List<Flight> filterTotalDowntime(List<Flight> flights, int totalDowntime);
}
