package com.gridnine.testing.model.segment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bean that represents a flight segment.
 */
public class SegmentTesting extends Segment {

    public SegmentTesting(final LocalDateTime dep, final LocalDateTime arr) {
        super(dep, arr);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + super.getDepartureDate().format(fmt) + '|' + super.getArrivalDate().format(fmt)
                + ']';
    }
}