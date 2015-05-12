package base;

public class Client {

	private int id;
	private String pseudo;
	private String ip;
	private boolean admin = false;
	private boolean muted = false;
	private boolean connected = true;
	
	public Client(int id,String pseudo,String ip){
		this.id = id;
		this.pseudo = pseudo;
		this.ip = ip;
	}
	
	public Client(int id,String pseudo,String ip,boolean admin){
		this.id = id;
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
	public int getId() {
		return id;
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
	
	public static Client[] add(Client[] l1,Client l2){
		if(l1 == null) return new Client[]{l2};
		Client[] l3 = new Client[l1.length+1];
		for(int i = 0; i < l1.length; i++){
			l3[i]=l1[i];
		}
		l3[l1.length]=l2;
		return l3;	
	}
	
	public static Client[] suppr(Client[] l, int pos){
		if(l.length-1 == 0)return null;
		Client[] l2 = new Client[l.length-1];
		for(int i2 = 0; i2 < l.length; i2++){
			if(i2 < pos){
				l2[i2]=l[i2];
			}else if(i2 >= pos+1){
				l2[i2-1]=l[i2];
			}
		}
		return l2;
	}
	
}
