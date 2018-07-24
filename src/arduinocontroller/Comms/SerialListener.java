/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Comms;

/**
 *
 * @author Nicholas Berryman
 */
public abstract class SerialListener {
    public abstract void serialRecieve(String message);
}
