package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.flight.Flight;
import com.gridnine.testing.model.segment.Segment;
import com.gridnine.testing.service.FlightFilter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to filter list of flights
 */
public class FlightFilterImpl implements FlightFilter {

    /**
     * Filter by departed flights
     *
     * @param flights List of flights
     * @return Filtered list of flights
     */
    @Override
    public List<Flight> filterDeparted(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(segment -> segment
                                .getDepartureDate()
                                .isAfter(LocalDateTime.now())
                        ))
                .collect(Collectors.toList());
    }

    /**
     * Filter by arrival before departure
     *
     * @param flights List of flights
     * @return Filtered list of flights
     */
    @Override
    public List<Flight> filterArrivalBeforeDeparture(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(segment -> segment
                                .getArrivalDate()
                                .isAfter(segment.getDepartureDate())
                        ))
                .collect(Collectors.toList());
    }

    /**
     * Filter flights by total downtime
     *
     * @param flights       List of flights
     * @param limitDowntime Downtime, hours
     * @return Filtered list of flights
     */
    @Override
    public List<Flight> filterTotalDowntime(List<Flight> flights, int limitDowntime) {
        return flights.stream()
                .filter(flight -> {
                    LocalDateTime previousArrival = null;
                    long totalDowntime = 0;
                    for (Segment segment : flight.getSegments()) {
                        if (previousArrival != null &&
                                (totalDowntime +=
                                        Duration.between(
                                                        previousArrival,
                                                        segment.getDepartureDate())
                                                .toHours()) > limitDowntime) {
                            return false;
                        }
                        previousArrival = segment.getArrivalDate();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
