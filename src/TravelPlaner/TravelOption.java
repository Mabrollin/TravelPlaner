/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARBRO02 Mostly a data holder for ETA for a trip and a String
 * representation of the trip
 */
public class TravelOption {

    private final List<Route.Trip> journey = new ArrayList<>();
    private final Stop origin;
    private final LocalTime departureTime;

    TravelOption(Stop origin, LocalTime departureTime) {
        this.origin = origin;
        this.departureTime = departureTime;

    }

    TravelOption(TravelOption other) {
        this(other.origin, other.departureTime);
        this.journey.addAll(other.journey);
    }

    public Stop getReachedStop() {
        return journey.isEmpty() ? null : journey.get(journey.size() - 1).getStop();
    }

    public LocalTime getETA() {
        return journey.isEmpty() ? departureTime : journey.get(journey.size() - 1).getArrival();
    }

    void travel(Route.Trip trip) {
        journey.add(trip);
    }

    @Override
    public String toString() {
        String br = "\n";
        Stop destination = journey.isEmpty() ? origin : journey.get(journey.size() - 1).getStop();
        StringBuilder sb = new StringBuilder();
        sb.append("From: ").append(origin).append(" ").append(departureTime).append(" ");
        sb.append("To ").append(destination).append(" ").append(getETA());
        sb.append(br);
        journey.forEach(trip -> sb.append(br).append(trip));

        return sb.toString();
    }

}
