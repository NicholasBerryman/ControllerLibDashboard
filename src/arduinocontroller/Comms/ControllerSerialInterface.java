/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Comms;

import arduinocontroller.Processing.ControllerListener;
import arduinocontroller.Processing.InterfaceController;
import java.util.ArrayList;

/**
 *
 * @author Nicholas Berryman
 */
public class ControllerSerialInterface {
    private final SerialInterface serialInterface;
    private final ArrayList<InterfaceController> controllers = new ArrayList<>();
    private static final String controllerCode = "C";
    private static final String axisCode = "A";
    private static final String buttonCode = "B";
    private static final String POVCode = "P";
    private static final String valueCode = "V";
    private static final String messageCode = "M";
    
    public ControllerSerialInterface(int arduinoPort){
        serialInterface = new SerialInterface(arduinoPort);
    }
    
    public void addSerialListener(SerialListener listener){
        serialInterface.addSerialListener(listener);
    }
    
    public void addController(InterfaceController controller){
        controllers.add(controller);
        controller.addControllerListener(new ControllerListener(){
            @Override
            public void axisChange(int axisIndex) {
                String controllerCode = Integer.toString(controller.getID()) + ControllerSerialInterface.controllerCode;
                String value = Float.toString(controller.getAxisValue(axisIndex)).substring(
                        0,Math.min(5,Float.toString(controller.getAxisValue(axisIndex)).length())) 
                                + ControllerSerialInterface.valueCode;
                String axisCode = Integer.toString(axisIndex)+ControllerSerialInterface.axisCode;
                serialInterface.send(controllerCode + value + axisCode);
            }
            
            @Override
            public void buttonChange(int buttonIndex) {
                String controllerCode = Integer.toString(controller.getID()) + ControllerSerialInterface.controllerCode;
                String value = Integer.toString(controller.getButtonValue(buttonIndex) ? 1 : 0) + ControllerSerialInterface.valueCode;
                String ButtonCode = Integer.toString(buttonIndex)+ControllerSerialInterface.buttonCode;
                serialInterface.send(controllerCode + value + ButtonCode);
            }

            @Override
            public void povChange(int POVIndex) {
                String controllerCode = Integer.toString(controller.getID()) + ControllerSerialInterface.controllerCode;
                String value = controller.getPOVValue(POVIndex)
                        + ControllerSerialInterface.valueCode;
                String POVCode = Integer.toString(POVIndex)+ControllerSerialInterface.POVCode;
                serialInterface.send(controllerCode + value + POVCode);
            }
        });
    }
    
    public void sendMessage(String toSend){
        serialInterface.send(messageCode+toSend);
    }
}
