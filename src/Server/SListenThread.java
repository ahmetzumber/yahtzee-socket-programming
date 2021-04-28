package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SListenThread  extends Thread {
    
    Server server;
    
    public SListenThread(Server server){
        this.server = server;
    }
    
    @Override
    public void run() {
        while(!this.server.socket.isClosed()){
            try {
                System.out.println("Listeningg...");
                Socket newSocket = this.server.socket.accept();
                SClient newSClient = new SClient(newSocket);
                newSClient.listen();
                this.server.sclients.add(newSClient);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
}
