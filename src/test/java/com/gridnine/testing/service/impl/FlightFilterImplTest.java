package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.flight.Flight;
import com.gridnine.testing.model.flight.FlightTesting;
import com.gridnine.testing.model.segment.SegmentTesting;
import com.gridnine.testing.service.FlightFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FlightFilterImplTest {

    public static final int DOWN_TIME_LIMIT = 2;
    public static final LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);
    public static final List<Flight> CORRECT_FLIGHTS = List.of(
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)))),
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
                    new SegmentTesting(THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5)))));
    public static final List<Flight> FLIGHTS_DEPARTING_IN_PAST = List.of(
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW))));

    public static final List<Flight> FLIGHTS_DEPARTS_BEFORE_IT_ARRIVES = List.of(
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)))));

    public static final List<Flight> FLIGHTS_MORE_THAN_TWO_HOURS_GROUND_TIME = List.of(
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
                    new SegmentTesting(THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)))),
            new FlightTesting(List.of(
                    new SegmentTesting(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
                    new SegmentTesting(THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4)),
                    new SegmentTesting(THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7)))));

    public static List<Flight> FLIGHTS_ALL_TYPES = new ArrayList<>();

    static {
        FLIGHTS_ALL_TYPES.addAll(CORRECT_FLIGHTS);
        FLIGHTS_ALL_TYPES.addAll(FLIGHTS_DEPARTING_IN_PAST);
        FLIGHTS_ALL_TYPES.addAll(FLIGHTS_DEPARTS_BEFORE_IT_ARRIVES);
        FLIGHTS_ALL_TYPES.addAll(FLIGHTS_MORE_THAN_TWO_HOURS_GROUND_TIME);
    }

    private final FlightFilter out = new FlightFilterImpl();

    @Test
    public void shouldReturnEmptyListWhenFlightDeparted() {
        Assertions.assertEquals(0, out.filterDeparted(FLIGHTS_DEPARTING_IN_PAST).size());
    }

    @Test
    public void shouldReturnEmptyListWhenFlightDepartsBeforeItArrives() {
        Assertions.assertEquals(0, out.filterArrivalBeforeDeparture(FLIGHTS_DEPARTS_BEFORE_IT_ARRIVES).size());
    }

    @Test
    public void shouldReturnEmptyListWhenFlightMoreThanTwoHoursGroundTime() {
        Assertions.assertEquals(
                0,
                out.filterTotalDowntime(
                        FLIGHTS_MORE_THAN_TWO_HOURS_GROUND_TIME,
                        DOWN_TIME_LIMIT).size());
    }

    @Test
    public void shouldReturnCorrectListWhenFlightsCorrect() {
        Assertions.assertIterableEquals(CORRECT_FLIGHTS, out.filterDeparted(CORRECT_FLIGHTS));
        Assertions.assertIterableEquals(CORRECT_FLIGHTS, out.filterArrivalBeforeDeparture(CORRECT_FLIGHTS));
        Assertions.assertIterableEquals(CORRECT_FLIGHTS, out.filterTotalDowntime(CORRECT_FLIGHTS, DOWN_TIME_LIMIT));
    }

    @Test
    public void shouldReturnCorrectListWhenFlightsHasAllFilterTypes() {
        Assertions.assertIterableEquals(
                CORRECT_FLIGHTS,
                out.filterDeparted(
                        out.filterArrivalBeforeDeparture(
                                out.filterTotalDowntime(FLIGHTS_ALL_TYPES, DOWN_TIME_LIMIT))));
    }
}