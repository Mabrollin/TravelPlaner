/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

import TravelPlaner.Route.Trip;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author MARBRO02 Works like a Node
 */
public class Stop {

    private final Coordinate coordinate;
    private final String name;

    @Override
    public String toString() {
        return name;
    }
    private final Set<Route> routes = new HashSet<>();

    public Stop(long x, long y, String name) {
        this.coordinate = new Coordinate(x, y);
        this.name = name;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    /**
     *
     * @param departureTime
     * @return Map of adjecent Stops-nodes mapped to their vertex-weight The
     * vertex-weight is a LocalTime instance of the departure closest after the
     * departurTime argument.
     */
    Map<Stop, TravelOption> getTravelOptions(LocalTime departureTime) {
        return getTravelOptions(new TravelOption(this, departureTime));
    }

    Map<Stop, TravelOption> getTravelOptions(TravelOption prevOption) {
        Map<Stop, TravelOption> travelOptions = new HashMap<>();
        routes.forEach(route -> {
            Trip trip = route.getTrip(prevOption.getETA());
            if (trip != null) {
                Stop stop = route.getStop();
                TravelOption newOption = new TravelOption(prevOption);
                newOption.travel(trip);
                travelOptions.put(stop, newOption);
            }
        });
        return travelOptions;
    }

    void addRoutes(Route... routes) {
        for (Route route : routes) {
            addRoute(route);
        }

    }

    public String getRouteSchedules() {
        String returnString = "";
        returnString = routes.stream().map((trip) -> trip.toString()).reduce(returnString, String::concat);
        return returnString;
    }

}
