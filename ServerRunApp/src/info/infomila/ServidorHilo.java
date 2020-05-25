/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class ServidorHilo extends Thread{

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ObjectOutputStream enviaObjecte;
    private int idSessio;
    
    public ServidorHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            enviaObjecte = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            //Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void desconnectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        String accion = "";
        
        try {
            accion = dis.readUTF();
            if(accion.equals("hola")){
                System.out.println("El cliente con idSesion "+this.idSessio+" saluda");
                Ruta rEnviar = new Ruta(1,"Enviada");
                enviarRuta(rEnviar.getId(),rEnviar.getTitol());
            }
        } catch (IOException ex) {
            //Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }

    private void enviarRuta(Integer id, String titol) {
        try {
            enviaObjecte.writeObject(id);
            enviaObjecte.writeObject(titol);
        } catch (IOException ex) {
            System.out.println("ERROR AL ENVIAR ALGUN PARAMETRE DE RUTA");
        }
        
    }
    
  
    
}
