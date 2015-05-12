package base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

public class Serveur extends JFrame{

	private String VERSION = "1.0";
	
	private static final long serialVersionUID = 1L;
	private int port = 2000;
	private Chat c;
	private JTextPane chat = new JTextPane();
	private JScrollPane scrollC = new JScrollPane(chat);
	private JTextPane liste = new JTextPane();
	private JScrollPane scrollL = new JScrollPane(liste);
	private Timer refresh;
	
    public static void main(String[] zero){
        new Serveur();
    }
    
    public Serveur(){
    	makeFenetre();
    	ServerSocket socket = null;
        try {
        	c = new Chat(chat,liste);
        	c.say2("LanChat serveur version "+VERSION);
        	c.say2("Démarrage du serveur sur le port : "+port);
        	socket = new ServerSocket(port);
	        Thread t = new Thread(new Accueil(socket,c));
	        t.start();
        } catch (IOException e) {
        	c.say2("Port déjà utilisé : "+port);
        }
        
        refresh = new Timer(200,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.refresh();
			}
        });
        refresh.start();
        
    }
    
    void makeFenetre(){
		this.setTitle("LanChat - Serveur (v"+VERSION+")");
		this.setSize(600,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chat.setEditable(false);
		chat.setText("");
		
		((DefaultCaret)chat.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		liste.setEditable(false);
		liste.setText("Clients connectés :");
		
		liste.setPreferredSize(new Dimension(180,0));
		scrollL.setAutoscrolls(false);
		this.add(scrollC,BorderLayout.CENTER);
		this.add(scrollL,BorderLayout.EAST);
		
		URL iconurl = this.getClass().getResource("icon64p.ico");
		if(iconurl != null){
			ImageIcon icon = new ImageIcon(iconurl);
			this.setIconImage(icon.getImage());
		}
		this.setVisible(true);
	}
}

