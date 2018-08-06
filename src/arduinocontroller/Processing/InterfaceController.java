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
    private final ArrayList<Component> POVs = new ArrayList<>();
    private final ArrayList<ControllerListener> controllerListeners = new ArrayList<>();
    private final double DEADZONE = 0.01;
    private static int lastIdent = -1;
    private int ID = -1;
    private boolean monitoring = false;
    
    
    private InterfaceController(Controller encapsulate){
        this.controller = encapsulate;
        for (Component comp : controller.getComponents()){
            if (comp.isAnalog()){
                axes.add(comp);
            }
            else{
                if (!comp.getIdentifier().toString().toLowerCase().equals("pov"))
                    buttons.add(comp);
                else
                    POVs.add(comp);
            }
        }
    }
    
    public void startMonitoring(){
        if (!monitoring){
            lastIdent++;
            ID = lastIdent;
            monitoring = true;
            controller.poll();
            new Thread(new ControllerWatchThread(this)).start();
        }
         
    }
    
    public synchronized void poll(){
        ArrayList<Float> oldAxes = new ArrayList<>();
        for (int i = 0; i < this.getAxisCount(); i++)
            oldAxes.add(this.getAxisValue(i));
        ArrayList<Boolean> oldButtons = new ArrayList<>();
        for (int i = 0; i < this.getButtonCount(); i++)
            oldButtons.add(this.getButtonValue(i));
        ArrayList<Float> oldPOVs = new ArrayList<>();
        for (int i = 0; i < this.getPOVCount(); i++)
            oldPOVs.add(this.getPOVValue(i));
        
        controller.poll();
        
        for (int i = 0; i < this.getAxisCount(); i++){
            if (oldAxes.get(i) != this.getAxisValue(i)){
                for (ControllerListener listener : controllerListeners){
                    listener.axisChange(i);
                }
            }
        }
        for (int i = 0; i < this.getButtonCount(); i++){
            if (oldButtons.get(i) != this.getButtonValue(i)){
                for (ControllerListener listener : controllerListeners)
                    listener.buttonChange(i);
            }
        }
        for (int i = 0; i < this.getPOVCount(); i++){
            if (oldPOVs.get(i) != this.getPOVValue(i)){
                for (ControllerListener listener : controllerListeners)
                    listener.povChange(i);
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
        if (axes.get(index).getPollData() < DEADZONE && axes.get(index).getPollData() > -DEADZONE)
            return 0;
        else
            return axes.get(index).getPollData();
    }
    
    public int getAxisCount(){
        return axes.size();
    }
    
    public float getPOVValue(int index){
        return POVs.get(index).getPollData();
    }
    
    public int getPOVCount(){
        return POVs.size();
    }

    public void addControllerListener(ControllerListener controllerListener) {
        this.controllerListeners.add(controllerListener);
    }
    
    public int getID(){
        return ID;
    }
    
    
    public static ArrayList<InterfaceController> GetAllControllers(){
        for (Controller contr : ControllerEnvironment.getDefaultEnvironment().getControllers()){
            boolean has = false;
            for (InterfaceController c : allControllers){
                if (c.controller == contr) has = true;
            }
            if (!has){
                InterfaceController newController = new InterfaceController(contr);
                if (newController.getAxisCount() > 0 || newController.getButtonCount() > 1)
                    allControllers.add(newController);
            }
        }
        return allControllers;
    }
}
