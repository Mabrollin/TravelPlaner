/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.light.gui.components;

import java.time.Duration;
import java.time.LocalTime;
import javafx.scene.shape.Line;

/**
 *
 * @author MARBRO02
 */
public class Route extends Line {

    public Route(Station[] routeStations, String[] args) {
        parseRoute(routeStations, args);
        setStartX(routeStations[0].getCenterX());
        setStartY(routeStations[0].getCenterY());
        setEndX(routeStations[1].getCenterX());
        setEndY(routeStations[1].getCenterY());

    }

    private void parseRoute(Station[] routeStations, String[] args) {
        TravelPlaner.Route route = new TravelPlaner.Route(routeStations[1].getStop(), args[0]);
        String[] departures = args[1].split("\n"), arrivals = args[2].split("\n");
        if (departures.length != arrivals.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < departures.length; i++) {
            if (departures[i].length() == 5) {
                route.addTrip(LocalTime.parse(departures[i]), LocalTime.parse(arrivals[i]));
            } else {
                LocalTime departure = LocalTime.parse(departures[i].substring(0, 5));
                LocalTime arrival = LocalTime.parse(arrivals[i].substring(0, 5));
                Duration duration = Duration.parse(departures[i].substring(6, 12));
                if (departures[i].charAt(12) == '*') {
                    int runs = Integer.parseInt(departures[i].substring(13));
                    for (int j = 0; j < runs; j++) {
                        route.addTrip(departure, arrival);
                        departure = departure.plus(duration);
                        arrival = arrival.plus(duration);
                    }
                } else if (departures[i].charAt(12) == '-' && departures[i].charAt(13) == '>') {
                    LocalTime end = LocalTime.parse(departures[i].substring(14));
                    while (departure.isBefore(end)) {
                        route.addTrip(departure, arrival);
                        departure = departure.plus(duration);
                        arrival = arrival.plus(duration);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        routeStations[0].getStop().addRoute(route);
        System.out.println(routeStations[0].getStop() + "adds: " + route.getTripsSchedules());

    }

}
