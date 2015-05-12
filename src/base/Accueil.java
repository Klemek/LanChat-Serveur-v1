package base;

import java.io.IOException;
import java.net.ServerSocket;

class Accueil implements Runnable{

    private ServerSocket socketserver;
    private Chat c;
    
    public Accueil(ServerSocket s, Chat c){
         socketserver = s;
         this.c = c;
         
     }

     public void run() {
         try {
             while(true){
                new Thread(new Connexion(socketserver.accept(),c)).start();;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }