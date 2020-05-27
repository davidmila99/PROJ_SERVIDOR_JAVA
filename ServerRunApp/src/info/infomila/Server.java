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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Server {
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Necessito dos parametres de configuracio, la clase que vols i el fitxer de propietats");
            return;
        }
        
        final int PORT = 15000;

        Interficie obj;
        
        try {
            obj = FabricaInterficie.getInterficeRunApp(args[0],args[1]);
            System.out.println("Connexió establerta amb l'origen de dades");
        } catch (Exception ex) {
            System.out.println("Problemes per establir connexió amb l'origen de dades");
            ex.printStackTrace();
            return;
        }
        
        
        InterficieGrafica ig = new InterficieGrafica(obj);
        
      
    }
    public static void EngegarServer(Interficie obj){
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
                ((ServidorHilo) new ServidorHilo(socket, idSession,obj)).start();
                idSession++;
            }
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


