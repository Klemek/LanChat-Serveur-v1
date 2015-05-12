package base;

import java.io.BufferedWriter;
import java.io.IOException;

public class Emission implements Runnable{

    private BufferedWriter out;
    private Chat c;
	private int time;
	private boolean stop = false;
	
	
	public Emission(final Chat c,BufferedWriter out){
		this.c = c;
		this.out = out;
		this.time = c.getTime();
	}
	
	@Override
	public void run() {
		while(!stop){
			System.out.print("");//meintient le thread activé
			if(time != c.getTime()){
				while(time < c.getTime()){
					sayP(c.getLog().get(time));
					time++;
				}
				if(time > c.getTime())time = c.getTime();
			}
		}
		
	}

	private void sayP(String msg){
			try {
				out.write(msg);
				out.newLine();
				out.flush();
			} catch (IOException e) {
				deco();
			}
			
	}
	
	private void deco(){
		stop = true;
	}
	
}
