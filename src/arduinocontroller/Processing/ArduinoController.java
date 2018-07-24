/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Processing;

import arduinocontroller.UI.COMSelector;
import arduinocontroller.UI.Dashboard;


/**
 *
 * @author Nicholas Berryman
 */
public class ArduinoController {
    public static final int POLL_RATE_MS = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new Dashboard().setVisible(true);
        new COMSelector().setVisible(true);
    }
}
