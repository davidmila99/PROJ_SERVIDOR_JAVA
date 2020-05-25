/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author David
 */
public class Server {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final int PORT = 15000;

        
        InterficieGrafica ig = new InterficieGrafica();
        
      
    }
    public static void EngegarServer(){
        final int PORT = 15000;
        ServerSocket ss;

        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(PORT);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                System.out.println("PSO PER KI");
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ((ServidorHilo) new ServidorHilo(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


