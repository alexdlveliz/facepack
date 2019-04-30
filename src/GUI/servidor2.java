/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
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
            ServerSocket  servidor = new ServerSocket(9999);
            Socket misocket = servidor.accept();
            DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
            String mensaje_texto = flujo_entrada.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(marcoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}