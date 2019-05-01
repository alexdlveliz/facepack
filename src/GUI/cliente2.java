/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import clases.paquete_de_envio;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Alex de León Véliz <alexdlveliz@hotmail.com>
 */
public class cliente2 
{
    public static void main(String args[])
    {
        MarcoCliente mimarco=new MarcoCliente();
		
        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MarcoCliente extends JFrame
{
    public MarcoCliente()
    {
	setBounds(600,300,280,350);
	LaminaMarcoCliente milamina=new LaminaMarcoCliente();
	add(milamina);
	setVisible(true);
    }		
}

class LaminaMarcoCliente extends JPanel implements Runnable
{
    public LaminaMarcoCliente()
    {
        nick = new JTextField(5);
        add(nick);
        JLabel texto=new JLabel("-CHAT-");
	add(texto);
        ip = new JTextField(8);
        add(ip);
        campochat = new JTextArea(12,20);
        campochat.setEnabled(false);
        add(campochat);
	campo1=new JTextField(20);
	add(campo1);		
	miboton=new JButton("Enviar");
        EnviaTexto event = new EnviaTexto();
        miboton.addActionListener(event);
	add(miboton);
        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {
        try{
            ServerSocket socketservidor = new ServerSocket(9090);
            Socket cliente;
            paquete_de_envio paquete_recibido = new paquete_de_envio();
            while(true)
            {
                cliente = socketservidor.accept();
                ObjectInputStream flujo_entrada = new ObjectInputStream(cliente.getInputStream());
                paquete_recibido = (paquete_de_envio) flujo_entrada.readObject();
                campochat.append("\n" + paquete_recibido.getNick() + ": "
                + paquete_recibido.getMensaje());
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    private class EnviaTexto implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //System.out.println(campo1.getText());
                Socket misocket = new Socket("192.168.43.93", 9999);
                paquete_de_envio datos = new paquete_de_envio();
                datos.setIp(ip.getText());
                datos.setNick(nick.getText());
                datos.setMensaje(campo1.getText());
                ObjectOutputStream flujo_salida = new ObjectOutputStream(misocket.getOutputStream());
                flujo_salida.writeObject(datos);
                /*DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
                flujo_salida.writeUTF(campo1.getText());
                flujo_salida.close();*/
                misocket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    private JTextField campo1, nick, ip;
    private JTextArea campochat;
    private JButton miboton;
}