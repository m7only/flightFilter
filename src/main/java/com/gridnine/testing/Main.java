package com.gridnine.testing;

import com.gridnine.testing.builder.impl.FlightBuilderTesting;
import com.gridnine.testing.model.flight.Flight;
import com.gridnine.testing.service.FlightFilter;
import com.gridnine.testing.service.impl.FlightFilterImpl;

import java.util.List;

public class Main {

    public static final int DOWNTIME_LIMIT = 2;

    public static void main(String[] args) {
        List<Flight> flights = new FlightBuilderTesting().createFlights();
        FlightFilter filter = new FlightFilterImpl();

        System.out.println(flights);
        System.out.println(filter.filterDeparted(flights));
        System.out.println(filter.filterArrivalBeforeDeparture(flights));
        System.out.println(filter.filterTotalDowntime(flights, DOWNTIME_LIMIT));
    }
}