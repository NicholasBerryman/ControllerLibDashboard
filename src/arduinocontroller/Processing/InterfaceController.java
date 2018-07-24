/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Processing;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author Nicholas Berryman
 */
public class InterfaceController {
    private final static ArrayList<InterfaceController> allControllers = new ArrayList<>();
    private final Controller controller;
    private final ArrayList<Component> buttons = new ArrayList<>();
    private final ArrayList<Component> axes = new ArrayList<>();
    private final ArrayList<ControllerListener> controllerListeners = new ArrayList<>();
    
    public InterfaceController(Controller encapsulate){
        this.controller = encapsulate;
        
        for (Component comp : controller.getComponents()){
            if (comp.isAnalog())
                axes.add(comp);
            else
                buttons.add(comp);
        }
    }
    
    public void startMonitoring(){
         new Thread(new ControllerWatchThread(this)).start();
    }
    
    public void poll(){
        ArrayList<Float> oldAxes = new ArrayList<>();
        for (int i = 0; i < this.getAxisCount(); i++)
            oldAxes.add(this.getAxisValue(i));
        ArrayList<Boolean> oldButtons = new ArrayList<>();
        for (int i = 0; i < this.getButtonCount(); i++)
            oldButtons.add(this.getButtonValue(i));
        controller.poll();
        for (int i = 0; i < this.getAxisCount(); i++){
            if (oldAxes.get(i) != this.getAxisValue(i)){
                for (ControllerListener listener : controllerListeners)
                    listener.axisChange(i);
            }
        }
        for (int i = 0; i < this.getButtonCount(); i++){
            if (oldButtons.get(i) != this.getButtonValue(i)){
                for (ControllerListener listener : controllerListeners)
                    listener.buttonChange(i);
            }
        }
    }
    
    public String getName(){
        return controller.getName();
    }
    
    public boolean getButtonValue(int index){
        return buttons.get(index).getPollData() > 0;
    }
    
    public int getButtonCount(){
        return buttons.size();
    }
    
    public float getAxisValue(int index){
        return axes.get(index).getPollData();
    }
    
    public int getAxisCount(){
        return axes.size();
    }

    public void addControllerListener(ControllerListener controllerListener) {
        this.controllerListeners.add(controllerListener);
    }
    
    
    
    
    public static ArrayList<InterfaceController> GetAllControllers(){
        for (Controller contr : ControllerEnvironment.getDefaultEnvironment().getControllers()){
            boolean has = false;
            for (InterfaceController c : allControllers){
                if (c.controller == contr) has = true;
            }
            if (!has) allControllers.add(new InterfaceController(contr));
        }
        return allControllers;
    }
}
