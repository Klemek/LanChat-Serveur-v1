package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reception implements Runnable{

	private BufferedReader in;
    private BufferedWriter out;
    private Client cl;
    private Chat c;
	private boolean stop = false;
	
	public Reception(Chat c, BufferedReader in,BufferedWriter out, int id) {
		this.c = c;
		this.in = in;
		this.out = out;
		this.cl = c.getClientByID(id);

	}

	@Override
	public void run() {
		while(!stop){
			if(!cl.isConnected())deco();
			try {
				String msg = in.readLine();
				if(msg != null && msg != ""){
					if(msg.toCharArray()[0]!='/'){
						if(cl.isMuted()){
							sayP("Vous ne pouvez plus parler.");
						}else{
							c.say(Chat.BEG+cl.getAdminMark()+cl.getPseudo()+Chat.END+msg);
						}
						
					}else{
						c.say2(Chat.BEG+cl.getAdminMark()+cl.getPseudo()+Chat.END+msg);
						String[] par = msg.split(" ");
						switch(par[0]){
							case "/admin":
								if(cl.isAdmin()){
									if(par.length < 2){
										sayP("/admin pseudo");
									}else{
										if(c.getClientByPseudo(par[1])==null){
											sayP(par[1]+" n'existe pas.");
										}else{
											c.setAdmin(par[1]);
										}
									}
								}else{
									sayP("Vous n'avez pas l'accès à cette commande");
								}
								break;
							case "/ban":
								if(cl.isAdmin()){
									if(par.length < 2){
										sayP("/ban pseudo");
									}else{
										Client cl1 = c.getClientByPseudo(par[1]);
										if(cl1==null){
											sayP(par[1]+" n'existe pas.");
										}else if(cl1.isAdmin()){
											sayP(par[1]+" est admin, vous ne pouvez pas le bannir.");
										}else if(cl1.getIp().equals(c.getIps())){
											sayP("Vous ne pouvez pas bannir l'ip du serveur");
										}else{
											sayP(c.banIP(cl1.getIp()));
											cl1.setConnected(false);
										}
									}
								}else{
									sayP("Vous n'avez pas l'accès à cette commande");
								}
								break;
							case "/deban":
								if(cl.isAdmin()){
									if(par.length < 2){
										sayP("/deban ip");
									}else{
										sayP(c.debanIP(par[1]));
									}
								}else{
									sayP("Vous n'avez pas l'accès à cette commande");
								}
								break;
							case "/disconnect":
								deco();
								break;
							case "/help":
								ArrayList<String> comm = new ArrayList<String>(Arrays.asList("/disconnect","/help","/ip","/list","/me","/pseudo"));
								ArrayList<String> commAdm = new ArrayList<String>(Arrays.asList("/kick","/ban","/deban","/mute","/admin"));
								if(cl.isAdmin()){
									comm.addAll(commAdm);
								}
								ArrayList<String> text = new ArrayList<String>(Arrays.asList("Liste des commandes :"));
								text.addAll(comm);
								for(String s:text){
									sayP(s);
								}
								break;
							case "/ip":
								if(par.length < 2){
									sayP("/ip pseudo");
								}else{
									if(c.getClientByPseudo(par[1])==null){
										sayP(par[1]+" n'existe pas.");
									}else{
										sayP("L'ip de "+par[1]+" est "+c.getIP(par[1]));
									}
								}
								break;
							case "/kick":
								if(cl.isAdmin()){
									if(par.length < 2){
										sayP("/kick pseudo");
									}else{
										Client cl1 = c.getClientByPseudo(par[1]);
										if(cl1==null){
											sayP(par[1]+" n'existe pas.");
										}else if(cl1.isAdmin()){
											sayP(par[1]+" est admin, vous ne pouvez pas le kick.");
										}else{
											cl1.setConnected(false);
										}
									}
								}else{
									sayP("Vous n'avez pas l'accès à cette commande");
								}
								break;
							case "/list":
								sayP("Liste de connectés :");
								for(int k = 0; k < c.getClients().length; k++){
									sayP(c.getClients()[k].getAdminMark()+c.getClients()[k].getPseudo());
								}
								break;
							case "/me":
								if(par.length < 2){
									sayP("/me action");
								}else{
									String action = par[1];
									for(int k = 2; k < par.length; k++){
										action = action+" "+par[k];
									}
									c.say("*"+cl.getPseudo()+" "+action+"*");
								}
								break;
							case "/mute":
								if(cl.isAdmin()){
									if(par.length < 2){
										sayP("/mute pseudo");
									}else{
										Client cl1 = c.getClientByPseudo(par[1]);
										if(cl1==null){
											sayP(par[1]+" n'existe pas.");
										}else if(cl1.isAdmin()){
											sayP(par[1]+" est admin, vous ne pouvez pas le mute.");
										}else{
											sayP(c.mute(par[1]));
										}
									}
								}else{
									sayP("Vous n'avez pas l'accès à cette commande");
								}
								break;
							case "/pseudo":
								if(par.length < 2){
									sayP("/pseudo NouveauPseudo");
								}else{
									if(par.length == 3 && cl.isAdmin()){
										Client cl1 = c.getClientByPseudo(par[1]);
										if(cl1==null){
											sayP(par[1]+" n'existe pas.");
										}else if(cl1.isAdmin()){
											sayP(par[1]+" est admin, vous ne pouvez pas changer son pseudo.");
										}else{
											switch(c.isFree(par[2])){
												case 1:
													sayP("Ce pseudo est déjà pris !");
													break;
												case 2:
													sayP("Pseudo invalide");
													break;
												default:
													c.setPseudo(cl1.getId(),par[2]);
													break;
											}
										}
									}else{
										switch(c.isFree(par[1])){
											case 1:
												sayP("Ce pseudo est déjà pris !");
												break;
											case 2:
												sayP("Pseudo invalide");
												break;
											default:
												c.setPseudo(cl.getId(),par[1]);
												break;
										}
									}
								}
								break;
							default:
								sayP("Commande inconnue");
								break;
						}
					}
				}
			} catch (IOException e) {
				c.say2(cl.getAdminMark()+cl.getPseudo()+" "+e.getLocalizedMessage());
				deco();
			}
		}
	}
		
	
	private void sayP(String msg){
		c.say2(cl.getAdminMark()+cl.getPseudo()+" <~ "+msg);
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			c.say2(e.getLocalizedMessage());
			deco();
		}
	}
	
	private void deco(){
		c.say(cl.getAdminMark()+cl.getPseudo()+" s'est déconnecté.");
		c.disconnect(cl.getId());
		stop = true;
		try {
			out.close();
			in.close();
		} catch (IOException e1) {}
	}

}
