/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MARBRO02
 */
public class TrafficMap {

    private final List<Stop> stops = new ArrayList<>();
    private final Map<String, Stop> stopMapping = new HashMap<>();

    public void add(Stop stop) {
        stops.add(stop);
        stopMapping.put(stop.toString(), stop);
    }

    public void add(Stop... stops) {
        for (Stop stop : stops) {
            add(stop);
        }
    }

    public TravelOption getTravelSuggestions(String start, String destination, LocalTime departure) {
        Stop startStop = stopMapping.get(start);
        Stop destinationStop = stopMapping.get(destination);
        if (startStop == null || destinationStop == null) {
            System.out.println("Could not find stops: "
                    + (startStop == null ? start : "")
                    + (destinationStop == null ? destination : "")
            );
            return null;
        }
        return getTravelSuggestions(startStop, destinationStop, departure);
    }

    public TravelOption getTravelSuggestions(Stop start, Stop destination, LocalTime departure) {
        //Initiated with all route-destinations from start.
        Map<Stop, TravelOption> possibleTravelOptions = start.getTravelOptions(departure);
        /*We need to keep track och reached stops and the ETA in order to 
           not go through irrelevant cases or get stuck in a loop
         */
        Map<Stop, TravelOption> bestReachedStops = new HashMap<>();
        bestReachedStops.putAll(possibleTravelOptions);

        Map<Stop, TravelOption> newTravelSegments = new HashMap<>();

        while (!possibleTravelOptions.isEmpty()) {
            //Step again and put them in seperate Map.
            newTravelSegments.clear();
            possibleTravelOptions.forEach(
                    (stop, option) -> newTravelSegments.putAll(stop.getTravelOptions(option))
            );
            possibleTravelOptions.clear();
            
            //uppdate best reached stops and adds qualified cases to possible travel segments
            newTravelSegments.forEach((stop, option) -> {
                if (!bestReachedStops.containsKey(stop)) {
                    bestReachedStops.put(stop, option);
                    newTravelSegments.put(stop, option);
                } else {
                    if (bestReachedStops.get(stop).getETA().isAfter(option.getETA())) {
                        bestReachedStops.put(stop, option);
                        newTravelSegments.put(stop, option);
                    }
                }
            });

        }
        if (!bestReachedStops.containsKey(destination)) {

            return null;
        }
        return bestReachedStops.get(destination);
    }

    public Stop getStopClosestTo(long x, long y) {
        throw new RuntimeException();
    }

    public List<String> getStops() {
        List<String> returnStrings = new ArrayList<>();
        stops.forEach(stop -> returnStrings.add(stop.toString()));
        return returnStrings;
    }

}
