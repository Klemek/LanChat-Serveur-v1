package base;

public class Client {

	private int id;
	private String pseudo;
	private String ip;
	private boolean admin = false;
	private boolean muted = false;
	private boolean connected = true;
	
	public Client(int id,String pseudo,String ip){
		this.id=id;
		this.pseudo = pseudo;
		this.ip = ip;
	}
	
	public Client(int id,String pseudo,String ip,boolean admin){
		this.id=id;
		this.pseudo = pseudo;
		this.ip = ip;
		this.admin = admin;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public String getIp() {
		return ip;
	}
	
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getAdminMark(){
		return ""+(admin?Chat.ADMIN:"");
	}

	public int getId() {
		return id;
	}
	
}
