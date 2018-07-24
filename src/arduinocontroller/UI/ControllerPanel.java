/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinocontroller.UI;

import arduinocontroller.Processing.InterfaceController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Nicholas Berryman
 */
public class ControllerPanel extends javax.swing.JPanel {
    private final ArrayList<JLabel> axes = new ArrayList<>();
    private final ArrayList<JLabel> buttons = new ArrayList<>();
    /**
     * Creates new form ControllerPanel
     * @param controller
     */
    public ControllerPanel(InterfaceController controller) {
        initComponents();
        this.setPreferredSize(new Dimension(640,480));
        this.setLayout(new FlowLayout());
        
        
        for (int i = 0; i < controller.getAxisCount(); i++){
            JLabel nextAxis = new JLabel(" "+Integer.toString(i)+" ");
            nextAxis.setBackground(Color.blue);
            nextAxis.setOpaque(true);
            axes.add(nextAxis);
        }
         for (int i = 0; i < controller.getButtonCount(); i++){
            JLabel nextButton = new JLabel(" "+Integer.toString(i)+" ");
            nextButton.setBackground(Color.red);
            nextButton.setOpaque(true);
            buttons.add(nextButton);
        }
        
        for (JLabel axis : axes){
            this.add(axis);
        }
        for (JLabel button : buttons){
            this.add(button);
        }
    }

    public void setButton(int buttonID, boolean on){
        if (on) buttons.get(buttonID).setBackground(Color.green);
        else buttons.get(buttonID).setBackground(Color.red);
    }
    
    public void setAxis(int axisID, double value){
        if (value > 0) {
            axes.get(axisID).setBackground(new Color(0.0f, (float)value, (float)(1.0-value)));
        }
        else if (value < 0) {
            axes.get(axisID).setBackground(new Color((float)-value, 0.0f, (float)(1.0+value)));
        }
        else {
            axes.get(axisID).setBackground(Color.blue);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
