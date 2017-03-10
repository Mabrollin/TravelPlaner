/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

import java.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MARBRO02
 */
public class TrafficMapTest {

    private static TrafficMap map;
    private static Stop stop1;
    private static Stop stop2;
    private static Stop stop3;
    private static Stop stop4;
    private static Stop stop5;
    private static Stop stop6;

    public TrafficMapTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        map = new TrafficMap();
        stop1 = new Stop(0, 0, "Station 1");
        stop2 = new Stop(0, 0, "Station 2");
        stop3 = new Stop(0, 0, "Station 3");
        stop4 = new Stop(0, 0, "Station 4");
        stop5 = new Stop(0, 0, "Station 5");
        stop6 = new Stop(0, 0, "Station 6");

        Route route102 = new Route(stop2, "Buss 102");
        route102.addTrips("07:00", "07:15", "09:30", "09:45", "11:00", "11:15", "13:45", "14:00", "17:15", "17:30");
        Route route104 = new Route(stop4, "Buss 104");
        route104.addTrips("07:30", "07:45", "08:30", "08:45", "12:15", "12:30", "13:45", "14:00", "18:15", "18:30");
        Route route105 = new Route(stop5, "Buss 105");
        route105.addTrips("07:00", "07:20", "09:20", "09:40", "11:35", "11:55", "13:45", "14:05", "18:15", "18:35");

        stop1.addRoutes(route102, route104, route105);
        
        Route route201 = new Route(stop1, "Buss 201");
        route201.addTrips("07:30", "07:45", "10:30", "10:45", "11:00", "11:15", "15:15", "15:30", "17:15", "17:30");
        Route route204 = new Route(stop4, "Buss 204");
        route204.addTrips("07:00", "07:15", "08:30", "08:45", "12:00", "12:15", "13:45", "14:00", "17:15", "17:30");
        Route route206 = new Route(stop6, "Buss 206");
        route206.addTrips("07:20", "07:40", "09:20", "09:40", "10:35", "10:55", "13:25", "13:45", "18:15", "18:35");
        Route route205 = new Route(stop5, "Buss 205");
        route205.addTrips("07:00", "07:15", "09:30", "09:45", "11:00", "11:15", "13:45", "14:00", "17:15", "17:30");
        Route route203 = new Route(stop3, "Buss 203");
        route203.addTrips("07:30", "07:45", "08:30", "08:45", "12:15", "12:30", "13:45", "14:00", "18:15", "18:30");
        
        stop2.addRoutes(route201, route204, route206, route205, route203);

        Route route305 = new Route(stop5, "Buss 305");
        route305.addTrips("07:00", "07:15", "08:30", "08:45", "12:00", "12:15", "13:45", "14:00", "17:15", "17:30");
        Route route302 = new Route(stop2, "Buss 302");
        route302.addTrips("07:00", "07:15", "09:30", "09:45", "11:00", "11:15", "13:45", "14:00", "17:15", "17:30");
        
        stop3.addRoutes(route305, route302);
        
        map.add(stop1, stop2, stop3, stop4, stop5, stop6);

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class TrafficMap.
     */
    @Test
    public void testAdd() {

    }

    /**
     * Test of getTravelSuggestions method, of class TrafficMap.
     */
    @Test
    public void testGetTravelSuggestions() {
        System.out.println("getTravelSuggestions");
        Stop start = stop1;
        Stop destination = stop3;
        LocalTime departure = LocalTime.parse("09:00");
        TravelOption expResult = null;
        TravelOption result = map.getTravelSuggestions(start, destination, departure);
        System.out.println(result);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getStopClosestTo method, of class TrafficMap.
     */
    @Test
    public void testGetStopClosestTo() {

    }

}
