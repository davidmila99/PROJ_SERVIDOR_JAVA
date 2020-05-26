/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class InterficieGrafica {
    private JFrame frame;
    private JButton bEngegar = new JButton("Engegar");
    private JButton bParar = new JButton("Parar");
    private Interficie obj;

    public InterficieGrafica(Interficie obj) {
        this.obj = obj;
        frame = new JFrame("RunApp - Gestor Categories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        frame.add(bEngegar,BorderLayout.WEST);
        frame.add(bParar,BorderLayout.EAST);
        
        bEngegar.addActionListener(new EscoltadorBotons());
        
        frame.setSize(300, 200);
        
        
    }
    
    class EscoltadorBotons implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        String opcio = e.getActionCommand();
        switch(opcio){
            case "Engegar":
                Server.EngegarServer(obj);
                break;
            case "Parar":
                break;
        }
    }

}
    
    
}
