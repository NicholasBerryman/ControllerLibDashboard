/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Processing;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicholas Berryman
 */
public class ControllerWatchThread implements Runnable{
    private final InterfaceController toWatch;
    
    public ControllerWatchThread(InterfaceController toWatch){
        this.toWatch = toWatch;
    }
    
    @Override
    public void run() {
        while (true){
            toWatch.poll();
            try {
                Thread.sleep(ArduinoController.POLL_RATE_MS);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerWatchThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
