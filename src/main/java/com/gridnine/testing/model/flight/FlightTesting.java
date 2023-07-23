package com.gridnine.testing.model.flight;

import com.gridnine.testing.model.segment.Segment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
public class FlightTesting extends Flight {

    public FlightTesting(final List<Segment> segs) {
        super(segs);
    }

    @Override
    public String toString() {
        return super.getSegments().stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}