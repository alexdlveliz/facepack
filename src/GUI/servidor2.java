/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import clases.paquete_de_envio;
import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author alex
 */
public class servidor2 
{
    public static void main(String args[])
    {
        marcoServidor marco = new marcoServidor();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class marcoServidor extends JFrame implements Runnable
{
    private JTextArea areatexto;
    public marcoServidor()
    {
        setBounds(1200,300,200,350);
        JPanel milamina = new JPanel();
        milamina.setLayout(new BorderLayout());
        areatexto = new JTextArea();
        milamina.add(areatexto, BorderLayout.CENTER);
        add(milamina);
        setVisible(true);
        Thread mihilo = new Thread(this);
        mihilo.start();
    }
    
    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9999);
            String nick, ip, mensaje;
            paquete_de_envio paquete_recibido;
            while(true)
            {
                Socket misocket = servidor.accept();
                ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());
                paquete_recibido = (paquete_de_envio) paquete_datos.readObject();
                nick = paquete_recibido.getNick();
                ip = paquete_recibido.getIp();
                mensaje = paquete_recibido.getMensaje();
//                DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
//                String mensaje_texto = flujo_entrada.readUTF();
//                areatexto.append("\n"+mensaje_texto);
                areatexto.append("\n" + nick + ":" + mensaje + " para "+ ip);
                misocket.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(marcoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}