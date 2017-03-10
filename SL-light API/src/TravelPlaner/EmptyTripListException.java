/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelPlaner;

/**
 *
 * @author MARBRO02
 */
public class EmptyTripListException extends RuntimeException {

    public EmptyTripListException() {
    }

    EmptyTripListException(String routeName) {
       super(routeName);
    }
    
}
