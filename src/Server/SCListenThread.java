package Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SCListenThread extends Thread {
    
    SClient sclient; 
    
    public SCListenThread(SClient sclient){
        this.sclient = sclient;
    }

    @Override
    public void run() {
        while(!this.sclient.socket.isClosed()){
            try {
                Object msg = this.sclient.sInput.readObject();
                System.out.println("SClient Message: "+msg.toString());
            } catch (IOException ex) {
                Logger.getLogger(SCListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SCListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
