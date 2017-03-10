/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARBRO02 Works like a one-way vertex between Stops (as they were
 * considered Nodes)
 */
public class Route {

    private final String routeName;
    private final List<Trip> trips = new ArrayList<>();
    private final Stop stop;

    public Route(Stop stop, String routeName) {
        this.stop = stop;
        this.routeName = routeName;
    }

    @Override
    public String toString() {
        return routeName + ", to: " + stop;
    }

    /**
     * Trip class data holder for departures from start Stop and arrivals at
     * destination Stop
     */
    class Trip {

        private final LocalTime departure;
        private final LocalTime arrival;
        private final String routeName;

        @Override
        public String toString() {
            return routeName + " | " + getDeparture() + " --> " + getArrival();
        }

        private Trip(LocalTime departure, LocalTime arrival, String routeName) {
            this.departure = departure;
            this.arrival = arrival;
            this.routeName = routeName;
        }

        public Stop getStop() {
            return stop;
        }

        boolean hasDeparted(LocalTime time) {
            return departure.isBefore(time);
        }

        LocalTime getArrival() {
            return arrival;
        }

        LocalTime getDeparture() {
            return departure;
        }

        private boolean departsAfter(Trip other) {
            return departure.isAfter(other.departure);
        }

        private boolean arrivesAfter(Trip other) {
            return arrival.isAfter(other.arrival);
        }

    }

    public Stop getStop() {
        return stop;
    }

    /**
     *
     * @param trip the only addTrip method that adds an element into trips, is
     * used by all pulbic getTrip in the end. Also removes "worthless" trips;
     * trips that both departes earlier and arrives later than another trip
     */
    private void addTrip(Trip trip) {
        if (trips.isEmpty()) {
            trips.add(trip);
            return;
        }
        int index = 0;
        //find the sorted index
        while (index < trips.size() && trip.departsAfter(trips.get(index))) {
            index++;
        }
        if (index == trips.size()) {
            trips.add(trip);

        } //no need to add if the trip after would arrive earlier 
        else if (trips.get(index).arrivesAfter(trip)) {
            trips.add(index, trip);

        } else {
            //Trip is useless
            return;
        }

        // remove all the earlier trips if they arrive later
        while (--index >= 0 && trips.get(index).arrivesAfter(trip)) {
            trips.remove(index);

        }
        for (int i = 0; i < trips.size(); i++) {

            if (trip.departsAfter(trips.get(i))) {
                //new trip is last trip

                return;
            }
        }
    }

    public void addTrip(LocalTime departure, LocalTime arrival) {

        addTrip(new Trip(departure, arrival, this.toString()));
    }

    /**
     *
     * @param times Array of departures and arrivals. All even indexes are
     * departures and the following odd index is the coresponding departure.
     * @throws IllegalArgumentException if argument array is not of even size
     */
    public void addTrips(LocalTime... times) {
        if (times.length % 2 != 0) {
            throw new IllegalArgumentException("Expected array with even length");
        }
        for (int i = 0; i < times.length; i += 2) {
            addTrip(times[i], times[i + 1]);
        }
    }

    /**
     *
     * @param formatter formatter for parsing string to time
     * @param timeStrings Array of departures and arrivals. All even indexes are
     * departures and the following odd index is the coresponding departure.
     * @throws IllegalArgumentException if argument array is not of even size
     */
    public void addTrips(DateTimeFormatter formatter, String... timeStrings) {
        if (timeStrings.length % 2 != 0) {
            throw new IllegalArgumentException("Expected array with even length");
        }
        LocalTime[] times = new LocalTime[timeStrings.length];
        for (int i = 0; i < times.length; i++) {
            times[i] = LocalTime.parse(timeStrings[i], formatter);
        }
        addTrips(times);
    }

    /**
     *
     * @param timeStrings Array of departures and arrivals. All even indexes are
     * departures and the following odd index is the coresponding departure.
     * @throws IllegalArgumentException if argument array is not of even size
     */
    public void addTrips(String... timeStrings) {
        addTrips(DateTimeFormatter.ISO_LOCAL_TIME, timeStrings);
    }

    // Use of field variable insteed of passing the variable through parameters
    private LocalTime departureTime = null;

    Trip getTrip(LocalTime departureTime) throws EmptyTripListException {
        if (trips.isEmpty()) {
            throw new EmptyTripListException(routeName);
        }
        this.departureTime = departureTime;

        return findTrip(0, trips.size() - 1);
    }

    /**
     *
     * @param start
     * @param end
     * @return a LocalTime in the schedule closest after departureTime. Using
     * the rekursiv insert routine for insertionSort to get the closest higher
     * value returns null if there are no such value.
     */
    private Trip findTrip(int start, int end) {

        if (start == end) {
            //the "list" is just one value
            Trip trip = trips.get(start);
            if (trip.hasDeparted(departureTime)) {
                //this value was the last trip and it has departed
                return null;
            } else {
                //return value
                return trip;
            }
        }
        //the middle point of subList, rounded down
        //the middle point is therefore concider to be on the "low" half"
        int middle = (start + end) / 2;
        Trip trip = trips.get(middle);

        if (trip.hasDeparted(departureTime)) {
            /* middle value has departed, meaning the "low half" of array
            has also departed, check the "high half"
             */
            return findTrip(middle + 1, end);
        } else {
            /*Value is in "low half*/
            return findTrip(start, middle);

        }

    }

    public String getTripsSchedules() {
        String returnString = "";
        returnString = trips.stream().map((trip) -> trip.toString()).reduce(returnString, String::concat);
        return returnString;
    }

}
