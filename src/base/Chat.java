package base;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JTextPane;

public class Chat {

	protected static char ADMIN = '#';
	protected static String BEG = "";
	protected static String END = " : ";
	
	private ArrayList<String> log = new ArrayList<String>();
	private ArrayList<String> log2 = new ArrayList<String>();
	private int time = 0;
	
	private Hashtable<Integer,Client> clients = new Hashtable<Integer,Client>();
	private int ind = 0;
	
	private ArrayList<String> ipban = new ArrayList<String>();
	
	private JTextPane chat = new JTextPane();
	private JTextPane liste = new JTextPane();
	
	private String ips = "";
	
	public Chat(JTextPane chat,JTextPane liste){
		this.chat = chat;
		this.liste = liste;
	}
	
	public int connect(String pseudo,String ip, boolean adm){
		int id = getNewID();
		clients.put(id,new Client(id,pseudo,ip,adm));
		refresh2();
		System.out.println("Client "+id+" : "+pseudo+" ("+ip+")");
		return id;
	}
	
	public void disconnect(int id){
		clients.remove(id);
		refresh2();
	}
	
	private int getNewID(){
		ind++;
		return ind-1;
	}
	
	public void say(String msg){
		log.add(msg);
		log2.add(msg);
		time++;
	}
	
	public void say2(String msg){
		log2.add(msg);
	}
	
	public void refresh(){
		chat.setText("");
		if(log2 != null){
			String text = "";
			for(String s:log2){
				text = text.equals("")?s:text+"\n"+s;
			}
			chat.setText(text);
		}
	}
	
	public void refresh2(){
		liste.setText("Clients connect�s :");
		if(clients != null){
			String text = "Clients connect�s :";
			for(Client c:clients.values()){
				text = text+"\n"+c.getAdminMark()+c.getPseudo()+" ("+c.getIp()+")";
			}
			liste.setText(text);
		}
	}
	
	public ArrayList<String> getLog() {
		return log;
	}
	
	public void clearLog() {
		log = null;
		time = 0;
	}
	
	public int isFree(String pseudo){
		if(getClientByPseudo(pseudo)!=null)return 1;
		if(pseudo.contains(""+ADMIN) || pseudo.contains(" "))return 2;
		return 0;
	}
	
	public String getPseudo(int id){
		return getClientByID(id).getPseudo();
	}
	
	public void setPseudo(int id,String ps){
		Client cl = getClientByID(id);
		String old = cl.getPseudo();
		getClientByID(id).setPseudo(ps);
		refresh2();
		say(old+" est maintenant "+ps);
	}
	
	public String getIP(int id){
		return getClientByID(id).getIp();
	}
	
	public String getIP(String pseudo){
		return getClientByPseudo(pseudo).getIp();
	}
	
	public int getID(String pseudo){
		return getClientByPseudo(pseudo).getId();
	}

	public int getTime() {
		return time;
	}

	public boolean isAdmin(int id) {
		return getClientByID(id).isAdmin();
	}
	
	public Client getClientByID(int id){
		return clients.get(id);
	}
	
	public Client getClientByPseudo(String pseudo){
		if(clients == null)return null;
		for(Client c:clients.values()){
			if(c.getPseudo().equals(pseudo)){
				return c;
			}
		}
		System.err.println("Pas de client avec le pseudo "+pseudo);
		return null;
	}

	public Hashtable<Integer, Client> getClients() {
		return clients;
	}
	
	public String banIP(String ip){
		if(ipban.contains(ip)){
			return "l'ip "+ip+" a d�j� �t� bannie.";
		}else{
			ipban.add(ip);
			return "l'ip "+ip+" a �t� bannie.";
		}	
	}
	
	public String debanIP(String ip){
		if(ipban.contains(ip)){
			ipban.remove(ip);
			return "l'ip "+ip+" a �t� d�bannie.";
		}else{
			return "l'ip "+ip+" n'est pas bannie.";
		}
	}
	
	public String mute(String pseudo){
		Client c = getClientByPseudo(pseudo);
		if(c.isMuted()){
			c.setMuted(false);
			return c.getPseudo()+" peut parler � nouveau.";
		}else{
			c.setMuted(true);
			return c.getPseudo()+" ne peut plus parler.";
		}
	}
	
	public boolean isBanned(String ip){
		return ipban.contains(ip);
	}
	
	public void setAdmin(String pseudo){
		Client c = getClientByPseudo(pseudo);
		if(c.isAdmin()){
			c.setAdmin(false);
			refresh2();
			say(c.getPseudo()+" n'est plus admin.");
		}else{
			c.setAdmin(true);
			refresh2();
			say(c.getPseudo()+" est maintenant admin.");
		}
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
		System.out.println("IP serveur : "+ips);
	}
	
}
