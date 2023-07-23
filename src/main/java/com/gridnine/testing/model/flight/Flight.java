package com.gridnine.testing.model.flight;

import com.gridnine.testing.model.segment.Segment;

import java.util.List;

/**
 * Bean that generalizing flights
 */
public abstract class Flight {
    private final List<Segment> segment;

    public Flight(final List<Segment> segment) {
        this.segment = segment;
    }

    public List<Segment> getSegments() {
        return segment;
    }
}
