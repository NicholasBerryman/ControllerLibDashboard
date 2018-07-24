/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.Comms;

import java.util.ArrayList;
import net.Network;
import net.Network_iface;

/**
 *
 * @author Nicholas Berryman
 */
public class SerialInterface {
    public static int BAUD_RATE = 115200;
    private final basic_iface serialReader = new basic_iface();
    private Network port;
    
    public SerialInterface(int port){
        this.port = new Network(0, serialReader, '\n');
        System.out.println(port);
        this.port.connect(this.port.getPortList().get(port), 115200);
    }
    
    public synchronized void send(String s){
        port.writeSerial(s+"\n");
    }
    
    public ArrayList<String> getFreePorts(){
        return new ArrayList<>(port.getPortList());
    }
    
    public void addSerialListener(SerialListener listener){
        serialReader.addSerialListener(listener);
    }
}

class basic_iface implements Network_iface{
    private ArrayList<SerialListener> listeners = new ArrayList<>();
    
    @Override
    public void writeLog(int i, String string) {
        System.out.println("   log:  |" + string + "|");
    }

    @Override
    public void parseInput(int id, int numBytes, int[] message) {
        String messageStr = "";
        for (int i = 0; i < numBytes; ++i) {
            messageStr += (char)message[i];
        }
        messageStr += "\n";
        for (SerialListener listener : listeners)
            listener.serialRecieve(messageStr);
    }

    @Override
    public void networkDisconnected(int i) {
        System.out.println("Error. Disconnect!");
    }
    
    public void addSerialListener(SerialListener listener){
        listeners.add(listener);
    }
}
