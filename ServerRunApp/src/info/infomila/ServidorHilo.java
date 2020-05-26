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
import java.util.ArrayList;
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
    private Interficie obj;
    
    public ServidorHilo(Socket socket, int id,Interficie obj) {
        this.socket = socket;
        this.idSessio = id;
        this.obj = obj;
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
        int accion;
        /*
        Accions 
        1: llista rutes
        2: llista punts
        */
        
        try {
            accion = dis.readInt();
                switch(accion){
                    case 1:
                        enviarLlistaRutes();
                        break;
                    case 2:
                        envriarPunts();
                        break;
                }
            /*
            if(accion.equals("hola")){
                System.out.println("El cliente con idSesion "+this.idSessio+" saluda");
                Ruta rEnviar = new Ruta(1,"Enviada");
                enviarRuta(rEnviar.getId(),rEnviar.getTitol());
            }
            */
        } catch (IOException ex) {
            //Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }

    private void enviarRuta(Ruta r) {
        try {
            enviaObjecte.writeObject(r.getId());
            enviaObjecte.writeObject(r.getTitol());
            enviaObjecte.writeObject(r.getDescMarkDown());
            enviaObjecte.writeObject(r.getDesnivell());
            enviaObjecte.writeObject(r.getAlcadaMax());
            enviaObjecte.writeObject(r.getAlcadaMin());
            enviaObjecte.writeObject(r.getDistanciaKm());
            //enviaObjecte.writeObject(r.getTemsAprox());
            enviaObjecte.writeObject(r.isCircular());
            enviaObjecte.writeObject(r.getDificultat());
            enviaObjecte.writeObject(r.getUrlGpx());
        } catch (IOException ex) {
            System.out.println("ERROR AL ENVIAR ALGUN PARAMETRE DE RUTA");
        }
        
    }

    private void enviarLlistaRutes() {
        try {
            ArrayList<Ruta> ruts = obj.getRutaList();
            dos.writeInt(ruts.size());
            for(Ruta en : ruts){
                enviarFoto(en.getFotoRuta());
                envairCategoria(en.getCatPare());
                enviarRuta(en);
            }
        } catch (InterficieException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void enviarFoto(Foto f) {
        try {
            enviaObjecte.writeObject(f.getId());
            enviaObjecte.writeObject(f.getUrlFoto());
            enviaObjecte.writeObject(f.getTitolFoto());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void envairCategoria(Categoria c) {
        try {
            enviaObjecte.writeObject(c.getId());
            enviaObjecte.writeObject(c.getNom());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void envriarPunts() {
        try {
            int idR = dis.readInt();
            ArrayList<Punt> pts = new ArrayList<>();
            pts = obj.getPutntsRuta(idR);
            dos.writeInt(pts.size());
            for(Punt p: pts){
                enviarFoto(p.getPuntFoto());
                enviarPut(p);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterficieException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarPut(Punt p) {
        try {
            enviaObjecte.writeObject(p.getNumero());
            enviaObjecte.writeObject(p.getNom());
            enviaObjecte.writeObject(p.getDesc());
            //enviaObjecte.writeObject(p.getHora());
            enviaObjecte.writeObject(p.getLat());
            enviaObjecte.writeObject(p.getLongitud());
            enviaObjecte.writeObject(p.getElevacio());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
    
  
    

