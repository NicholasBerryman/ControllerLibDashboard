/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Processing;

/**
 *
 * @author Nicholas Berryman
 */
public abstract class ControllerListener {
    public abstract void axisChange(int axisIndex);
    public abstract void buttonChange(int buttonIndex);
}
