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
class Coordinate {

    @Override
    public String toString() {
        return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    private final long x, y;
}
